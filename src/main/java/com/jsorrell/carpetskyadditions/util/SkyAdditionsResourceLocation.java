package com.jsorrell.carpetskyadditions.util;

import com.jsorrell.carpetskyadditions.SkyAdditionsExtension;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceLocation;

public class SkyAdditionsResourceLocation {
    public static final String NAMESPACE = SkyAdditionsExtension.MOD_ID;
    private final ResourceLocation resourceLocation;

    public SkyAdditionsResourceLocation(String path) {
        this.resourceLocation = ResourceLocation.fromNamespaceAndPath(NAMESPACE, path);
    }

    public String getPath() {
        return resourceLocation.getPath();
    }

    public String getNamespace() {
        return resourceLocation.getNamespace();
    }

    public Identifier getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public String toString() {
        return resourceLocation.toString();
    }

    @Override
    public boolean equals(Object o) {
        return resourceLocation.equals(o);
    }

    @Override
    public int hashCode() {
        return resourceLocation.hashCode();
    }

}
