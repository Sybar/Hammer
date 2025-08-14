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
package instances.ResidenceOfKingIgnis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.instancezone.Instance;
import org.l2jmobius.gameserver.model.skill.Skill;
import org.l2jmobius.gameserver.model.skill.SkillCaster;
import org.l2jmobius.gameserver.model.skill.holders.SkillHolder;
import org.l2jmobius.gameserver.network.enums.ChatType;

import instances.AbstractInstance;

/**
 * @author RobikBobik
 * @NOTE: Retail like working - I get informations from wiki and youtube video.
 */
public class ResidenceOfKingIgnis extends AbstractInstance
{
	// NPCs
	private static final int TARA = 34047;
	private static final int FREYA = 29109;
	private static final int IGNIS = 29105;
	
	// Skills
	private static final SkillHolder FIRE_RAGE = new SkillHolder(50050, 1);
	private static final SkillHolder FREYA_SAFETY_ZONE = new SkillHolder(50052, 1);
	
	// Misc
	private static final int TEMPLATE_ID = 195;
	private static final Map<Player, Integer> _playerFireRage = new ConcurrentHashMap<>();
	
	public ResidenceOfKingIgnis()
	{
		super(TEMPLATE_ID);
		addStartNpc(TARA);
		addTalkId(FREYA);
		addKillId(IGNIS);
		addAttackId(IGNIS);
		addInstanceLeaveId(TEMPLATE_ID);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		switch (event)
		{
			case "ENTER":
			{
				enterInstance(player, npc, TEMPLATE_ID);
				_playerFireRage.put(player, 0);
				break;
			}
			case "REMOVE_FIRE_RAGE":
			{
				if (player.isAffectedBySkill(FIRE_RAGE.getSkillId()))
				{
					final int playerFireRage = _playerFireRage.getOrDefault(player, 0);
					if (playerFireRage < 5)
					{
						_playerFireRage.put(player, playerFireRage + 1);
						
						final Instance world = player.getInstanceWorld();
						if (world != null)
						{
							final Npc Freya = world.getNpc(FREYA);
							if (Freya != null)
							{
								Freya.doCast(FREYA_SAFETY_ZONE.getSkill());
								Freya.broadcastSay(ChatType.NPC_SHOUT, "Bless with you. Let's finish fight!");
							}
						}
						break;
					}
					
					npc.broadcastSay(ChatType.NPC_SHOUT, "You cannot use my power again.");
					player.sendMessage("Freya: You cannot use my power again.");
					break;
				}
				
				npc.broadcastSay(ChatType.NPC_SHOUT, "I help you only when you affected by Fire Rage skill.");
				break;
			}
			case "CAST_FIRE_RAGE":
			{
				if (SkillCaster.checkUseConditions(npc, FIRE_RAGE.getSkill()))
				{
					npc.doCast(FIRE_RAGE.getSkill());
				}
				break;
			}
		}
		
		return null;
	}
	
	@Override
	public void onAttack(Npc npc, Player attacker, int damage, boolean isSummon, Skill skill)
	{
		startQuestTimer("CAST_FIRE_RAGE", 1000, npc, null);
	}
	
	@Override
	public void onKill(Npc npc, Player player, boolean isSummon)
	{
		final Instance world = npc.getInstanceWorld();
		if (world != null)
		{
			world.finishInstance();
		}
	}
	
	public static void main(String[] args)
	{
		new ResidenceOfKingIgnis();
	}
}
