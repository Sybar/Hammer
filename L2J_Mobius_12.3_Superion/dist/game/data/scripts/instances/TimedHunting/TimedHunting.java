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

import java.util.HashSet;
import java.util.Set;

import org.l2jmobius.gameserver.data.holders.TimedHuntingZoneHolder;
import org.l2jmobius.gameserver.data.xml.TimedHuntingZoneData;
import org.l2jmobius.gameserver.managers.InstanceManager;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.instancezone.Instance;
import org.l2jmobius.gameserver.model.item.holders.ItemHolder;
import org.l2jmobius.gameserver.model.skill.holders.SkillHolder;
import org.l2jmobius.gameserver.network.NpcStringId;
import org.l2jmobius.gameserver.network.enums.ChatType;

import instances.AbstractInstance;

/**
 * @author Mobius, Tanatos
 */
public class TimedHunting extends AbstractInstance
{
	// NPCs
	// Fioren's Crystal Prison
	private static final int FIOREN = 34634;
	private static final int RESEARCHERS_TELEPORTER_105 = 34529; // Storm Isle
	private static final int RESEARCHERS_TELEPORTER_110 = 34618; // Isle of Prayer
	private static final int RESEARCHERS_TELEPORTER_115 = 34624; // Alligator Island
	private static final int RESEARCHERS_TELEPORTER_120 = 34682; // Frost Island
	
	// Jamoa's Camp
	private static final int JAMOA = 34635;
	private static final int EXPEDITION_TELEPORTER_105 = 34526; // Primeval Isle
	private static final int EXPEDITION_TELEPORTER_110 = 34626; // Swamp of Screams
	private static final int EXPEDITION_TELEPORTER_115 = 34628; // Blazing Swamp
	private static final int EXPEDITION_TELEPORTER_120 = 34684; // Wasteland
	
	// Pantheon's Museum
	private static final int PANTHEON = 34636;
	private static final int OVERSEER_TELEPORTER_105 = 34524; // Golden Altar
	private static final int OVERSEER_TELEPORTER_110 = 34630; // Mimir's Altar
	private static final int OVERSEER_TELEPORTER_115 = 34632; // Plunderous Plains
	private static final int OVERSEER_TELEPORTER_120 = 34686; // Elven Forest
	
	// Pantheon's Museum Monsters
	private static final Set<Integer> PANTHEON_MONSTERS = new HashSet<>();
	static
	{
		PANTHEON_MONSTERS.add(24470);
		PANTHEON_MONSTERS.add(24471);
		PANTHEON_MONSTERS.add(24472);
		PANTHEON_MONSTERS.add(24473);
		PANTHEON_MONSTERS.add(24474);
		PANTHEON_MONSTERS.add(24475);
		PANTHEON_MONSTERS.add(24837);
		PANTHEON_MONSTERS.add(24838);
		PANTHEON_MONSTERS.add(24839);
		PANTHEON_MONSTERS.add(24840);
		PANTHEON_MONSTERS.add(24841);
		PANTHEON_MONSTERS.add(24842);
		PANTHEON_MONSTERS.add(24939);
		PANTHEON_MONSTERS.add(24940);
		PANTHEON_MONSTERS.add(24941);
		PANTHEON_MONSTERS.add(24942);
		PANTHEON_MONSTERS.add(24943);
		PANTHEON_MONSTERS.add(24944);
		PANTHEON_MONSTERS.add(24974);
		PANTHEON_MONSTERS.add(24975);
		PANTHEON_MONSTERS.add(24976);
		PANTHEON_MONSTERS.add(24977);
		PANTHEON_MONSTERS.add(24978);
		PANTHEON_MONSTERS.add(24979);
	}
	
	// Abandoned Coal Mines
	private static final int INVESTIGATORS_TELEPORTER = 34551;
	
	// Imperial Tomb
	private static final int SEARCH_TEAM_TELEPORTER = 34552;
	
	// Ravaged Innadril
	private static final int LIONEL_HUNTER = 34646; // Heine
	private static final int PATROL_TELEPORTER_105 = 34568; // Field of Silence
	private static final int PATROL_TELEPORTER_115 = 34647; // Alligator Beach
	private static final int PATROL_TELEPORTER_120 = 34688; // Alligator Island
	
