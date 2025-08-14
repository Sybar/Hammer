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
package ai.bosses.ChaosGolem;

import java.util.Calendar;

import org.l2jmobius.commons.time.TimeUtil;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.skill.holders.SkillHolder;

import ai.AbstractNpcAI;

/**
 * @author Tanatos
 */
public class ChaosGolem extends AbstractNpcAI
{
	private static final int GOLEM_17 = 29431;
	private static final int GOLEM_18 = 29432;
	private static final SkillHolder CHAOS_POWER = new SkillHolder(62051, 1);
	private static final Location GOLEM_17_SPAWN_LOC = new Location(86134, -121704, -4384);
	private static final Location GOLEM_18_SPAWN_LOC = new Location(94095, -120986, -4552);
	private static final int[] GOLEM_17_TIME =
	{
		14,
		00
	};
	private static final int[] GOLEM_18_TIME =
	{
		18,
		00
	};
	private static final long DESPAWN_DELAY = 3_600_000;
	
	private ChaosGolem()
	{
		addSpawnId(GOLEM_17, GOLEM_18);
		scheduleGolem17();
		LOGGER.info("Chaos Golem #17 will spawn at " + GOLEM_17_TIME[0] + ":" + GOLEM_17_TIME[1] + ".");
		scheduleGolem18();
		LOGGER.info("Chaos Golem #18 will spawn at " + GOLEM_18_TIME[0] + ":" + GOLEM_18_TIME[1] + ".");
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		switch (event)
		{
			case "spawnGolem17":
			{
				addSpawn(GOLEM_17, GOLEM_17_SPAWN_LOC.getX(), GOLEM_17_SPAWN_LOC.getY(), GOLEM_17_SPAWN_LOC.getZ(), 0, false, DESPAWN_DELAY, false);
				scheduleGolem17();
				break;
			}
			case "spawnGolem18":
			{
				addSpawn(GOLEM_18, GOLEM_18_SPAWN_LOC.getX(), GOLEM_18_SPAWN_LOC.getY(), GOLEM_18_SPAWN_LOC.getZ(), 0, false, DESPAWN_DELAY, false);
				scheduleGolem18();
				break;
			}
		}
		
		return super.onEvent(event, npc, player);
	}
	
	@Override
	public void onSpawn(Npc npc)
	{
		CHAOS_POWER.getSkill().applyEffects(npc, npc);
	}
	
	private void scheduleGolem17()
	{
		final Calendar nextGolem17Time = TimeUtil.getNextTime(GOLEM_17_TIME[0], GOLEM_17_TIME[1]);
		startQuestTimer("spawnGolem17", nextGolem17Time.getTimeInMillis() - System.currentTimeMillis(), null, null);
	}
	
	private void scheduleGolem18()
	{
		final Calendar nextGolem18Time = TimeUtil.getNextTime(GOLEM_18_TIME[0], GOLEM_18_TIME[1]);
		startQuestTimer("spawnGolem18", nextGolem18Time.getTimeInMillis() - System.currentTimeMillis(), null, null);
	}
	
	public static void main(String[] args)
	{
		new ChaosGolem();
	}
}
