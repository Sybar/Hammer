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
package org.l2jmobius.gameserver.network.clientpackets.homunculus;

import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.homunculus.Homunculus;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.ClientPacket;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExDeleteHomunculusDataResult;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomunculusPointInfo;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExShowHomunculusList;

/**
 * @author Mobius
 */
public class RequestExDeleteHomunculusData extends ClientPacket
{
	private int _slot;
	
	@Override
	protected void readImpl()
	{
		_slot = readInt(); // Position?
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getPlayer();
		if (player == null)
		{
			return;
		}
		
		final Homunculus homunculus = player.getHomunculusList().get(_slot);
		if (homunculus.isActive())
		{
			player.sendPacket(SystemMessageId.A_BONDED_HOMUNCULUS_CANNOT_BE_DESTROYED_BREAK_THE_BONDS_AND_TRY_AGAIN);
			return;
		}
		
		if (player.getHomunculusList().remove(homunculus))
		{
			long evolutionPoints = player.getVariables().getLong(PlayerVariables.HOMUNCULUS_EVOLUTION_POINTS, 0);
			switch (homunculus.getType())
			{
				case 0:
				{
					evolutionPoints += 1;
					break;
				}
				case 1:
				{
					evolutionPoints += 15;
					break;
				}
				case 2:
				{
					evolutionPoints += 500;
					break;
				}
				case 3:
				{
					evolutionPoints += 1000;
					break;
				}
			}
			
			player.getVariables().set(PlayerVariables.HOMUNCULUS_EVOLUTION_POINTS, evolutionPoints);
			player.sendPacket(new ExHomunculusPointInfo(player));
			player.sendPacket(new ExDeleteHomunculusDataResult());
			player.sendPacket(new ExShowHomunculusList(player));
		}
	}
}