	// Otherworldly Atelia Refinery
	private static final int ATELIA_REFINERY_TELEPORT_DEVICE = 34583; // Otherworldly Atelia Refinery
	
	// Time Rift
	private static final int GATEKEEPER_ZIGGURAT = 34694; // Time Rift
	private static final int IVORY_TOWER_RESEARCHER = 34695; // Time Rift
	
	// Tower of Insolence
	private static final int PRIESTS_TELEPORTER = 34549; // Tower of Insolence
	private static final int PRIEST_GUARD = 34778; // Tower of Insolence
	private static final int NEREA = 34779; // Tower of Insolence
	
	// Tower of Insolence Monsters
	private static final Set<Integer> BATTLEGROUND_GUARDS = new HashSet<>();
	static
	{
		BATTLEGROUND_GUARDS.add(23844);
		BATTLEGROUND_GUARDS.add(23845);
		BATTLEGROUND_GUARDS.add(23850);
		BATTLEGROUND_GUARDS.add(23851);
	}
	private static final int INSOLENCE_RELIC_1 = 19900;
	private static final int INSOLENCE_RELIC_2 = 19901;
	private static final int INSOLENCE_RELIC_3 = 19902;
	private static final int INSOLENCE_RELIC_4 = 19903;
	private static final int INSOLENCE_RELIC_5 = 19904;
	private static final int INSOLENCE_RELIC_6 = 19905;
	private static final int INSOLENCE_KEEPER_1 = 23852;
	private static final int INSOLENCE_KEEPER_2 = 23856;
	private static final int INSOLENCE_KEEPER_3 = 23860;
	private static final Set<Integer> INSOLENCE_KEEPERS = new HashSet<>();
	static
	{
		INSOLENCE_KEEPERS.add(INSOLENCE_KEEPER_1);
		INSOLENCE_KEEPERS.add(INSOLENCE_KEEPER_2);
		INSOLENCE_KEEPERS.add(INSOLENCE_KEEPER_3);
	}
	private static final int INSOLENCE_WATCHER_4 = 23868;
	private static final int INSOLENCE_WATCHER_5 = 23873;
	private static final int INSOLENCE_WATCHER_6 = 23878;
	private static final Set<Integer> INSOLENCE_MONSTERS = new HashSet<>();
	static
	{
		INSOLENCE_MONSTERS.add(23853);
		INSOLENCE_MONSTERS.add(23854);
		INSOLENCE_MONSTERS.add(23855);
		INSOLENCE_MONSTERS.add(23857);
		INSOLENCE_MONSTERS.add(23858);
		INSOLENCE_MONSTERS.add(23859);
		INSOLENCE_MONSTERS.add(23861);
		INSOLENCE_MONSTERS.add(23862);
		INSOLENCE_MONSTERS.add(23863);
		INSOLENCE_MONSTERS.add(23864);
		INSOLENCE_MONSTERS.add(23865);
		INSOLENCE_MONSTERS.add(23866);
		INSOLENCE_MONSTERS.add(23867);
		INSOLENCE_MONSTERS.add(INSOLENCE_WATCHER_4);
		INSOLENCE_MONSTERS.add(23869);
		INSOLENCE_MONSTERS.add(23870);
		INSOLENCE_MONSTERS.add(23871);
		INSOLENCE_MONSTERS.add(23872);
		INSOLENCE_MONSTERS.add(INSOLENCE_WATCHER_5);
		INSOLENCE_MONSTERS.add(23874);
		INSOLENCE_MONSTERS.add(23875);
		INSOLENCE_MONSTERS.add(23876);
		INSOLENCE_MONSTERS.add(23877);
		INSOLENCE_MONSTERS.add(INSOLENCE_WATCHER_6);
	}
	
	// War Fortress Superion
	private static final int SUPERION_GUARDIAN = 23879;
	private static final int SUPERION_ARCHER = 23880;
	private static final int SUPERION_MAGE = 23881;
	private static final Set<Integer> SUPERION_MONSTERS = new HashSet<>();
	static
	{
		SUPERION_MONSTERS.add(SUPERION_GUARDIAN);
		SUPERION_MONSTERS.add(SUPERION_ARCHER);
		SUPERION_MONSTERS.add(SUPERION_MAGE);
	}
	
