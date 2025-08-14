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
public class DyeEffectEnchantNormalSkill extends ServerPacket
{
	private final Player _player;
	private final int _category;
	private final int _slotId;
	private int _slotLevel;
	private int _skillId;
	private final int _skillLevel;
	private int _challengeCount;
	private Boolean _success = false;
	
	public DyeEffectEnchantNormalSkill(Player player, int category, int slotId)
	{
		_player = player;
		_category = category;
		_slotId = slotId;
		_slotLevel = _player.getAccountVariables().getInt(AccountVariables.DYE_LEVEL_FOR_SLOT_ + slotId, 0);
		switch (slotId)
		{
			case 1:
			{
				_skillId = 37073; // Seal - Giant's Power
				break;
			}
			case 2:
			{
				_skillId = 37074; // Seal - Giant's Wisdom
				break;
			}
			case 3:
			{
				_skillId = 37075; // Seal - Giant's Might
				break;
			}
		}
		
		_skillLevel = _slotLevel == 0 ? 1 : _slotLevel + 1;
		_challengeCount = _player.getAccountVariables().getInt(AccountVariables.DYE_CHALLENGE_COUNT_FOR_SLOT_ + slotId, 30);
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_DYEEFFECT_ENCHANT_NORMALSKILL.writeId(this, buffer);
		if ((_player.getInventory().getItemByItemId(Config.DYE_ENCHANT_NORMAL_SKILL_ITEM_ID).getCount() > Config.DYE_ENCHANT_NORMAL_SKILL_ITEM_COUNT))
		{
			if (Rnd.get(100) < Config.DYE_ENCHANT_NORMAL_SKILL_CHANCE)
			{
				_success = true;
				_slotLevel = _slotLevel + 1;
				_player.getAccountVariables().set(AccountVariables.DYE_LEVEL_FOR_SLOT_ + _slotId, _slotLevel);
				
				// Add skill here.
				final Skill dyeSkill = SkillData.getInstance().getSkill(_skillId, _skillLevel);
				_player.addSkill(dyeSkill, true);
			}
			
			_player.destroyItemByItemId(ItemProcessType.FEE, Config.DYE_ENCHANT_NORMAL_SKILL_ITEM_ID, Config.DYE_ENCHANT_NORMAL_SKILL_ITEM_COUNT, _player, true);
		}
		
		_challengeCount = _challengeCount - 1;
		_player.getAccountVariables().set(AccountVariables.DYE_CHALLENGE_COUNT_FOR_SLOT_ + _slotId, _challengeCount);
		
		buffer.writeInt(_success);
		buffer.writeInt(_category);
		buffer.writeInt(_slotId);
		buffer.writeInt(_slotLevel);
		buffer.writeInt(_skillId);
		buffer.writeInt(_skillLevel);
		buffer.writeInt(_challengeCount);
	}
}
