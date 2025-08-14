/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.serverpackets.ability;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.Config;
import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.data.xml.AbilityPointsData;
import org.l2jmobius.gameserver.data.xml.SkillTreeData;
import org.l2jmobius.gameserver.model.SkillLearn;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.skill.Skill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.ServerPacket;

/**
 * @author Sdw
 */
public class ExAcquireAPSkillList extends ServerPacket
{
	private final int _abilityPoints, _usedAbilityPoints;
	private final long _price;
	private final boolean _enable;
	private final List<Skill> _skills = new ArrayList<>();
	
	public ExAcquireAPSkillList(Player player)
	{
		_abilityPoints = player.getAbilityPoints();
		_usedAbilityPoints = player.getAbilityPointsUsed();
		_price = AbilityPointsData.getInstance().getPrice(_abilityPoints);
		for (SkillLearn sk : SkillTreeData.getInstance().getAbilitySkillTree().values())
		{
			final Skill knownSkill = player.getKnownSkill(sk.getSkillId());
			if (knownSkill != null)
			{
				if (knownSkill.getLevel() == sk.getSkillLevel())
				{
					_skills.add(knownSkill);
				}
			}
		}
		
		_enable = (!player.isSubClassActive() || player.isDualClassActive()) && (player.getLevel() >= 99) && (player.getNobleLevel() > 0);
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_ACQUIRE_AP_SKILL_LIST.writeId(this, buffer);
		buffer.writeInt(_enable);
		buffer.writeLong(Config.ABILITY_POINTS_RESET_ADENA);
		buffer.writeLong(_price);
		buffer.writeInt(Config.ABILITY_MAX_POINTS);
		buffer.writeInt(_abilityPoints);
		buffer.writeInt(_usedAbilityPoints);
		buffer.writeInt(_skills.size());
		for (Skill skill : _skills)
		{
			buffer.writeInt(skill.getId());
			buffer.writeInt(skill.getLevel());
		}
	}
}
