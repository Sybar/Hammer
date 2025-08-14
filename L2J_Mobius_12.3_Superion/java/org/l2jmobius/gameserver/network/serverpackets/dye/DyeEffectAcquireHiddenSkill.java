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

import org.l2jmobius.Config;
import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.commons.util.Rnd;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.item.enums.ItemProcessType;
import org.l2jmobius.gameserver.model.skill.Skill;
import org.l2jmobius.gameserver.model.variables.AccountVariables;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.ServerPacket;

/**
 * @author CostyKiller
 */
public class DyeEffectAcquireHiddenSkill extends ServerPacket
{
	private final Player _player;
	private final int _category;
	private final int _slotId;
	private final int _slotLevel;
	private Boolean _success = false;
	private int _hiddenSkillId;
	private int _hiddenSkillLevel;
	
	public DyeEffectAcquireHiddenSkill(Player player, int category, int slotId)
	{
		_player = player;
		_category = category;
		_slotId = slotId;
		_slotLevel = _player.getAccountVariables().getInt(AccountVariables.DYE_LEVEL_FOR_SLOT_ + slotId, 1);
		
		switch (slotId)
		{
			case 1:
			{
				_hiddenSkillId = 37066; // Seal Heritage - Giant's Power
				break;
			}
			case 2:
			{
				_hiddenSkillId = 37067; // Seal Heritage - Giant's Wisdom
				break;
			}
			case 3:
			{
				_hiddenSkillId = 37068; // Seal Heritage - Giant's Might
				break;
			}
		}
		
		switch (_slotLevel)
		{
			case 26:
			case 27:
			{
				_hiddenSkillLevel = 1;
				break;
			}
			case 28:
			case 29:
			case 30:
			{
				_hiddenSkillLevel = 2;
				break;
			}
		}
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_DYEEFFECT_ACQUIRE_HIDDENSKILL.writeId(this, buffer);
		
		if ((_slotLevel >= 26) && (_player.getInventory().getItemByItemId(Config.DYE_ENCHANT_HIDDEN_SKILL_ITEM_ID).getCount() > Config.DYE_ENCHANT_HIDDEN_SKILL_ITEM_COUNT))
		{
			if (Rnd.get(100) < Config.DYE_ENCHANT_HIDDEN_SKILL_CHANCE)
			{
				_success = true;
				_player.getAccountVariables().set(AccountVariables.DYE_HIDDEN_SKILL_LEVEL_FOR_SLOT_ + _slotId, _hiddenSkillLevel);
				
				// Add skill here.
				final Skill dyeHiddenSkill = SkillData.getInstance().getSkill(_hiddenSkillId, _hiddenSkillLevel);
				_player.addSkill(dyeHiddenSkill, true);
				
				// Check hidden skills here.
				final int hiddenPowerSkillLevel = _player.getAccountVariables().getInt(AccountVariables.DYE_HIDDEN_SKILL_LEVEL_FOR_SLOT_ + 1, 0);
				final int hiddenWisdomSkillLevel = _player.getAccountVariables().getInt(AccountVariables.DYE_HIDDEN_SKILL_LEVEL_FOR_SLOT_ + 2, 0);
				final int hiddenMightSkillLevel = _player.getAccountVariables().getInt(AccountVariables.DYE_HIDDEN_SKILL_LEVEL_FOR_SLOT_ + 3, 0);
				final int hiddenSealBuffLevel = hiddenPowerSkillLevel + hiddenWisdomSkillLevel + hiddenMightSkillLevel;
				
				// Add buff skill here.
				final Skill hiddenSealBuffSkill = SkillData.getInstance().getSkill(37072 /* Giant Seal Buff */, hiddenSealBuffLevel);
				_player.addSkill(hiddenSealBuffSkill, true);
			}
			
			_player.destroyItemByItemId(ItemProcessType.FEE, Config.DYE_ENCHANT_HIDDEN_SKILL_ITEM_ID, Config.DYE_ENCHANT_HIDDEN_SKILL_ITEM_COUNT, _player, true);
		}
		
		buffer.writeInt(_success);
		buffer.writeInt(_category);
		buffer.writeInt(_slotId);
		buffer.writeInt(_hiddenSkillId);
		buffer.writeInt(_hiddenSkillLevel);
	}
}
