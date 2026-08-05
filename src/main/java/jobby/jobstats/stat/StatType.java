package jobby.jobstats.stat;

import net.minecraft.ChatFormatting;

public enum StatType {
    BLOCKS_MINED("jobstats:blocks_mined", "Blocks Mined", ChatFormatting.GRAY),
    CROPS_HARVESTED("jobstats:crops_harvested", "Crops Harvested", ChatFormatting.GREEN),
    MOB_KILLS("jobstats:mob_kills", "Kills", ChatFormatting.DARK_RED),
    DAMAGE_DEALT("jobstats:damage_dealt", "Damage Dealt", ChatFormatting.RED),
    ARMOR_DAMAGE("jobstats:armor_damage", "Damage Absorbed", ChatFormatting.BLUE),
    FISH_CAUGHT("jobstats:fish_caught", "Fish Caught", ChatFormatting.AQUA),
    SHEEP_SHEARED("jobstats:sheep_sheared", "Sheep Sheared", ChatFormatting.WHITE),
    ARROWS_SHOT("jobstats:arrows_shot", "Arrows Shot", ChatFormatting.YELLOW),
    ELYTRA_FLIGHT("jobstats:elytra_flight", "Flight Time (s)", ChatFormatting.LIGHT_PURPLE),
    CRITICAL_STRIKES("jobstats:critical_strikes", "Critical Strikes", ChatFormatting.GOLD),
    TRIDENTS_THROWN("jobstats:tridents_thrown", "Times Thrown", ChatFormatting.DARK_AQUA),
    LOGS_STRIPPED("jobstats:logs_stripped", "Logs Stripped", ChatFormatting.GOLD);

    private final String key;
    private final String displayName;
    private final ChatFormatting defaultColor;

    StatType(String key, String displayName, ChatFormatting defaultColor) {
        this.key = key;
        this.displayName = displayName;
        this.defaultColor = defaultColor;
    }

    public String getKey() { return key; }
    public String getDisplayName() { return displayName; }
    public ChatFormatting getDefaultColor() { return defaultColor; }
}