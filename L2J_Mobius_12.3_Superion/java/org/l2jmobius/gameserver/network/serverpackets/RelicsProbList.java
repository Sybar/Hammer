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
package org.l2jmobius.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.l2jmobius.Config;
import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;

/**
 * @author Atronic
 */
public class RelicsProbList extends ServerPacket
{
	private final int _type;
	private final int _key;
	private final Map<Integer, Long> _relics = new HashMap<>();
	
	public RelicsProbList(int type, int key)
	{
		_type = type;
		_key = key;
		switch (key)
		{
			case 1:
			{
				for (int relicId : Stream.of(Config.D_GRADE_COMMON_RELICS, Config.D_GRADE_SHINING_RELICS, Config.NO_GRADE_COMMON_RELICS).flatMap(List::stream).collect(Collectors.toList()))
				{
					_relics.put(relicId, calculateChance(relicId));
				}
				break;
			}
			case 2:
			{
				for (int relicId : Stream.of(Config.C_GRADE_COMMON_RELICS, Config.C_GRADE_SHINING_RELICS, Config.D_GRADE_COMMON_RELICS, Config.D_GRADE_SHINING_RELICS).flatMap(List::stream).collect(Collectors.toList()))
				{
					_relics.put(relicId, calculateChance(relicId));
				}
				break;
			}
			case 3:
			{
				for (int relicId : Stream.of(Config.B_GRADE_COMMON_RELICS, Config.B_GRADE_SHINING_RELICS, Config.C_GRADE_COMMON_RELICS, Config.C_GRADE_SHINING_RELICS).flatMap(List::stream).collect(Collectors.toList()))
				{
					_relics.put(relicId, calculateChance(relicId));
				}
				break;
			}
			case 4:
			{
				for (int relicId : Stream.of(Config.A_GRADE_COMMON_RELICS, Config.B_GRADE_COMMON_RELICS, Config.B_GRADE_SHINING_RELICS).flatMap(List::stream).collect(Collectors.toList()))
				{
					_relics.put(relicId, calculateChance(relicId));
				}
				break;
			}
		}
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_RELICS_PROB_LIST.writeId(this, buffer);
		buffer.writeInt(_type);
		buffer.writeInt(_key);
		buffer.writeInt(_relics.size());
		for (Entry<Integer, Long> entry : _relics.entrySet())
		{
			buffer.writeInt(entry.getKey()); // relicId
			buffer.writeLong(entry.getValue()); // chance
		}
	}
	
	private long calculateChance(int relicId)
	{
		long chance = 0L;
		
		if (_key == 1) // No-Grade
		{
			if (Config.NO_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_NO_GRADE_INGREDIENTS_CHANCE_NO_GRADE * 100000000) / Config.NO_GRADE_COMMON_RELICS.size();
			}
			else if (Config.D_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_NO_GRADE_INGREDIENTS_CHANCE_D_GRADE * 100000000) / Config.D_GRADE_COMMON_RELICS.size();
			}
			else if (Config.D_GRADE_SHINING_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_NO_GRADE_INGREDIENTS_CHANCE_SHINING_D_GRADE * 100000000) / Config.D_GRADE_SHINING_RELICS.size();
			}
		}
		else if (_key == 2) // D-Grade
		{
			if (Config.D_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_D_GRADE_INGREDIENTS_CHANCE_D_GRADE * 100000000) / Config.D_GRADE_COMMON_RELICS.size();
			}
			else if (Config.D_GRADE_SHINING_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_D_GRADE_INGREDIENTS_CHANCE_SHINING_D_GRADE * 100000000) / Config.D_GRADE_SHINING_RELICS.size();
			}
			else if (Config.C_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_D_GRADE_INGREDIENTS_CHANCE_C_GRADE * 100000000) / Config.C_GRADE_COMMON_RELICS.size();
			}
			else if (Config.C_GRADE_SHINING_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_D_GRADE_INGREDIENTS_CHANCE_SHINING_C_GRADE * 100000000) / Config.C_GRADE_SHINING_RELICS.size();
			}
		}
		else if (_key == 3) // C-Grade
		{
			if (Config.C_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_C_GRADE_INGREDIENTS_CHANCE_C_GRADE * 100000000) / Config.C_GRADE_COMMON_RELICS.size();
			}
			else if (Config.C_GRADE_SHINING_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_C_GRADE_INGREDIENTS_CHANCE_SHINING_C_GRADE * 100000000) / Config.C_GRADE_SHINING_RELICS.size();
			}
			else if (Config.B_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_C_GRADE_INGREDIENTS_CHANCE_B_GRADE * 100000000) / Config.B_GRADE_COMMON_RELICS.size();
			}
			else if (Config.B_GRADE_SHINING_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_C_GRADE_INGREDIENTS_CHANCE_SHINING_B_GRADE * 100000000) / Config.B_GRADE_SHINING_RELICS.size();
			}
		}
		else if (_key == 4) // B-Grade
		{
			if (Config.B_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_B_GRADE_INGREDIENTS_CHANCE_B_GRADE * 100000000) / Config.B_GRADE_COMMON_RELICS.size();
			}
			else if (Config.B_GRADE_SHINING_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_B_GRADE_INGREDIENTS_CHANCE_SHINING_B_GRADE * 100000000) / Config.B_GRADE_SHINING_RELICS.size();
			}
			else if (Config.A_GRADE_COMMON_RELICS.contains(relicId))
			{
				chance = (Config.RELIC_COMPOUND_B_GRADE_INGREDIENTS_CHANCE_A_GRADE * 100000000) / Config.A_GRADE_COMMON_RELICS.size();
			}
		}
		
		return chance;
	}
}
