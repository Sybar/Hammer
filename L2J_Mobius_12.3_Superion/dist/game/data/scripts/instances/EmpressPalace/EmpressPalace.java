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
package instances.EmpressPalace;

import java.util.List;

import org.l2jmobius.gameserver.managers.InstanceManager;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.groups.Party;
import org.l2jmobius.gameserver.model.instancezone.Instance;
import org.l2jmobius.gameserver.model.item.holders.ItemHolder;
import org.l2jmobius.gameserver.model.skill.AbnormalVisualEffect;
import org.l2jmobius.gameserver.model.skill.Skill;
import org.l2jmobius.gameserver.model.skill.SkillCaster;
import org.l2jmobius.gameserver.model.skill.holders.SkillHolder;
import org.l2jmobius.gameserver.network.NpcStringId;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.enums.Movie;
import org.l2jmobius.gameserver.network.serverpackets.ExShowScreenMessage;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

import instances.AbstractInstance;

/**
 * @author Tanatos
 * @URL https://www.youtube.com/watch?v=IfSt5NeTelc
 * @URL https://l2central.info/main/articles/2934.html
 */
public class EmpressPalace extends AbstractInstance
{
	// NPCs
	private static final int CONTROLLER = 18017;
	private static final int DUMMY = 29052;
	private static final int RAMONA_DUMMY = 19648;
	private static final int RAMONA_1_EASY = 26541;
	private static final int RAMONA_2_EASY = 26542;
	private static final int RAMONA_1_NORMAL = 26543;
	private static final int RAMONA_2_NORMAL = 26544;
	private static final int RAMONA_FINAL_NORMAL = 26545;
	
	// Skills
	// private static final SkillHolder RAMONA_SPAWN = new SkillHolder(62102, 1); // Giant Empress' Teleport
	// 1st Ramona
	private static final SkillHolder RAMONA_MAGIC = new SkillHolder(62101, 1); // Giant Empress' Magic
	private static final SkillHolder RAMONA_THUNDER_EASY = new SkillHolder(62103, 1); // Giant Empress' Slow
	private static final SkillHolder RAMONA_THUNDER_NORMAL = new SkillHolder(62103, 2); // Giant Empress' Slow
	private static final SkillHolder RAMONA_CHAIN = new SkillHolder(62104, 1); // Giant Empress' Chain Smash
	private static final SkillHolder RAMONA_CHAOS = new SkillHolder(62105, 1); // Giant Empress' Chaos
	// 2nd Ramona
	private static final SkillHolder RAMONA_YELLOW_ARROW_CAST = new SkillHolder(62106, 1); // Giant Empress' Lightning Arrow
	private static final SkillHolder RAMONA_BLUE_ARROW_CAST = new SkillHolder(62107, 1); // Giant Empress' Blue Arrow
	private static final SkillHolder RAMONA_PURPLE_ARROW_CAST = new SkillHolder(62108, 1); // Giant Empress' Purple Arrow
	private static final SkillHolder RAMONA_RED_ARROW_CAST = new SkillHolder(62109, 1); // Giant Empress' Red Arrow
	private static final SkillHolder RAMONA_YELLOW_ARROW_EASY = new SkillHolder(62110, 1); // Giant Empress' Lightning Arrow
	private static final SkillHolder RAMONA_YELLOW_ARROW_NORMAL = new SkillHolder(62110, 2); // Giant Empress' Lightning Arrow
	private static final SkillHolder RAMONA_BLUE_ARROW_EASY = new SkillHolder(62111, 1); // Giant Empress' Blue Arrow
	private static final SkillHolder RAMONA_BLUE_ARROW_NORMAL = new SkillHolder(62111, 2); // Giant Empress' Blue Arrow
	private static final SkillHolder RAMONA_PURPLE_ARROW_EASY = new SkillHolder(62112, 1); // Giant Empress' Purple Arrow
	private static final SkillHolder RAMONA_PURPLE_ARROW_NORMAL = new SkillHolder(62112, 2); // Giant Empress' Purple Arrow
	private static final SkillHolder RAMONA_RED_ARROW_EASY = new SkillHolder(62113, 1); // Giant Empress' Red Arrow
	private static final SkillHolder RAMONA_RED_ARROW_NORMAL = new SkillHolder(62113, 2); // Giant Empress' Red Arrow
	private static final SkillHolder RAMONA_FOCUS_SHOT = new SkillHolder(62114, 1); // Giant Empress' Focus Shot
	private static final SkillHolder RAMONA_STRIKE_EASY = new SkillHolder(62115, 1); // Giant Empress' Thunder Strike
	private static final SkillHolder RAMONA_STRIKE_NORMAL = new SkillHolder(62115, 2); // Giant Empress' Thunder Strike
	// 3rd Ramona
	private static final SkillHolder RAMONA_STRIKE = new SkillHolder(62116, 1); // Giant Empress' Lightning Strike
	private static final SkillHolder RAMONA_PATH = new SkillHolder(62117, 1); // Giant Empress' Lightning Path
	private static final SkillHolder RAMONA_DESPAIR_1 = new SkillHolder(62118, 1); // Giant Empress' Despair
	private static final SkillHolder RAMONA_DESPAIR_2 = new SkillHolder(62119, 1); // Giant Empress' Despair
	private static final SkillHolder RAMONA_DESPAIR_3 = new SkillHolder(62120, 1); // Giant Empress' Despair
	private static final SkillHolder RAMONA_FOCUS = new SkillHolder(62125, 1); // Giant Empress' Focus
	// Items
	private static final ItemHolder MAGIC_SPHERE = new ItemHolder(83272, 1);
	private static final ItemHolder TIME_STONE = new ItemHolder(83269, 1);
	
