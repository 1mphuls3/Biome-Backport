package com.myst.biomebackport.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static ForgeConfigSpec.IntValue SLOTS;
    public static void registerServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Server configs");
        SLOTS = builder.comment("The number of slots in a decorated pot (Default [9])")
                .defineInRange("slots", 9, 1, 9);
        builder.pop();
    }
}
