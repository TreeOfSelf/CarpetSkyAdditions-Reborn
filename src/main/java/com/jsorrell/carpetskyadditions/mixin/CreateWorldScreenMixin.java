package com.jsorrell.carpetskyadditions.mixin;

import com.jsorrell.carpetskyadditions.config.SkyAdditionsConfig;
import com.jsorrell.carpetskyadditions.gen.SkyAdditionsWorldPresets;
import com.jsorrell.carpetskyadditions.helpers.DataConfigurationHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value = EnvType.CLIENT)
@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    // Try to reload config whenever Create New World is clicked
    @Inject(
            method = "openFresh(Lnet/minecraft/client/Minecraft;Ljava/lang/Runnable;)V",
            at = @At("HEAD"))
    private static void loadConfigFromFile(Minecraft minecraft, Runnable onClose, CallbackInfo ci) {
        AutoConfig.getConfigHolder(SkyAdditionsConfig.class).load();
    }

    @WrapOperation(
            method = "openFresh(Lnet/minecraft/client/Minecraft;Ljava/lang/Runnable;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldCallback;)V",
            at =
                    @At(
                            value = "FIELD",
                            opcode = Opcodes.GETSTATIC,
                            target =
                                    "Lnet/minecraft/world/level/levelgen/presets/WorldPresets;NORMAL:Lnet/minecraft/resources/ResourceKey;"))
    private static ResourceKey<WorldPreset> setDefaultSelectedWorldPreset(Operation<ResourceKey<WorldPreset>> original) {
        SkyAdditionsConfig config =
                AutoConfig.getConfigHolder(SkyAdditionsConfig.class).get();
        return config.defaultToSkyBlockWorld ? SkyAdditionsWorldPresets.SKYBLOCK : /*WorldPresets.NORMAL*/ original.call();
    }

    @ModifyArg(method = "openFresh(Lnet/minecraft/client/Minecraft;Ljava/lang/Runnable;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldCallback;)V",
        at =
        @At(
            value = "INVOKE",
            target =
                "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;openCreateWorldScreen(Lnet/minecraft/client/Minecraft;Ljava/lang/Runnable;Ljava/util/function/Function;Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContextMapper;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldCallback;)V"))
    private static ResourceKey<WorldPreset> setDefaultWorldGenSettingsB(ResourceKey<WorldPreset> worldPreset) {
        SkyAdditionsConfig config = AutoConfig.getConfigHolder(SkyAdditionsConfig.class).get();
        if (config.defaultToSkyBlockWorld)
            // Set Skyblock-specific world generation
            return SkyAdditionsWorldPresets.SKYBLOCK;
        // Fallback to normal preset
        return worldPreset;
    }

    @ModifyArg(
            method = "openCreateWorldScreen",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;createDefaultLoadConfig(Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/world/level/WorldDataConfiguration;)Lnet/minecraft/server/WorldLoader$InitConfig;"))
    private static WorldDataConfiguration enableSkyAdditionsDatapacks(WorldDataConfiguration dc) {
        return DataConfigurationHelper.updateDataConfiguration(dc);
    }
}
