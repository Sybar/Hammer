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

import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.ServerPacket;

/**
 * @author Atronic
 */
public class ExRelicsPointInfo extends ServerPacket
{
	private final int _relicCombinationGrade3Points;
	private final int _relicCombinationGrade4Points;
	
	public ExRelicsPointInfo(Player player)
	{
		_relicCombinationGrade3Points = player.getVariables().getInt(PlayerVariables.RELICS_COMBINATION_GRADE_3_POINTS, 0);
		_relicCombinationGrade4Points = player.getVariables().getInt(PlayerVariables.RELICS_COMBINATION_GRADE_4_POINTS, 0);
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_RELICS_POINT_INFO.writeId(this, buffer);
		
		buffer.writeInt(2);
		
		buffer.writeInt(3); // Grade
		buffer.writeInt(_relicCombinationGrade3Points); // CurrentCount
		buffer.writeInt(20); // OneTimeCount
		buffer.writeInt(220); // MaxCount
		
		buffer.writeInt(4); // Grade
		buffer.writeInt(_relicCombinationGrade4Points); // CurrentCount
		buffer.writeInt(20); // OneTimeCount
		buffer.writeInt(220); // MaxCount
	}
}
