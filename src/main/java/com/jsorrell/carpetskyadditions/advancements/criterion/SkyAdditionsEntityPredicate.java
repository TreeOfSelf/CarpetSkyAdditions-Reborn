package com.jsorrell.carpetskyadditions.advancements.criterion;

import java.util.Optional;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record SkyAdditionsEntityPredicate(
        Optional<SkyAdditionsLocationPredicate> location,
        Optional<SkyAdditionsLocationPredicate> steppingOnLocation) {

    public static final Codec<SkyAdditionsEntityPredicate> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.optionalField("location",SkyAdditionsLocationPredicate.CODEC, false)
                            .forGetter(SkyAdditionsEntityPredicate::location),
                    Codec.optionalField("steppingOnLocation",SkyAdditionsLocationPredicate.CODEC, false)
                            .forGetter(SkyAdditionsEntityPredicate::location))

                    .apply(instance, SkyAdditionsEntityPredicate::new));

    public static final SkyAdditionsEntityPredicate ANY = new SkyAdditionsEntityPredicate(
            SkyAdditionsLocationPredicate.ANY, SkyAdditionsLocationPredicate.ANY);


    public SkyAdditionsEntityPredicate {
    };

    public boolean matches(ServerLevel level, Vec3 position, Entity entity) {
        if (this == ANY)
            return true;
        if (entity == null)
            return false;

        if (!location.get().matches(level, entity.getX(), entity.getY(), entity.getZ()))
            return false;

        if (steppingOnLocation.get() != SkyAdditionsLocationPredicate.ANY.get()) {
            Vec3 stepPos = Vec3.atCenterOf(entity.getOnPos());
            if (!steppingOnLocation.get().matches(level, stepPos.x(), stepPos.y(), stepPos.z())) {
                return false;
            }
        }
        return true;
    }
}
