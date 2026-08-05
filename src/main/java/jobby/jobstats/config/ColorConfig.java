package jobby.jobstats.config;

import jobby.jobstats.stat.StatType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorConfig {
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("JobStats");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("ToolTipColors.yaml");

    private static boolean useOneColor = false;
    private static ChatFormatting universalColor = ChatFormatting.GRAY;
    private static final Map<String, ChatFormatting> statColors = new HashMap<>();

    public static void load() {
        try {
            if (!Files.exists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }

            if (!Files.exists(CONFIG_FILE)) {
                generateDefaultConfig();
            }

            parseConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChatFormatting getColorForStat(StatType stat) {
        if (useOneColor) {
            return universalColor;
        }
        return statColors.getOrDefault(stat.name(), stat.getDefaultColor());
    }

    private static void generateDefaultConfig() throws IOException {
        StringBuilder yaml = new StringBuilder();
        yaml.append("# JobStats Color Configuration\n");
        yaml.append("# Available colors: BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE\n\n");
        
        yaml.append("use_one_color: false\n");
        yaml.append("universal_color: GRAY\n\n");
        
        yaml.append("colors:\n");
        for (StatType type : StatType.values()) {
            yaml.append("  ").append(type.name()).append(": ").append(type.getDefaultColor().name()).append("\n");
        }

        Files.writeString(CONFIG_FILE, yaml.toString());
    }

    private static void parseConfig() throws IOException {
        List<String> lines = Files.readAllLines(CONFIG_FILE);
        boolean inColorsSection = false;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;

            if (trimmed.startsWith("use_one_color:")) {
                useOneColor = Boolean.parseBoolean(trimmed.split(":")[1].trim());
            } else if (trimmed.startsWith("universal_color:")) {
                universalColor = parseFormatting(trimmed.split(":")[1].trim(), ChatFormatting.GRAY);
            } else if (trimmed.startsWith("colors:")) {
                inColorsSection = true;
            } else if (inColorsSection && trimmed.contains(":")) {
                String[] parts = trimmed.split(":");
                String statName = parts[0].trim();
                ChatFormatting color = parseFormatting(parts[1].trim(), ChatFormatting.WHITE);
                statColors.put(statName, color);
            }
        }
    }

    private static ChatFormatting parseFormatting(String colorName, ChatFormatting fallback) {
        try {
            return ChatFormatting.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("[JobStats] Invalid color found in config: " + colorName + ". Defaulting to " + fallback.name());
            return fallback;
        }
    }
}