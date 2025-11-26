/*
 * Copyright (c) 2013 L2jMobius
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package instances.TimedHunting;

import java.util.Calendar;

import org.l2jmobius.gameserver.data.holders.TimedHuntingZoneHolder;
import org.l2jmobius.gameserver.data.xml.TimedHuntingZoneData;
import org.l2jmobius.gameserver.managers.GrandBossManager;
import org.l2jmobius.gameserver.managers.InstanceManager;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.instancezone.Instance;

import instances.AbstractInstance;

/**
 * @author Mobius
 */
public class TimedHunting extends AbstractInstance
{
	// NPCs
	private static final int PATROL_TELEPORTER = 34568;
	private static final int PATROL_GUARD = 34569;
	
	// Tower of Insolence
	private static final int TELEPORT_SCOUT = 34549; // Tower of Insolence
	private static final int SPACETEMPORAL_RIFT = 34616; // Teleport to Baium - Tower of Insolence
	private static final int BAIUM_BOSS_ID = 29391; // Baium ID
	
	// Locations
	private static final Location BAIUM_TELEPORT_LOC = new Location(113365, 14886, 10053);
	
	// Misc
	private static final int[] TEMPLATE_IDS =
	{
		1001, // Fioren's Crystal Prison
		1006, // Jamoa Camp
		1007, // Pantheon's Museum
		1013, // Devastated Innadril
		1020, // Tower of Insolence
	};
	
	public TimedHunting()
	{
		super(TEMPLATE_IDS);
		addStartNpc(PATROL_TELEPORTER, PATROL_GUARD, TELEPORT_SCOUT, SPACETEMPORAL_RIFT);
		addTalkId(PATROL_TELEPORTER, PATROL_GUARD, TELEPORT_SCOUT, SPACETEMPORAL_RIFT);
		addFirstTalkId(PATROL_TELEPORTER, PATROL_GUARD, TELEPORT_SCOUT, SPACETEMPORAL_RIFT);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		if (event.startsWith("ENTER"))
		{
			final int zoneId = Integer.parseInt(event.split(" ")[1]);
			final TimedHuntingZoneHolder huntingZone = TimedHuntingZoneData.getInstance().getHuntingZone(zoneId);
			if (huntingZone == null)
			{
				return null;
			}
			
			if (huntingZone.isSoloInstance())
			{
				enterInstance(player, npc, huntingZone.getInstanceId());
			}
			else
			{
				Instance world = null;
				for (Instance instance : InstanceManager.getInstance().getInstances())
				{
					if (instance.getTemplateId() == huntingZone.getInstanceId())
					{
						world = instance;
						break;
					}
				}
				
				if (world == null)
				{
					world = InstanceManager.getInstance().createInstance(huntingZone.getInstanceId(), player);
				}
				
				player.teleToLocation(huntingZone.getEnterLocation(), world);
			}
		}
		else if (event.equals("BAIUM_ENTER")) // Logic to allow entry into Baium zone.
		{
			// 1. Time Validation
			final Calendar now = Calendar.getInstance();
			final int day = now.get(Calendar.DAY_OF_WEEK);
			final int hour = now.get(Calendar.HOUR_OF_DAY);
			final int minute = now.get(Calendar.MINUTE);
			final int second = now.get(Calendar.SECOND);
			
			boolean isOpen = false;
			final int targetDay = Calendar.WEDNESDAY;
			final int startHour = 22;
			final int startMinute = 0;
			final int endHour = 23;
			final int endMinute = 0;
			
			if (day == targetDay)
			{
				long currentTotalSeconds = (hour * 3600) + (minute * 60) + second;
				long startTotalSeconds = (startHour * 3600) + (startMinute * 60);
				long endTotalSeconds = (endHour * 3600) + (endMinute * 60);
				
				if ((currentTotalSeconds >= startTotalSeconds) && (currentTotalSeconds < endTotalSeconds))
				{
					isOpen = true;
				}
			}
			
			if (!isOpen && !player.isGM())
			{
				return "34616.html";
			}
			
			// 2. Baium Status Validation
			final int status = GrandBossManager.getInstance().getStatus(BAIUM_BOSS_ID);
			if (status == 3) // DEAD
			{
				return "34616.html";
			}
			
			// 3. Castle Validation
			boolean hasCastle = false;
			if (player.getClan() != null)
			{
				final int castleId = player.getClan().getCastleId();
				if ((castleId == 5) || (castleId == 8))
				{
					hasCastle = true;
				}
			}
			
			if (!hasCastle && !player.isGM())
			{
				player.sendMessage("You don't belong here!");
				return null;
			}
			
			// 4. Instance Management (Template ID 1020)
			Instance world = null;
			for (Instance instance : InstanceManager.getInstance().getInstances())
			{
				if (instance.getTemplateId() == 1020)
				{
					world = instance;
					break;
				}
			}
			
			if (world == null)
			{
				world = InstanceManager.getInstance().createInstance(1020, player);
			}
			
			if (world != null)
			{
				player.setInstance(world);
				player.teleToLocation(BAIUM_TELEPORT_LOC, world);
			}
			else
			{
				player.sendMessage("Error creating Baium instance.");
			}
		}
		
		return null;
	}
	
	@Override
	public String onFirstTalk(Npc npc, Player player)
	{
		if (npc.getId() == SPACETEMPORAL_RIFT)
		{
			return "34616.html";
		}
		
		final Instance instance = npc.getInstanceWorld();
		final String htmltext = null;
		if (isInInstance(instance))
		{
			return npc.getId() + ".html";
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new TimedHunting();
	}
}
