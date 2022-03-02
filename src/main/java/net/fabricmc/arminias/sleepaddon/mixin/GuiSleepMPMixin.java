package net.fabricmc.arminias.sleepaddon.mixin;

import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiSleepMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiSleepMP.class)
public abstract class GuiSleepMPMixin extends GuiChat {
    @ModifyConstant(method = "initGui", constant = @Constant(intValue = 40))
    private int moveUpButton(int value) {
        return value + 30;
    }
}
