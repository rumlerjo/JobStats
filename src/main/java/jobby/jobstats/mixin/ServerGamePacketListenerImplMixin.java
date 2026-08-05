package jobby.jobstats.mixin;

import jobby.jobstats.stat.StatManager;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
    @Shadow public ServerPlayer player;

    @Inject(method = "handleSetCreativeModeSlot", at = @At("HEAD"))
    private void onCreativeSpawn(ServerboundSetCreativeModeSlotPacket packet, CallbackInfo ci) {
        ItemStack stack = packet.itemStack();
        if (StatManager.isTrackable(stack)) {
            StatManager.setOwnership(stack, this.player.getName().getString());
        }
    }
}