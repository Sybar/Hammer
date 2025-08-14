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
package org.l2jmobius.gameserver.network.serverpackets.relics;

import org.l2jmobius.Config;
import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.ServerPacket;

/**
 * @author Brado
 */
public class ExRelicsSummonList extends ServerPacket
{
	public ExRelicsSummonList(Player player)
	{
	}
	
	@Override
	protected void writeImpl(GameClient client, WritableBuffer buffer)
	{
		if (!Config.RELIC_SYSTEM_ENABLED)
		{
			return;
		}
		
		ServerPackets.EX_RELICS_SUMMON_LIST.writeId(this, buffer);
		buffer.writeInt(10);
		
		// Shining Relics
		buffer.writeInt(1);
		buffer.writeInt(-1);
		buffer.writeInt(2);
		buffer.writeInt(-1);
		
		// Relics
		buffer.writeInt(3);
		buffer.writeInt(-1);
		buffer.writeInt(4);
		buffer.writeInt(-1);
		
		// Guaranteed Relics
		buffer.writeInt(5);
		buffer.writeInt(-1);
		buffer.writeInt(6);
		buffer.writeInt(-1);
		buffer.writeInt(7);
		buffer.writeInt(-1);
		
		// Attempt Relics
		buffer.writeInt(8);
		buffer.writeInt(-1);
		buffer.writeInt(9);
		buffer.writeInt(-1);
		buffer.writeInt(10);
		buffer.writeInt(-1);
	}
}
