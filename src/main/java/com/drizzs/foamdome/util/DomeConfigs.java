package com.drizzs.foamdome.util;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DomeConfigs
{
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    public static class CommonConfig {

        public final ForgeConfigSpec.IntValue DOMESIZE;
        public final ForgeConfigSpec.IntValue GLASSDOMESIZE;
        public final ForgeConfigSpec.IntValue LAVADOMESIZE;

        CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Obsidian Dome Size");
            DOMESIZE = builder
                    .comment("This Controls the size of the Obsidian version of FoamDomes Dome Creators. Changing this may affect the shape of the sphere! Perfect Sphere is 5")
                    .defineInRange("DomeSize", 5, 3, 15);
            GLASSDOMESIZE = builder
                    .comment("This Controls the size of the Glass version of FoamDomes Dome Creators. Changing this may affect the shape of the sphere! Perfect Sphere is 5")
                    .defineInRange("DomeSize", 5, 3, 15);
            LAVADOMESIZE = builder
                    .comment("This Controls the size of the Lava version of FoamDomes Dome Creators. Changing this may affect the shape of the sphere! Perfect Sphere is 5")
                    .defineInRange("DomeSize", 5, 3, 15);
            builder.pop();

        }
    }

}
