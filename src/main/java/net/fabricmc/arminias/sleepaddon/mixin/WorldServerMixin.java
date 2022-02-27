package net.fabricmc.arminias.sleepaddon.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldServer.class)
public abstract class WorldServerMixin extends World {
    public WorldServerMixin(ISaveHandler par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler, ILogAgent par6ILogAgent) {
        super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent);
    }
    
    @ModifyVariable(method = "tick", at = @At("LOAD"), name = "var1")
    public boolean modify_var1_load(boolean var1) {
        return true;
    }
}
