package jobby.jobstats.stat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jobby.jobstats.config.ColorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;

public class StatManager {

    public static boolean isTrackable(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        
        return stack.has(DataComponents.TOOL) || 
               stack.has(DataComponents.WEAPON) || 
               (stack.has(DataComponents.EQUIPPABLE) && stack.has(DataComponents.MAX_DAMAGE)) || 
               stack.has(DataComponents.GLIDER) || 
               stack.is(Items.SHIELD) || 
               stack.is(Items.TRIDENT) || 
               stack.is(Items.SHEARS) || 
               stack.is(Items.FISHING_ROD) || 
               stack.is(Items.MACE) ||
               stack.is(Items.BOW) ||
               stack.is(Items.CROSSBOW);
    }

    public static void incrementStat(ItemStack stack, StatType stat, int amount) {
        if (stack == null || stack.isEmpty()) return;

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag nbt = customData.copyTag();

        int currentValue = nbt.getIntOr(stat.getKey(), 0); 
        nbt.putInt(stat.getKey(), currentValue + amount);

        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        updateLore(stack, nbt);
    }

    public static void setOwnership(ItemStack stack, String ownerName) {
        if (!isTrackable(stack)) return;

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag nbt = customData.copyTag();

        if (!nbt.contains("jobstats:owner")) {
            nbt.putString("jobstats:owner", ownerName);
            
            // Record the date ownership was established
            String currentDate = LocalDate.now().toString(); // Outputs format: YYYY-MM-DD
            nbt.putString("jobstats:created_date", currentDate);
            
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
            updateLore(stack, nbt);
        }
    }

    private static void updateLore(ItemStack stack, CompoundTag nbt) {
        List<Component> newLoreLines = new ArrayList<>();
        
        newLoreLines.add(Component.literal("")); 
        
        if (nbt.contains("jobstats:owner")) {
            String owner = nbt.getStringOr("jobstats:owner", "Unknown");
            
            // Check if the item has a recorded creation date and format accordingly
            if (nbt.contains("jobstats:created_date")) {
                String date = nbt.getStringOr("jobstats:created_date", "Unknown Date");
                newLoreLines.add(Component.literal("Owner: " + owner + " (" + date + ")").withStyle(ChatFormatting.GOLD));
            } else {
                newLoreLines.add(Component.literal("Owner: " + owner).withStyle(ChatFormatting.GOLD));
            }
        }

        newLoreLines.add(Component.literal("♦ Item Statistics ♦").withStyle(ChatFormatting.DARK_PURPLE));

        for (StatType type : StatType.values()) {
            if (nbt.contains(type.getKey())) {
                int value = nbt.getIntOr(type.getKey(), 0);
                ChatFormatting dynamicColor = ColorConfig.getColorForStat(type);
                newLoreLines.add(Component.literal(type.getDisplayName() + ": " + value).withStyle(dynamicColor));
            }
        }

        stack.set(DataComponents.LORE, new ItemLore(newLoreLines));
    }
}