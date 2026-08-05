package jobby.jobstats.event;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class AdditionalEvents {
    public static void register() {
        // Elytra Flight Time (Ticks)
        ServerTickEvents.START_LEVEL_TICK.register(level -> {
            level.players().forEach(player -> {
                if (player.isFallFlying()) {
                    ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
                    if (StatManager.isTrackable(chestplate)) {
                        StatManager.incrementStat(chestplate, StatType.ELYTRA_FLIGHT, 1);
                    }
                }
            });
        });
    }
}