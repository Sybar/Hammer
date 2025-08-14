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
package ai.bosses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.network.NpcStringId;

import ai.AbstractNpcAI;

/**
 * @author Tanatos
 */
public class SpawnAnnouncement extends AbstractNpcAI
{
	private static final int STAKATO_CROG = 29426;
	private static final int DEMONIC_VENOM = 29427;
	private static final int AVENGER_GRAFF = 29428;
	private static final int MAD_CULLAN = 29429;
	private static final int ARROGANT_LEBRUUM = 29430;
	private static final int GOLEM_17 = 29431;
	private static final int GOLEM_18 = 29432;
	private static final Set<Integer> RAID_BOSSES = new HashSet<>();
	static
	{
		RAID_BOSSES.add(STAKATO_CROG);
		RAID_BOSSES.add(DEMONIC_VENOM);
		RAID_BOSSES.add(AVENGER_GRAFF);
		RAID_BOSSES.add(MAD_CULLAN);
		RAID_BOSSES.add(ARROGANT_LEBRUUM);
		RAID_BOSSES.add(GOLEM_17);
		RAID_BOSSES.add(GOLEM_18);
	}
	private static final NpcStringId[] SPAWN_MSG =
	{
		NpcStringId.MUTANT_STAKATO_QUEEN_HAS_APPEARED_IN_THE_SWAMP_OF_SCREAMS,
		NpcStringId.DEMONIC_VENOM_HAS_APPEARED_IN_THE_FIELDS_OF_MASSACRE,
		NpcStringId.AVENGER_GRAFF_HAS_APPEARED_IN_THE_CEMETERY,
		NpcStringId.MAD_CULLAN_HAS_APPEARED_IN_THE_HOT_SPRINGS,
		NpcStringId.ARROGANT_LEBRUUM_HAS_APPEARED_ON_THE_WALL_OF_ARGOS,
		NpcStringId.CHAOS_GOLEM_17_HAS_APPEARED_IN_THE_PAVEL_RUINS,
		NpcStringId.CHAOS_GOLEM_18_HAS_APPEARED_IN_THE_PAVEL_RUINS
	};
	
	public SpawnAnnouncement()
	{
		addSpawnId(RAID_BOSSES);
	}
	
	@Override
	public void onSpawn(Npc npc)
	{
		final int index = new ArrayList<>(RAID_BOSSES).indexOf(npc.getId());
		if (index != -1)
		{
			final NpcStringId message = SPAWN_MSG[index];
			World.getInstance().getPlayers().forEach(p -> showOnScreenMsg(p, message, 2, 10000, true));
		}
	}
	
	public static void main(String[] args)
	{
		new SpawnAnnouncement();
	}
}
