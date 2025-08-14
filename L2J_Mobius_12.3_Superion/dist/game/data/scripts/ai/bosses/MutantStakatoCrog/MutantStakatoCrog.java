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
package ai.bosses.MutantStakatoCrog;

import java.util.Calendar;

import org.l2jmobius.commons.time.TimeUtil;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;

import ai.AbstractNpcAI;

/**
 * @author Tanatos
 */
public class MutantStakatoCrog extends AbstractNpcAI
{
	private static final int STAKATO_CROG = 29426;
	private static final Location[] STAKATO_CROG_SPAWN_LOC =
	{
		new Location(73629, -57308, -3105),
		new Location(77761, -41481, -3228),
		new Location(71804, -43440, -3229)
	};
	private static final long DESPAWN_DELAY = 1_200_000;
	
	private static final SpawnEvent[] SPAWN_EVENTS =
	{
		new SpawnEvent("spawnFirstCrog", 13, 0),
		new SpawnEvent("spawnSecondCrog", 16, 0),
		new SpawnEvent("spawnThirdCrog", 20, 0)
	};
	
	private int _location;
	
	private MutantStakatoCrog()
	{
		for (SpawnEvent event : SPAWN_EVENTS)
		{
			scheduleSpawn(event);
		}
		
		LOGGER.info("Mutant Stakato Crog will spawn at 13:00, 16:00 and 20:00.");
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		for (SpawnEvent spawnEvent : SPAWN_EVENTS)
		{
			if (spawnEvent.name.equals(event))
			{
				_location = getRandom(2);
				addSpawn(STAKATO_CROG, STAKATO_CROG_SPAWN_LOC[_location].getX(), STAKATO_CROG_SPAWN_LOC[_location].getY(), STAKATO_CROG_SPAWN_LOC[_location].getZ(), 0, false, DESPAWN_DELAY, false);
				
				scheduleSpawn(spawnEvent);
				break;
			}
		}
		
		return super.onEvent(event, npc, player);
	}
	
	private void scheduleSpawn(SpawnEvent spawnEvent)
	{
		final Calendar time = TimeUtil.getNextTime(spawnEvent.hour, spawnEvent.minute);
		startQuestTimer(spawnEvent.name, time.getTimeInMillis() - System.currentTimeMillis(), null, null);
	}
	
	private static class SpawnEvent
	{
		String name;
		int hour;
		int minute;
		
		SpawnEvent(String name, int hour, int minute)
		{
			this.name = name;
			this.hour = hour;
			this.minute = minute;
		}
	}
	
	public static void main(String[] args)
	{
		new MutantStakatoCrog();
	}
}
