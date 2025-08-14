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
package org.l2jmobius.gameserver.network.serverpackets.dye;

import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.variables.AccountVariables;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.ServerPacket;

/**
 * @author CostyKiller
 */
public class DyeEffectList extends ServerPacket
{
	private final Player _player;
	private final int _category;
	// private final int _slotId;
	private final int _slot1Level;
	private final int _slot2Level;
	private final int _slot3Level;
	private final int _challengeCountForSlot1;
	private final int _challengeCountForSlot2;
	private final int _challengeCountForSlot3;
	private final int _hiddenSkillLevelForSlot1;
	private final int _hiddenSkillLevelForSlot2;
	private final int _hiddenSkillLevelForSlot3;
	
	public DyeEffectList(Player player)
	{
		_player = player;
		_category = 1;
		// _slotId = 1;
		_slot1Level = _player.getAccountVariables().getInt(AccountVariables.DYE_LEVEL_FOR_SLOT_ + 1, 0);
		_slot2Level = _player.getAccountVariables().getInt(AccountVariables.DYE_LEVEL_FOR_SLOT_ + 2, 0);
		_slot3Level = _player.getAccountVariables().getInt(AccountVariables.DYE_LEVEL_FOR_SLOT_ + 3, 0);
		_challengeCountForSlot1 = _player.getAccountVariables().getInt(AccountVariables.DYE_CHALLENGE_COUNT_FOR_SLOT_ + 1, 30);
		_challengeCountForSlot2 = _player.getAccountVariables().getInt(AccountVariables.DYE_CHALLENGE_COUNT_FOR_SLOT_ + 2, 30);
		_challengeCountForSlot3 = _player.getAccountVariables().getInt(AccountVariables.DYE_CHALLENGE_COUNT_FOR_SLOT_ + 3, 30);
		_hiddenSkillLevelForSlot1 = _player.getAccountVariables().getInt(AccountVariables.DYE_HIDDEN_SKILL_LEVEL_FOR_SLOT_ + 1, 0);
		_hiddenSkillLevelForSlot2 = _player.getAccountVariables().getInt(AccountVariables.DYE_HIDDEN_SKILL_LEVEL_FOR_SLOT_ + 2, 0);
		_hiddenSkillLevelForSlot3 = _player.getAccountVariables().getInt(AccountVariables.DYE_HIDDEN_SKILL_LEVEL_FOR_SLOT_ + 3, 0);
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_DYEEFFECT_LIST.writeId(this, buffer);
		buffer.writeInt(3); // array size
		
		// Slot 1
		buffer.writeInt(_category); // int nCategory; Only 1 category atm
		buffer.writeInt(1); // int nSlotID; Slot 1 Seal - Giant's Power
		buffer.writeInt(_slot1Level); // int nSlotLevel; 0 / 30 max
		buffer.writeInt(37073); // int nSkillID;
		buffer.writeInt(_slot1Level); // int nSkillLevel;
		buffer.writeInt(_hiddenSkillLevelForSlot1 != 0 ? 37066 : 0); // var int nHiddenSkillID;
		buffer.writeInt(_hiddenSkillLevelForSlot1); // int nHiddenSkillLevel;
		buffer.writeInt(_challengeCountForSlot1); // int nChallengeCount; Upgrade attempt left: 30 / 30 max
		
		// Slot 2
		buffer.writeInt(_category); // int nCategory; Only 1 category atm
		buffer.writeInt(2); // int nSlotID; Slot 2 Seal - Giant's Wisdom
		buffer.writeInt(_slot2Level); // int nSlotLevel; 0 / 30 max
		buffer.writeInt(37074); // int nSkillID;
		buffer.writeInt(_slot2Level); // int nSkillLevel;
		buffer.writeInt(_hiddenSkillLevelForSlot2 != 0 ? 37067 : 0); // int nHiddenSkillID;
		buffer.writeInt(_hiddenSkillLevelForSlot2); // int nHiddenSkillLevel;
		buffer.writeInt(_challengeCountForSlot2); // int nChallengeCount; Upgrade attempt left: 30 / 30 max
		
		// Slot 3
		buffer.writeInt(_category); // int nCategory; Only 1 category atm
		buffer.writeInt(3); // int nSlotID; Slot 3 Seal - Giant's Might
		buffer.writeInt(_slot3Level); // int nSlotLevel; 0 / 30 max
		buffer.writeInt(37075); // int nSkillID;
		buffer.writeInt(_slot3Level); // int nSkillLevel;
		buffer.writeInt(_hiddenSkillLevelForSlot3 != 0 ? 37068 : 0); // int nHiddenSkillID;
		buffer.writeInt(_hiddenSkillLevelForSlot3); // int nHiddenSkillLevel;
		buffer.writeInt(_challengeCountForSlot3); // int nChallengeCount; Upgrade attempt left: 30 / 30 max
	}
}
