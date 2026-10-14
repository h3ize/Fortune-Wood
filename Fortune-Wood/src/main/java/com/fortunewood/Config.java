package com.fortunewood;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue WOOD_PER_LEVEL;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        WOOD_PER_LEVEL = builder
                .comment("Bonus wood scale per Fortune level. Bonus logs = this * (level + random 0..level). Set to 0 to disable the bonus.")
                .defineInRange("woodPerLevel", 1, 0, 16);
        SPEC = builder.build();
    }

    private Config() {
    }
}
