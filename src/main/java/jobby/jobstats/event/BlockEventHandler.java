package jobby.jobstats.event;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.world.item.ItemStack;

public class BlockEventHandler {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (world.isClientSide() || player.isSpectator()) return;

            ItemStack mainHandStack = player.getMainHandItem();
            
            if (StatManager.isTrackable(mainHandStack)) {
                StatManager.setOwnership(mainHandStack, player.getName().getString());
                StatManager.incrementStat(mainHandStack, StatType.BLOCKS_MINED, 1);
            }
        });
    }
}