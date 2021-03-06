package net.MagikVehicleHelper.api;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 
 * Have your entity extend this class if it is a water vehicle.
 * 
 * @author Magik
 */


public class WaterVehicleHelper extends EntityLiving{
	
	public static Boolean isBoatEmpty = true;
	public float vehicXOffset = 0;
	public float vehicYOffset = 0;
	public float vehicZOffset = 0;
	public float moveModifier = 1F;
	protected String soundname;
	public WaterVehicleHelper(World world, String soundname) {
		super(world);
		setSize(0.9F, 0.9F);
		isImmuneToFire = true;
	        this.motionX = 0.0D;
	        this.motionY = 0.0D;
	        this.motionZ = 0.0D;
	 	this.soundname = soundname;
	}

	@Override
	public boolean canRenderOnFire()
	{
		return false;
	}
	
	public boolean canBePushed()
	    {
	        return true;
	    }
	
	@Override
	public void fall(float p1){}

	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound(soundname, 0.15F, 1.0F);
	}
	
	public String getMovingSound()
	{
		return null;
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		if (!worldObj.isRemote && (riddenByEntity == null || riddenByEntity == player))
		{
			player.mountEntity(this);
			return true;
		}
		else
		{
			return false;
		}
	}
	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if (riddenByEntity != null && riddenByEntity instanceof EntityLivingBase)
		{
			prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
			rotationPitch = riddenByEntity.rotationPitch * 0.5F;
			setRotation(rotationYaw, rotationPitch);
			rotationYawHead = renderYawOffset = rotationYaw;
			p_70612_1_ = ((EntityLivingBase)riddenByEntity).moveStrafing * 0.5F;
			p_70612_2_ = ((EntityLivingBase)riddenByEntity).moveForward * (moveModifier / 8);

			if (onGround)
			{
				float f2 = MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F);
				float f3 = MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F);
				motionX += -0.4F * f2 * p_70612_2_;
				motionZ += 0.4F * f3 * p_70612_2_;
			}

			stepHeight = 1.0F;
			jumpMovementFactor = getAIMoveSpeed() * 0.1F;

			if (!worldObj.isRemote)
			{
				setAIMoveSpeed(p_70612_2_);
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}
			 
	                for (int j1 = 0; j1 < 2; ++j1)
	                {
	                    int k = MathHelper.floor_double(this.posY) + j1;
	                }
		}
	}

	@Override
	public void updateRiderPosition()
	{
		if (riddenByEntity != null)
		{
			riddenByEntity.setPosition(posX + vehicXOffset, posY + getMountedYOffset() + riddenByEntity.getYOffset() + vehicYOffset, posZ + vehicZOffset);
		}
	}
}
