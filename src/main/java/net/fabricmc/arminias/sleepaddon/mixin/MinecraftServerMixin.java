package net.fabricmc.arminias.sleepaddon.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    private long prevTime = Minecraft.getSystemTime();
    private float lastMult = 1F;
    @Shadow
    public WorldServer[] worldServers;

    @Inject(method = "run()V", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;sleep(J)V"))
    private void injectTimeUpdate(CallbackInfo ci) {
        long cTime = Minecraft.getSystemTime();
        if (this.worldServers[0].areAllPlayersAsleep())
        {
            // Smooth it out a bit
            float newMult = lastMult * 0.7F + Math.max(50F / (cTime - prevTime), 1) * 0.3F;
            ((MinecraftAccessor)Minecraft.getMinecraft()).getTimer().timerSpeed = newMult;
            lastMult = newMult;
        }
        else
        {
            lastMult = 1F;
            ((MinecraftAccessor)Minecraft.getMinecraft()).getTimer().timerSpeed = lastMult;
        }
        prevTime = cTime;
    }
}
