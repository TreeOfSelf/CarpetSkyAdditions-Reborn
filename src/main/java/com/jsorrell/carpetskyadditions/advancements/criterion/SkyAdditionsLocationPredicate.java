package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.jsorrell.carpetskyadditions.helpers.CoralSpreader;
import com.jsorrell.carpetskyadditions.helpers.SmallDripleafSpreader;
import com.jsorrell.carpetskyadditions.util.SkyAdditionsResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;

public record SkyAdditionsLocationPredicate(
                Optional<LocationPredicate> location,
                Optional<MinMaxBounds.Doubles> coralSuitability,
                Optional<Boolean> coralConvertible,
                Optional<Boolean> desertPyramidCheck,
                Optional<Boolean> smallDripleafCanSpread) {

        public static final Codec<SkyAdditionsLocationPredicate> CODEC = RecordCodecBuilder.create(
                        instance -> instance.group(
                                        Codec.optionalField("location",LocationPredicate.CODEC,  false)
                                                        .forGetter(SkyAdditionsLocationPredicate::location),
                                        Codec.optionalField("coral_suitability",MinMaxBounds.Doubles.CODEC,  false)
                                                        .forGetter(SkyAdditionsLocationPredicate::coralSuitability),
                                        Codec.optionalField("coral_convertible", Codec.BOOL,  false)
                                                        .forGetter(SkyAdditionsLocationPredicate::coralConvertible),
                                        Codec.optionalField("is_desert_pyramid_blue_terracotta",Codec.BOOL,  false)
                                                        .forGetter(SkyAdditionsLocationPredicate::desertPyramidCheck),
                                        Codec.optionalField("small_dripleaf_spreadable", Codec.BOOL, false)
                                                        .forGetter(SkyAdditionsLocationPredicate::smallDripleafCanSpread))
                                        .apply(instance, SkyAdditionsLocationPredicate::new));

        public static final Optional<SkyAdditionsLocationPredicate> ANY = Optional
                        .of(new SkyAdditionsLocationPredicate(null, null, null, null, null));

        public SkyAdditionsLocationPredicate {
        }

        private boolean doDesertPyramidCheck(ServerLevel level, BlockPos blueTerracottaPos, boolean sendDebugMessage) {
                StructureTemplate template = level.getServer()
                                .getStructureManager()
                                .get(new SkyAdditionsResourceLocation("desert_pyramid"))
                                .orElseThrow();
                BlockPos centerOffset = new BlockPos(
                                template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
                BlockPos structureOrigin = blueTerracottaPos.subtract(centerOffset);

                StructurePlaceSettings placeSettings = new StructurePlaceSettings().setRotationPivot(centerOffset);

                return Arrays.stream(Rotation.values()).anyMatch(r -> {
                        placeSettings.setRotation(r);

                        for (Block block : new Block[] {
                                        Blocks.BLUE_TERRACOTTA,
                                        Blocks.ORANGE_TERRACOTTA,
                                        Blocks.SANDSTONE,
                                        Blocks.CUT_SANDSTONE,
                                        Blocks.CHISELED_SANDSTONE,
                                        Blocks.SANDSTONE_STAIRS,
                                        Blocks.SANDSTONE_SLAB
                        }) {
                                List<StructureTemplate.StructureBlockInfo> requiredBlocks = template.filterBlocks(
                                                structureOrigin,
                                                placeSettings, block);
                                for (StructureTemplate.StructureBlockInfo requiredBlock : requiredBlocks) {
                                        BlockState requiredState = requiredBlock.state();
                                        BlockState currentState = level.getBlockState(requiredBlock.pos());
                                        if (currentState != requiredState) {
                                                // Use terracotta to determine location and rotation
                                                if (sendDebugMessage && !requiredState.is(BlockTags.TERRACOTTA)) {
                                                        // Help players within 10 blocks of bounding box debug builds
                                                        AABB buildersBox = AABB.of(template
                                                                        .getBoundingBox(placeSettings, structureOrigin)
                                                                        .inflatedBy(10));
                                                        List<ServerPlayer> playersToNotify = level
                                                                        .getPlayers(serverPlayer -> buildersBox
                                                                                        .contains(serverPlayer
                                                                                                        .position()));
                                                        MutableComponent message;
                                                        if (currentState.getBlock() == requiredState.getBlock()) {
                                                                Map.Entry<Property<?>, Comparable<?>> incorrectProperty = requiredState
                                                                                .getValues()
                                                                                .entrySet().stream()
                                                                                .filter(e -> currentState.getValue(e
                                                                                                .getKey()) != e.getValue())
                                                                                .findAny()
                                                                                .orElseThrow();
                                                                message = Component.translatable(
                                                                                "message.desert_pyramid_incorrect_state",
                                                                                requiredBlock.pos().getX(),
                                                                                requiredBlock.pos().getY(),
                                                                                requiredBlock.pos().getZ(),
                                                                                incorrectProperty.getKey().getName(),
                                                                                incorrectProperty.getValue());
                                                        } else {
                                                                message = Component.translatable(
                                                                                "message.desert_pyramid_incorrect_block",
                                                                                requiredBlock.pos().getX(),
                                                                                requiredBlock.pos().getY(),
                                                                                requiredBlock.pos().getZ(),
                                                                                requiredState.getBlock().getName());
                                                        }
                                                        playersToNotify.forEach(
                                                                        player -> player.sendSystemMessage(message
                                                                                        .withStyle(ChatFormatting.DARK_RED)));
                                                }
                                                return false;
                                        }
                                }
                        }
                        return true;
                });
        }

        public boolean matches(ServerLevel level, double x, double y, double z) {
                BlockPos blockPos = BlockPos.containing(x, y, z);
                if (desertPyramidCheck.isPresent()) {
                        if (doDesertPyramidCheck(level, blockPos, desertPyramidCheck.get()) == desertPyramidCheck
                                        .get()) {
                                return true;
                        }
                }

                if (coralConvertible.isPresent() || coralSuitability.isPresent()) {

                        if (coralConvertible.isPresent() && CoralSpreader.isConvertible(level, blockPos) == coralConvertible.get()) {
                                return true;
                        }

                        if (coralSuitability.isPresent() && coralSuitability.get()
                                        .matches(CoralSpreader.calculateCoralSuitability(level, blockPos))) {
                                return true;
                        }
                }


                if (smallDripleafCanSpread.isPresent()) {
                        boolean canSpread = SmallDripleafSpreader.canSpreadFrom(level.getBlockState(blockPos), level,
                                        blockPos);
                        boolean sDCS = smallDripleafCanSpread.get();
                        if (canSpread == sDCS) {
                                return true;
                        }
                }

                return false;
        }

        // public JsonElement serializeToJson() {
        // if (this == ANY)
        // return JsonNull.INSTANCE;

        // JsonObject jsonObject = new JsonObject();
        // if (desertPyramidCheck != null) {
        // jsonObject.addProperty("is_desert_pyramid_blue_terracotta",
        // desertPyramidCheck);
        // }
        // if (coralConvertible != null) {
        // jsonObject.addProperty("coral_convertible", desertPyramidCheck);
        // }
        // if (!coralSuitability.isAny()) {
        // jsonObject.add("coral_suitability", coralSuitability.serializeToJson());
        // }
        // if (smallDripleafCanSpread != null) {
        // jsonObject.addProperty("small_dripleaf_spreadable", smallDripleafCanSpread);
        // }
        // return jsonObject;
        // }

        // public static SkyAdditionsLocationPredicate fromJson(JsonElement json) {
        // if (json == null || json.isJsonNull())
        // return ANY;

        // JsonObject jsonObject = GsonHelper.convertToJsonObject(json, "location");
        // Boolean desertPyramidCheck = null;
        // if (jsonObject.has("is_desert_pyramid_blue_terracotta")) {
        // desertPyramidCheck = GsonHelper.getAsBoolean(jsonObject,
        // "is_desert_pyramid_blue_terracotta");
        // }
        // Boolean coralConvertible = null;
        // if (jsonObject.has("coral_convertible")) {
        // coralConvertible = GsonHelper.getAsBoolean(jsonObject, "coral_convertible");
        // }
        // MinMaxBounds.Doubles coralSuitability =
        // MinMaxBounds.Doubles.fromJson(jsonObject.get("coral_suitability"));
        // Boolean smallDripleafCanSpread = null;
        // if (jsonObject.has("small_dripleaf_spreadable")) {
        // smallDripleafCanSpread = GsonHelper.getAsBoolean(jsonObject,
        // "small_dripleaf_spreadable");
        // }
        // return new SkyAdditionsLocationPredicate(
        // desertPyramidCheck, coralConvertible, coralSuitability,
        // smallDripleafCanSpread);
        // }
}
