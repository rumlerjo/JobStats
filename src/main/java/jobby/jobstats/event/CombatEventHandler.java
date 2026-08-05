package jobby.jobstats.event;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class CombatEventHandler {
    public static void register() {
        // Expanded the lambda to (level, entity, killedEntity, damageSource)
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((level, entity, killedEntity, damageSource) -> {
            if (entity instanceof ServerPlayer player) {
                ItemStack mainHandStack = player.getMainHandItem();
                
                if (StatManager.isTrackable(mainHandStack)) {
                    StatManager.setOwnership(mainHandStack, player.getName().getString());
                    StatManager.incrementStat(mainHandStack, StatType.MOB_KILLS, 1);
                }
            }
        });
    }
}