	// Skills
	private static final SkillHolder MORE_ADENA = new SkillHolder(32930, 1);
	private static final SkillHolder CHAOS_POWER = new SkillHolder(62051, 1);
	
	// Items
	private static final ItemHolder CHAOS_AETHER = new ItemHolder(83182, 1);
	
	// Locations
	private static final Location FIELD_OF_SILENCE = new Location(95983, 170989, -3640);
	private static final Location FIELD_OF_WHISPERS = new Location(95981, 210144, -3456);
	private static final Location ALLIGATOR_BEACH = new Location(114572, 202589, -3408);
	private static final Location ALLIGATOR_ISLAND = new Location(121342, 185640, -3587);
	
	// Misc
	private static final int CHAOS_PROBABILITY = 3;
	private static final int[] TEMPLATE_IDS =
	{
		1001, // Fioren's Crystal Prison
		1006, // Jamoa's Camp
		1007, // Pantheon's Museum
		1011, // Abandoned Coal Mines
		1012, // Imperial Tomb
		1013, // Ravaged Innadril
		1015, // Otherworldly Atelia Refinery
		1016, // Time Rift
		1020, // Tower of Insolence
		1025, // War Fortress Superion
	};
	
	public TimedHunting()
	{
		super(TEMPLATE_IDS);
		addStartNpc(FIOREN, JAMOA, PANTHEON, INVESTIGATORS_TELEPORTER, SEARCH_TEAM_TELEPORTER, LIONEL_HUNTER, ATELIA_REFINERY_TELEPORT_DEVICE, GATEKEEPER_ZIGGURAT, IVORY_TOWER_RESEARCHER, PRIESTS_TELEPORTER, PRIEST_GUARD, NEREA);
		addFirstTalkId(FIOREN, JAMOA, PANTHEON, INVESTIGATORS_TELEPORTER, SEARCH_TEAM_TELEPORTER, LIONEL_HUNTER, ATELIA_REFINERY_TELEPORT_DEVICE, GATEKEEPER_ZIGGURAT, IVORY_TOWER_RESEARCHER, PRIESTS_TELEPORTER, PRIEST_GUARD, NEREA);
		addFirstTalkId(RESEARCHERS_TELEPORTER_105, RESEARCHERS_TELEPORTER_110, RESEARCHERS_TELEPORTER_115, RESEARCHERS_TELEPORTER_120);
		addFirstTalkId(EXPEDITION_TELEPORTER_105, EXPEDITION_TELEPORTER_110, EXPEDITION_TELEPORTER_115, EXPEDITION_TELEPORTER_120);
		addFirstTalkId(OVERSEER_TELEPORTER_105, OVERSEER_TELEPORTER_110, OVERSEER_TELEPORTER_115, OVERSEER_TELEPORTER_120);
		addFirstTalkId(INVESTIGATORS_TELEPORTER);
		addFirstTalkId(SEARCH_TEAM_TELEPORTER);
		addFirstTalkId(PATROL_TELEPORTER_105, PATROL_TELEPORTER_115, PATROL_TELEPORTER_120);
		addFirstTalkId(ATELIA_REFINERY_TELEPORT_DEVICE);
		addFirstTalkId(GATEKEEPER_ZIGGURAT, IVORY_TOWER_RESEARCHER);
		addFirstTalkId(PRIESTS_TELEPORTER, PRIEST_GUARD, NEREA);
		addTalkId(FIOREN, JAMOA, PANTHEON, INVESTIGATORS_TELEPORTER, SEARCH_TEAM_TELEPORTER, LIONEL_HUNTER, ATELIA_REFINERY_TELEPORT_DEVICE, GATEKEEPER_ZIGGURAT, IVORY_TOWER_RESEARCHER, PRIESTS_TELEPORTER, PRIEST_GUARD, NEREA);
		addTalkId(RESEARCHERS_TELEPORTER_105, RESEARCHERS_TELEPORTER_110, RESEARCHERS_TELEPORTER_115, RESEARCHERS_TELEPORTER_120);
		addTalkId(EXPEDITION_TELEPORTER_105, EXPEDITION_TELEPORTER_110, EXPEDITION_TELEPORTER_115, EXPEDITION_TELEPORTER_120);
		addTalkId(OVERSEER_TELEPORTER_105, OVERSEER_TELEPORTER_110, OVERSEER_TELEPORTER_115, OVERSEER_TELEPORTER_120);
		addTalkId(INVESTIGATORS_TELEPORTER);
		addTalkId(SEARCH_TEAM_TELEPORTER);
		addTalkId(PATROL_TELEPORTER_105, PATROL_TELEPORTER_115, PATROL_TELEPORTER_120);
		addTalkId(ATELIA_REFINERY_TELEPORT_DEVICE);
		addTalkId(GATEKEEPER_ZIGGURAT, IVORY_TOWER_RESEARCHER);
		addTalkId(PRIESTS_TELEPORTER, PRIEST_GUARD, NEREA);
		addKillId(PANTHEON_MONSTERS);
		addKillId(INSOLENCE_RELIC_1, INSOLENCE_RELIC_2, INSOLENCE_RELIC_3, INSOLENCE_RELIC_4, INSOLENCE_RELIC_5, INSOLENCE_RELIC_6);
		addKillId(INSOLENCE_KEEPER_1, INSOLENCE_KEEPER_2, INSOLENCE_KEEPER_3);
		addKillId(INSOLENCE_MONSTERS);
		addKillId(SUPERION_GUARDIAN, SUPERION_ARCHER, SUPERION_MAGE);
		addSpawnId(BATTLEGROUND_GUARDS);
		addSpawnId(INSOLENCE_KEEPERS);
		addSpawnId(INSOLENCE_MONSTERS);
		addSpawnId(SUPERION_MONSTERS);
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
		
		if (event.equals("toFieldOfSilence"))
		{
			player.teleToLocation(FIELD_OF_SILENCE);
		}
		else if (event.equals("toFieldOfWhispers"))
		{
			if (player.getLevel() < 110)
			{
				return "34646-1.html";
			}
			
			player.teleToLocation(FIELD_OF_WHISPERS);
		}
		else if (event.equals("toAlligatorBeach"))
		{
			if (player.getLevel() < 115)
			{
				return "34646-1.html";
			}
			
			player.teleToLocation(ALLIGATOR_BEACH);
		}
		else if (event.equals("toAlligatorIsland"))
		{
			if (player.getLevel() < 120)
			{
				return "34646-1.html";
			}
			
			player.teleToLocation(ALLIGATOR_ISLAND);
		}
		
		return null;
	}
	
