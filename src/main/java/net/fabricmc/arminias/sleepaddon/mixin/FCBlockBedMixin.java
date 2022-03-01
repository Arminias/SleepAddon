package net.fabricmc.arminias.sleepaddon.mixin;

import net.minecraft.src.BlockBed;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FCBlockBed;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FCBlockBed.class)
public abstract class FCBlockBedMixin extends BlockBed {
	public FCBlockBedMixin(int par1) {
		super(par1);
	}

	@Inject(at = @At("HEAD"), method = "onBlockActivated", cancellable = true)
	private void onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(super.onBlockActivated(world, i, j, k, player, iFacing, fXClick, fYClick, fZClick));
	}
}
