package net.fabricmc.arminias.sleepaddon.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLiving {
    @Shadow
    public ChunkCoordinates playerLocation;
    @Shadow
    protected boolean sleeping;
    @Shadow
    private int sleepTimer;
    @Shadow
    protected abstract void func_71013_b(int par1);

    public EntityPlayerMixin(World par1World) {
        super(par1World);
    }

    @Inject(at = @At("RETURN"), method = "sleepInBedAt", cancellable = true)
    public void sleepInBedAt(int par1, int par2, int par3, CallbackInfoReturnable<EnumStatus> cir) {
        if (cir.getReturnValue() == EnumStatus.OTHER_PROBLEM) {
            if (!this.worldObj.isRemote)
            {
                if (this.isPlayerSleeping() || !this.isEntityAlive())
                {
                    cir.setReturnValue(EnumStatus.OTHER_PROBLEM);
                    return;
                }

                if (!this.worldObj.provider.isSurfaceWorld())
                {
                    cir.setReturnValue(EnumStatus.NOT_POSSIBLE_HERE);
                    return;
                }

                if (this.worldObj.isDaytime())
                {
                    cir.setReturnValue(EnumStatus.NOT_POSSIBLE_NOW);
                    return;
                }

                if (Math.abs(this.posX - (double)par1) > 3.0D || Math.abs(this.posY - (double)par2) > 2.0D || Math.abs(this.posZ - (double)par3) > 3.0D)
                {
                    cir.setReturnValue(EnumStatus.TOO_FAR_AWAY);
                    return;
                }

                double var4 = 8.0D;
                double var6 = 5.0D;
                List var8 = this.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().getAABB((double)par1 - var4, (double)par2 - var6, (double)par3 - var4, (double)par1 + var4, (double)par2 + var6, (double)par3 + var4));

                if (!var8.isEmpty())
                {
                    cir.setReturnValue(EnumStatus.NOT_SAFE);
                    return;
                }
            }

            this.setSize(0.2F, 0.2F);
            this.yOffset = 0.2F;

            if (this.worldObj.blockExists(par1, par2, par3))
            {
                int var9 = this.worldObj.getBlockMetadata(par1, par2, par3);
                int var5 = BlockBed.getDirection(var9);
                float var10 = 0.5F;
                float var7 = 0.5F;

                switch (var5)
                {
                    case 0:
                        var7 = 0.9F;
                        break;

                    case 1:
                        var10 = 0.1F;
                        break;

                    case 2:
                        var7 = 0.1F;
                        break;

                    case 3:
                        var10 = 0.9F;
                }

                this.func_71013_b(var5);
                this.setPosition((double)((float)par1 + var10), (double)((float)par2 + 0.9375F), (double)((float)par3 + var7));
            }
            else
            {
                this.setPosition((double)((float)par1 + 0.5F), (double)((float)par2 + 0.9375F), (double)((float)par3 + 0.5F));
            }

            this.sleeping = true;
            this.sleepTimer = 0;
            this.playerLocation = new ChunkCoordinates(par1, par2, par3);
            this.motionX = this.motionZ = this.motionY = 0.0D;

            if (!this.worldObj.isRemote)
            {
                this.worldObj.updateAllPlayersSleepingFlag();
            }

            cir.setReturnValue(EnumStatus.OK);
            return;
        }
    }
}
