package net.MagikVehicleHelper.api;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 
 * Have your custom entity extend this class if you would like it to be a land vehicle.
 * 
 * @author Magik
 */
public class LandVehicleHelper extends EntityLiving
{
	public float vehicXOffset = 0;
	public float vehicYOffset = 0;
	public float vehicZOffset = 0;
	public float moveModifier = 1F;
	public String soundname;

	public LandVehicleHelper(World world, String soundname)
	{
		super(world);
		setSize(0.9F, 0.9F);
		isImmuneToFire = true;
		this.soundname = soundname;
	}

	@Override
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	public void fall(float f){}

	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound(soundname, 0.15F, 1.0F);
	}

	public String getMovingSound()
	{
		return "";
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
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
	public void moveEntityWithHeading(float f, float f1)
	{
		if (riddenByEntity != null && riddenByEntity instanceof EntityLivingBase)
		{
			prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
			rotationPitch = riddenByEntity.rotationPitch * 0.5F;
			setRotation(rotationYaw, rotationPitch);
			rotationYawHead = renderYawOffset = rotationYaw;
			f = ((EntityLivingBase)riddenByEntity).moveStrafing * 0.5F;
			f1 = ((EntityLivingBase)riddenByEntity).moveForward * (moveModifier / 8);

			if (onGround)
			{
				float f2 = MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F);
				float f3 = MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F);
				motionX += -0.4F * f2 * f1;
				motionZ += 0.4F * f3 * f1;
			}

			stepHeight = 1.0F;
			jumpMovementFactor = getAIMoveSpeed() * 0.1F;

			if (!worldObj.isRemote)
			{
				setAIMoveSpeed(f1);
				super.moveEntityWithHeading(f, f1);
			}
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
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