	// Misc
	private static final int TEMPLATE_ID_EASY = 344;
	private static final int TEMPLATE_ID_NORMAL = 345;
	
	public EmpressPalace()
	{
		super(TEMPLATE_ID_EASY, TEMPLATE_ID_NORMAL);
		addAttackId(RAMONA_1_EASY, RAMONA_2_EASY, RAMONA_1_NORMAL, RAMONA_2_NORMAL, RAMONA_FINAL_NORMAL);
		addKillId(CONTROLLER, RAMONA_2_EASY, RAMONA_FINAL_NORMAL);
		addSpawnId(CONTROLLER, DUMMY, RAMONA_DUMMY, RAMONA_1_EASY, RAMONA_2_EASY, RAMONA_1_NORMAL, RAMONA_2_NORMAL, RAMONA_FINAL_NORMAL);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		if (event.contains("enterInstance"))
		{
			final long currentTime = System.currentTimeMillis();
			if (player.isInParty())
			{
				final Party party = player.getParty();
				if (!party.isLeader(player))
				{
					player.sendPacket(SystemMessageId.ONLY_A_PARTY_LEADER_CAN_MAKE_THE_REQUEST_TO_ENTER);
					return null;
				}
				
				if (player.isInCommandChannel())
				{
					player.sendPacket(SystemMessageId.YOU_CANNOT_ENTER_AS_YOU_DON_T_MEET_THE_REQUIREMENTS);
					return null;
				}
				
				final List<Player> members = party.getMembers();
				for (Player member : members)
				{
					if (!member.isInsideRadius3D(npc, 1000))
					{
						player.sendMessage("Player " + member.getName() + " must come closer.");
						return null;
					}
					
					if ((currentTime < InstanceManager.getInstance().getInstanceTime(member, 344)) || (currentTime < InstanceManager.getInstance().getInstanceTime(member, 345)))
					{
						player.sendPacket(new SystemMessage(SystemMessageId.C1_CANNOT_ENTER_YET).addString(member.getName()));
						return null;
					}
				}
				
				for (Player member : members)
				{
					if (event.equals("enterInstanceEasy"))
					{
						enterInstance(member, npc, TEMPLATE_ID_EASY);
					}
					else
					{
						enterInstance(member, npc, TEMPLATE_ID_NORMAL);
					}
				}
			}
			else if (player.isGM())
			{
				if ((currentTime < InstanceManager.getInstance().getInstanceTime(player, 344)) || (currentTime < InstanceManager.getInstance().getInstanceTime(player, 345)))
				{
					player.sendPacket(new SystemMessage(SystemMessageId.C1_CANNOT_ENTER_YET).addString(player.getName()));
					return null;
				}
				
				if (event.equals("enterInstanceEasy"))
				{
					enterInstance(player, npc, TEMPLATE_ID_EASY);
				}
				else
				{
					enterInstance(player, npc, TEMPLATE_ID_NORMAL);
				}
			}
			else
			{
				player.sendPacket(SystemMessageId.YOU_ARE_NOT_IN_A_PARTY_SO_YOU_CANNOT_ENTER);
			}
			
			if (player.getInstanceWorld() != null)
			{
				startQuestTimer("startInstance", 10000, null, player);
				startQuestTimer("startTimer", 10000, null, player);
			}
		}
		else
		{
			final Instance world = player.getInstanceWorld();
			if (!isInInstance(world))
			{
				return null;
			}
			
			switch (event)
			{
				case "startInstance":
				{
					
					showOnScreenMsg(world, NpcStringId.YOU_CAN_STAY_IN_THE_ZONE_FOR_6_MIN_DESTROY_THE_MAGIC_DEVICE, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "startTimer":
				{
					startQuestTimer("fiveMin", 60000, null, player);
					startQuestTimer("fourMin", 120000, null, player);
					startQuestTimer("threeMin", 180000, null, player);
					startQuestTimer("twoMin", 240000, null, player);
					startQuestTimer("oneMin", 300000, null, player);
					startQuestTimer("tenSec", 350000, null, player);
					startQuestTimer("nineSec", 351000, null, player);
					startQuestTimer("eightSec", 352000, null, player);
					startQuestTimer("sevenSec", 353000, null, player);
					startQuestTimer("sixSec", 354000, null, player);
					startQuestTimer("fiveSec", 355000, null, player);
					startQuestTimer("fourSec", 356000, null, player);
					startQuestTimer("threeSec", 357000, null, player);
					startQuestTimer("twoSec", 358000, null, player);
					startQuestTimer("oneSec", 359000, null, player);
					startQuestTimer("endInstance", 360000, null, player);
					break;
				}
				case "fiveMin":
				{
					showOnScreenMsg(world, NpcStringId.FIVE_MIN_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "fourMin":
				{
					showOnScreenMsg(world, NpcStringId.FOUR_MIN_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "threeMin":
				{
					showOnScreenMsg(world, NpcStringId.THREE_MIN_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "twoMin":
				{
					showOnScreenMsg(world, NpcStringId.TWO_MIN_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "oneMin":
				{
					showOnScreenMsg(world, NpcStringId.ONE_MIN_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "tenSec":
				{
					showOnScreenMsg(world, NpcStringId.TEN_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "nineSec":
				{
					showOnScreenMsg(world, NpcStringId.NINE_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "eightSec":
				{
					showOnScreenMsg(world, NpcStringId.EIGHT_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "sevenSec":
				{
					showOnScreenMsg(world, NpcStringId.SEVEN_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "sixSec":
				{
					showOnScreenMsg(world, NpcStringId.SIX_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "fiveSec":
				{
					showOnScreenMsg(world, NpcStringId.FIVE_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "fourSec":
				{
					showOnScreenMsg(world, NpcStringId.FOUR_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "threeSec":
				{
					showOnScreenMsg(world, NpcStringId.THREE_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "twoSec":
				{
					showOnScreenMsg(world, NpcStringId.TWO_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "oneSec":
				{
					showOnScreenMsg(world, NpcStringId.ONE_SEC_LEFT, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "endInstance":
				{
					world.destroy();
					break;
				}
				case "spawnRamona":
				{
					world.spawnGroup("RAMONA_1");
					startQuestTimer("ramonaFirstMsg", 10000, null, player);
					break;
				}
				case "ramonaFirstTransform":
				{
					playMovie(world, Movie.SC_RAMONA_TRANS_A);
					world.spawnGroup("RAMONA_2");
					startQuestTimer("ramonaSecondMsg", 10000, null, player);
					break;
				}
				case "ramonaLastTransform":
				{
					if (isEasyMode(world))
					{
						playMovie(world, Movie.SC_RAMONA_TRANS_A);
						startQuestTimer("ramonaSecondtMsg", 10000, null, player);
					}
					else
					{
						playMovie(world, Movie.SC_RAMONA_TRANS_B);
						startQuestTimer("ramonaThirdMsg", 10000, null, player);
					}
					
					world.spawnGroup("RAMONA_FINAL");
					break;
				}
				case "ramonaFirstMsg":
				{
					showOnScreenMsg(world, NpcStringId.THE_GIANTS_WILL_RETURN_YOU_CAN_T_STOP_US, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "ramonaSecondMsg":
				{
					showOnScreenMsg(world, NpcStringId.THERE_IS_ENOUGH_SUFFERING_FOR_EVERYONE, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "ramonaThirdMsg":
				{
					showOnScreenMsg(world, NpcStringId.CAN_YOU_FEEL_IT_IT_S_TRUE_DESPAIR, ExShowScreenMessage.TOP_CENTER, 10000);
					break;
				}
				case "ramonaThunder":
				{
					for (int i = 0; i < (getRandom(7) + 2); i++)
					{
						addSpawn(DUMMY, player.getX() + getRandom(-800, 800), player.getY() + getRandom(-800, 800), player.getZ() + 20, 0, false, 5000, false, world.getId());
					}
					
					startQuestTimer("ramonaThunderCast", 500, null, player);
					break;
				}
				case "ramonaThunderCast":
				{
					world.getAliveNpcs(DUMMY).forEach(dummy ->
					{
						if (dummy != null)
						{
							dummy.doCast(isEasyMode(world) ? RAMONA_THUNDER_EASY.getSkill() : RAMONA_THUNDER_NORMAL.getSkill());
						}
					});
					break;
				}
				case "ramonaChain":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_1_EASY) : world.getNpc(RAMONA_1_NORMAL);
					if ((ramona != null) && SkillCaster.checkUseConditions(ramona, RAMONA_CHAIN.getSkill()))
					{
						ramona.doCast(RAMONA_CHAIN.getSkill());
						showOnScreenMsg(world, NpcStringId.YOU_CAN_RUN_FROM_ME_BUT_YOU_CAN_T_ESCAPE_THE_SUFFERING, ExShowScreenMessage.TOP_CENTER, 10000);
						startQuestTimer("ramonaChaos", 1700, null, player);
					}
					break;
				}
				case "ramonaChaos":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_1_EASY) : world.getNpc(RAMONA_1_NORMAL);
					if (ramona != null)
					{
						ramona.abortCast();
						ramona.doCast(RAMONA_CHAOS.getSkill());
					}
					break;
				}
				case "ramonaYellowArrowCast":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if ((ramona != null) && SkillCaster.checkUseConditions(ramona, RAMONA_YELLOW_ARROW_CAST.getSkill()))
					{
						ramona.doCast(RAMONA_YELLOW_ARROW_CAST.getSkill());
						startQuestTimer("ramonaYellowArrow", 2700, null, player);
					}
					break;
				}
				case "ramonaYellowArrow":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if (ramona != null)
					{
						ramona.abortCast();
						ramona.doCast(isEasyMode(world) ? RAMONA_YELLOW_ARROW_EASY.getSkill() : RAMONA_YELLOW_ARROW_NORMAL.getSkill());
					}
					break;
				}
				case "ramonaBlueArrowCast":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if ((ramona != null) && SkillCaster.checkUseConditions(ramona, RAMONA_BLUE_ARROW_CAST.getSkill()))
					{
						ramona.doCast(RAMONA_BLUE_ARROW_CAST.getSkill());
						startQuestTimer("ramonaBlueArrow", 2700, null, player);
					}
					break;
				}
				case "ramonaBlueArrow":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if (ramona != null)
					{
						ramona.abortCast();
						ramona.doCast(isEasyMode(world) ? RAMONA_BLUE_ARROW_EASY.getSkill() : RAMONA_BLUE_ARROW_NORMAL.getSkill());
					}
					break;
				}
				case "ramonaPurpleArrowCast":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if ((ramona != null) && SkillCaster.checkUseConditions(ramona, RAMONA_PURPLE_ARROW_CAST.getSkill()))
					{
						ramona.doCast(RAMONA_PURPLE_ARROW_CAST.getSkill());
						startQuestTimer("ramonaPurpleArrow", 2700, null, player);
					}
					break;
				}
				case "ramonaPurpleArrow":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if (ramona != null)
					{
						ramona.abortCast();
						ramona.doCast(isEasyMode(world) ? RAMONA_PURPLE_ARROW_EASY.getSkill() : RAMONA_PURPLE_ARROW_NORMAL.getSkill());
					}
					break;
				}
				case "ramonaRedArrowCast":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if ((ramona != null) && SkillCaster.checkUseConditions(ramona, RAMONA_RED_ARROW_CAST.getSkill()))
					{
						ramona.doCast(RAMONA_RED_ARROW_CAST.getSkill());
						startQuestTimer("ramonaRedArrow", 2700, null, player);
					}
					break;
				}
				case "ramonaRedArrow":
				{
					final Npc ramona = isEasyMode(world) ? world.getNpc(RAMONA_2_EASY) : world.getNpc(RAMONA_2_NORMAL);
					if (ramona != null)
					{
						ramona.abortCast();
						ramona.doCast(isEasyMode(world) ? RAMONA_RED_ARROW_EASY.getSkill() : RAMONA_RED_ARROW_NORMAL.getSkill());
					}
					break;
				}
			}
		}
		
		return super.onEvent(event, npc, player);
	}
	
	@Override
	public void onAttack(Npc npc, Player attacker, int damage, boolean isSummon, Skill skill)
	{
		final Instance world = npc.getInstanceWorld();
		if (isInInstance(world))
		{
			switch (npc.getId())
			{
				case RAMONA_1_EASY:
				case RAMONA_1_NORMAL:
				{
					final boolean ramona50 = world.getParameters().getBoolean("RAMONA_50", false);
					if ((npc.getCurrentHp() <= (npc.getMaxHp() * 0.5)) && !ramona50)
					{
						world.getParameters().set("RAMONA_50", true);
						if (isEasyMode(world))
						{
							startQuestTimer("ramonaLastTransform", 500, null, attacker);
						}
						else
						{
							startQuestTimer("ramonaFirstTransform", 500, null, attacker);
						}
						
						npc.deleteMe();
					}
					else
					{
						if (getRandom(100) < 90)
						{
							if (SkillCaster.checkUseConditions(npc, RAMONA_MAGIC.getSkill()))
							{
								final Creature mostHated = npc.asAttackable().getMostHated();
								npc.setTarget(mostHated);
								npc.doCast(RAMONA_MAGIC.getSkill());
							}
						}
						else if ((getRandom(100) < 98) && (getRandom(100) >= 90))
						{
							startQuestTimer("ramonaThunder", 1000, null, attacker);
						}
						else
						{
							startQuestTimer("ramonaChain", 1000, null, attacker);
						}
					}
					break;
				}
				case RAMONA_2_EASY:
				case RAMONA_2_NORMAL:
				{
					if (!isEasyMode(world))
					{
						final boolean ramona20 = world.getParameters().getBoolean("RAMONA_20", false);
						if ((npc.getCurrentHp() <= (npc.getMaxHp() * 0.2)) && !ramona20)
						{
							world.getParameters().set("RAMONA_20", true);
							startQuestTimer("ramonaLastTransform", 500, null, attacker);
							npc.deleteMe();
						}
					}
					
					if (getRandom(100) < 10)
					{
						startQuestTimer("ramonaYellowArrowCast", 1000, null, attacker);
					}
					else if ((getRandom(100) < 20) && (getRandom(100) >= 10))
					{
						startQuestTimer("ramonaBlueArrowCast", 1000, null, attacker);
					}
					else if ((getRandom(100) < 30) && (getRandom(100) >= 20))
					{
						startQuestTimer("ramonaPurpleArrowCast", 1000, null, attacker);
					}
					else if ((getRandom(100) < 40) && (getRandom(100) >= 30))
					{
						startQuestTimer("ramonaRedArrowCast", 1000, null, attacker);
					}
					else if ((getRandom(100) < 50) && (getRandom(100) >= 40))
					{
						if (SkillCaster.checkUseConditions(npc, RAMONA_STRIKE_EASY.getSkill()))
						{
							final Creature mostHated = npc.asAttackable().getMostHated();
							npc.setTarget(mostHated);
							npc.doCast(isEasyMode(world) ? RAMONA_STRIKE_EASY.getSkill() : RAMONA_STRIKE_NORMAL.getSkill());
						}
					}
					else
					{
						if (SkillCaster.checkUseConditions(npc, RAMONA_FOCUS_SHOT.getSkill()))
						{
							final Creature mostHated = npc.asAttackable().getMostHated();
							npc.setTarget(mostHated);
							npc.doCast(RAMONA_FOCUS_SHOT.getSkill());
						}
					}
					break;
				}
				case RAMONA_FINAL_NORMAL:
				{
					if (getRandom(100) < 40)
					{
						if (SkillCaster.checkUseConditions(npc, RAMONA_STRIKE.getSkill()))
						{
							final Creature mostHated = npc.asAttackable().getMostHated();
							npc.setTarget(mostHated);
							npc.doCast(RAMONA_STRIKE.getSkill());
						}
					}
					else if ((getRandom(100) < 70) && (getRandom(100) >= 40))
					{
						if (SkillCaster.checkUseConditions(npc, RAMONA_PATH.getSkill()))
						{
							final Creature mostHated = npc.asAttackable().getMostHated();
							npc.setTarget(mostHated);
							npc.doCast(RAMONA_PATH.getSkill());
						}
					}
					else
					{
						if (SkillCaster.checkUseConditions(npc, RAMONA_DESPAIR_1.getSkill()))
						{
							if (npc.getCurrentHp() < (npc.getMaxHp() * 0.05))
							{
								npc.doCast(RAMONA_DESPAIR_3.getSkill());
							}
							else if (npc.getCurrentHp() < (npc.getMaxHp() * 0.1))
							{
								npc.doCast(RAMONA_DESPAIR_2.getSkill());
							}
							else
							{
								npc.doCast(RAMONA_DESPAIR_1.getSkill());
							}
							
							showOnScreenMsg(world, NpcStringId.CAN_YOU_FEEL_IT_IT_S_TRUE_DESPAIR, ExShowScreenMessage.TOP_CENTER, 10000);
						}
					}
					break;
				}
			}
		}
	}
	
	@Override
	public void onKill(Npc npc, Player killer, boolean isSummon)
	{
		final Instance world = npc.getInstanceWorld();
		if (isInInstance(world))
		{
			switch (npc.getId())
			{
				case CONTROLLER:
				{
					world.despawnGroup("CONTROLLER");
					startQuestTimer("spawnRamona", 500, null, killer);
					break;
				}
				case RAMONA_2_EASY:
				case RAMONA_FINAL_NORMAL:
				{
					for (Player member : world.getPlayers())
					{
						if ((member.getParty() != null) || (member.isGM()))
						{
							giveItems(member, MAGIC_SPHERE);
							giveItems(member, TIME_STONE);
						}
					}
					
					showOnScreenMsg(world, NpcStringId.THIS_ISN_T_OVER, ExShowScreenMessage.TOP_CENTER, 10000);
					world.finishInstance(1);
					break;
				}
			}
		}
	}
	
	private boolean isEasyMode(Instance world)
	{
		return world.getTemplateId() == TEMPLATE_ID_EASY;
	}
	
	@Override
	public void onSpawn(Npc npc)
	{
		npc.setRandomWalking(false);
		
		switch (npc.getId())
		{
			case CONTROLLER:
			{
				// TODO: Find correct effect.
				npc.getEffectList().startAbnormalVisualEffect(AbnormalVisualEffect.SEIZURE2);
				break;
			}
			case RAMONA_1_EASY:
			case RAMONA_1_NORMAL:
			{
				// TODO: Make a cinematic.
				// npc.doCast(RAMONA_SPAWN.getSkill());
				break;
			}
			case RAMONA_2_EASY:
			case RAMONA_2_NORMAL:
			{
				npc.setCurrentHp(npc.getMaxHp() * 0.5);
				break;
			}
			case RAMONA_FINAL_NORMAL:
			{
				npc.setCurrentHp(npc.getMaxHp() * 0.2);
				RAMONA_FOCUS.getSkill().applyEffects(npc, npc);
				break;
			}
		}
	}
	
	public static void main(String[] args)
	{
		new EmpressPalace();
	}
}
