package caracalsmod;

import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MODID)
public class WTFConfig {
    @Config.Comment({"Spawn weight for caracals within savannahs; note that the vanilla spawn weights add up to 42.",
             "Default: 6 (for a 6/48 spawn chance in vanilla)"})
    @Config.RequiresMcRestart
    public static int caracalSavannahSpawnWeight = 5;

    @Config.Comment({"Spawn weight for caracals within rare spawning biomes (forest + jungle edge).",
            "Default: 1"})
    @Config.RequiresMcRestart
    public static int caracalRareSpawnWeight = 1;

    @Config.Comment({"Allow caracals to be hit by their owners.", "Default: false"})
    public static boolean friendlyFire = false;

    @Config.Comment({"Caracal attack damage.", "Default: 8"})
    @Config.RequiresMcRestart
    public static double caracalAttackDamage = 8;
}
