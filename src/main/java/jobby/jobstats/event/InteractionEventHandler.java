package jobby.jobstats.event;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InteractionEventHandler {
    public static void register() {
        // Handle Shearing
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClientSide() && entity instanceof Sheep sheep) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.is(Items.SHEARS) && sheep.readyForShearing()) {
                    if (StatManager.isTrackable(stack)) {
                        StatManager.setOwnership(stack, player.getName().getString());
                        StatManager.incrementStat(stack, StatType.SHEEP_SHEARED, 1);
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }
}