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
        
        // Log Stripping
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (!level.isClientSide()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() instanceof AxeItem && level.getBlockState(hitResult.getBlockPos()).getBlock() instanceof RotatedPillarBlock) {
                    if (StatManager.isTrackable(stack)) {
                        StatManager.incrementStat(stack, StatType.LOGS_STRIPPED, 1);
                    }
                }
            }
            return InteractionResult.PASS;
        });

        // Elytra Flight Time (Ticks)
        // Corrected to START_LEVEL_TICK for MojMap
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