	@Override
	public void onKill(Npc npc, Player killer, boolean isPet)
	{
		final Instance world = npc.getInstanceWorld();
		final StatSet npcVars = npc.getVariables();
		final boolean chaosPower = npcVars.getBoolean("CHAOS_POWER", false);
		if (isInInstance(world))
		{
			// Pantheon buff.
			if (PANTHEON_MONSTERS.contains(npc.getId()) && (getRandom(100) < 5) && !killer.isAffectedBySkill(MORE_ADENA.getSkillId()))
			{
				MORE_ADENA.getSkill().applyEffects(killer, killer);
			}
			
			// Chaos Aether drop.
			if ((INSOLENCE_MONSTERS.contains(npc.getId()) && chaosPower) //
				|| (((npc.getId() == SUPERION_GUARDIAN) || (npc.getId() == SUPERION_ARCHER) || (npc.getId() == SUPERION_MAGE)) && (getRandom(100) < 20)))
			{
				giveItems(killer, CHAOS_AETHER);
			}
			
			// Summon mobs on relic or keeper kill.
			switch (npc.getId())
			{
				case INSOLENCE_RELIC_1:
				{
					addSpawn(23853, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23853, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23853, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23854, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23854, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23854, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23855, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23855, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23855, npc.getLocation(), true, 20000, false, world.getId());
					break;
				}
				case INSOLENCE_RELIC_2:
				{
					addSpawn(23857, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23857, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23857, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23858, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23858, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23858, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23859, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23859, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23859, npc.getLocation(), true, 20000, false, world.getId());
					break;
				}
				case INSOLENCE_RELIC_3:
				{
					addSpawn(23861, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23861, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23861, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23862, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23862, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23862, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23863, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23863, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(23863, npc.getLocation(), true, 20000, false, world.getId());
					break;
				}
				case INSOLENCE_RELIC_4:
				{
					addSpawn(INSOLENCE_WATCHER_4, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_4, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_4, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_4, npc.getLocation(), true, 20000, false, world.getId());
					break;
				}
				case INSOLENCE_RELIC_5:
				{
					addSpawn(INSOLENCE_WATCHER_5, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_5, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_5, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_5, npc.getLocation(), true, 20000, false, world.getId());
					break;
				}
				case INSOLENCE_RELIC_6:
				{
					addSpawn(INSOLENCE_WATCHER_6, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_6, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_6, npc.getLocation(), true, 20000, false, world.getId());
					addSpawn(INSOLENCE_WATCHER_6, npc.getLocation(), true, 20000, false, world.getId());
					break;
				}
				case INSOLENCE_KEEPER_1:
				{
					switch (getRandom(3))
					{
						case 0:
						{
							addSpawn(23853, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23853, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23853, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
						case 1:
						{
							addSpawn(23854, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23854, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23854, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
						case 2:
						{
							addSpawn(23855, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23855, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23855, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
					}
					break;
				}
				case INSOLENCE_KEEPER_2:
				{
					switch (getRandom(3))
					{
						case 0:
						{
							addSpawn(23857, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23857, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23857, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
						case 1:
						{
							addSpawn(23858, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23858, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23858, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
						case 2:
						{
							addSpawn(23859, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23859, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23859, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
					}
					break;
				}
				case INSOLENCE_KEEPER_3:
				{
					switch (getRandom(3))
					{
						case 0:
						{
							addSpawn(23861, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23861, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23861, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
						case 1:
						{
							addSpawn(23862, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23862, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23862, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
						case 2:
						{
							addSpawn(23863, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23863, npc.getLocation(), true, 20000, false, world.getId());
							addSpawn(23863, npc.getLocation(), true, 20000, false, world.getId());
							break;
						}
					}
					break;
				}
				// Spawn next mob on Superion
				case SUPERION_MAGE:
				{
					addSpawn(SUPERION_GUARDIAN, npc.getLocation(), false, 0, false, world.getId());
					break;
				}
				case SUPERION_GUARDIAN:
				{
					addSpawn(SUPERION_ARCHER, npc.getLocation(), false, 0, false, world.getId());
					break;
				}
				case SUPERION_ARCHER:
				{
					addSpawn(SUPERION_MAGE, npc.getLocation(), false, 0, false, world.getId());
					break;
				}
			}
		}
	}
	
	@Override
	public void onSpawn(Npc npc)
	{
		final Instance world = npc.getInstanceWorld();
		final StatSet npcVars = npc.getVariables();
		if (world != null)
		{
			if ((INSOLENCE_MONSTERS.contains(npc.getId()) && (getRandom(100) < CHAOS_PROBABILITY)) //
				|| SUPERION_MONSTERS.contains(npc.getId()))
			{
				npcVars.set("CHAOS_POWER", true);
				CHAOS_POWER.getSkill().applyEffects(npc, npc);
			}
			
			if (INSOLENCE_MONSTERS.contains(npc.getId()) //
				|| INSOLENCE_KEEPERS.contains(npc.getId()) //
				|| BATTLEGROUND_GUARDS.contains(npc.getId()) //
				|| SUPERION_MONSTERS.contains(npc.getId()))
			{
				npc.setRandomWalking(false);
			}
			
			if ((npc.getId() == INSOLENCE_WATCHER_4) || (npc.getId() == INSOLENCE_WATCHER_5) || (npc.getId() == INSOLENCE_WATCHER_6))
			{
				npc.broadcastSay(ChatType.NPC_GENERAL, NpcStringId.STOP_THE_INVADERS_2);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new TimedHunting();
	}
}
