package net.MagikVehicleHelper.api.packet;

import java.io.IOException;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

public class PacketHandler {
protected String channelName = "MagikVehicleHelperAPI";
	
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) throws IOException
	{
	
	}

	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event) throws IOException
	{	
	
	}
}
