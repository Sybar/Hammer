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
package ai.bosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.item.holders.ItemHolder;

import ai.AbstractNpcAI;

/**
 * @author Tanatos
 */
public class DropSystem extends AbstractNpcAI
{
	// NPCs
	private static final int STAKATO_CROG = 29426;
	private static final int DEMONIC_VENOM = 29427;
	private static final int AVENGER_GRAFF = 29428;
	private static final int MAD_CULLAN = 29429;
	private static final int ARROGANT_LEBRUUM = 29430;
	private static final int GOLEM_17 = 29431;
	private static final int GOLEM_18 = 29432;
	
	// Items
	// Contributor rewards
	private static final ItemHolder STAKATO_CROG_CONTRIBUTOR = new ItemHolder(83175, 1);
	private static final ItemHolder DEMONIC_VENOM_CONTRIBUTOR = new ItemHolder(83195, 1);
	private static final ItemHolder AVENGER_GRAFF_CONTRIBUTOR = new ItemHolder(83201, 1);
	private static final ItemHolder MAD_CULLAN_CONTRIBUTOR = new ItemHolder(83207, 1);
	private static final ItemHolder ARROGANT_LEBRUUM_CONTRIBUTOR = new ItemHolder(83213, 1);
	
	// First hit rewards
	private static final ItemHolder STAKATO_CROG_FIRST_HIT = new ItemHolder(83176, 1);
	private static final ItemHolder DEMONIC_VENOM_FIRST_HIT = new ItemHolder(83196, 1);
	private static final ItemHolder AVENGER_GRAFF_FIRST_HIT = new ItemHolder(83202, 1);
	private static final ItemHolder MAD_CULLAN_FIRST_HIT = new ItemHolder(83208, 1);
	private static final ItemHolder ARROGANT_LEBRUUM_FIRST_HIT = new ItemHolder(83214, 1);
	
	// Last blow rewards
	private static final ItemHolder STAKATO_CROG_LAST_HIT = new ItemHolder(83177, 1);
	private static final ItemHolder DEMONIC_VENOM_LAST_HIT = new ItemHolder(83197, 1);
	private static final ItemHolder AVENGER_GRAFF_LAST_HIT = new ItemHolder(83203, 1);
	private static final ItemHolder MAD_CULLAN_LAST_HIT = new ItemHolder(83209, 1);
	private static final ItemHolder ARROGANT_LEBRUUM_LAST_HIT = new ItemHolder(83215, 1);
	
	// Most damage dealt rewards
	private static final ItemHolder STAKATO_CROG_1ST = new ItemHolder(83178, 1);
	private static final ItemHolder STAKATO_CROG_2ND = new ItemHolder(83179, 1);
	private static final ItemHolder STAKATO_CROG_3RD = new ItemHolder(83180, 1);
	private static final ItemHolder DEMONIC_VENOM_1ST = new ItemHolder(83198, 1);
	private static final ItemHolder DEMONIC_VENOM_2ND = new ItemHolder(83199, 1);
	private static final ItemHolder DEMONIC_VENOM_3RD = new ItemHolder(83200, 1);
	private static final ItemHolder AVENGER_GRAFF_1ST = new ItemHolder(83204, 1);
	private static final ItemHolder AVENGER_GRAFF_2ND = new ItemHolder(83205, 1);
	private static final ItemHolder AVENGER_GRAFF_3RD = new ItemHolder(83206, 1);
	private static final ItemHolder MAD_CULLAN_1ST = new ItemHolder(83210, 1);
	private static final ItemHolder MAD_CULLAN_2ND = new ItemHolder(83211, 1);
	private static final ItemHolder MAD_CULLAN_3RD = new ItemHolder(83212, 1);
	private static final ItemHolder ARROGANT_LEBRUUM_1ST = new ItemHolder(83216, 1);
	private static final ItemHolder ARROGANT_LEBRUUM_2ND = new ItemHolder(83217, 1);
	private static final ItemHolder ARROGANT_LEBRUUM_3RD = new ItemHolder(83218, 1);
	
	// Golem Reward
	private static final ItemHolder GOLEM_REWARD = new ItemHolder(83367, 1);
	
	// Rewards
	private static final Map<Integer, ItemHolder[]> REWARD_MAP = Map.of(STAKATO_CROG, new ItemHolder[]
	{
		STAKATO_CROG_CONTRIBUTOR,
		STAKATO_CROG_FIRST_HIT,
		STAKATO_CROG_LAST_HIT,
		STAKATO_CROG_1ST,
		STAKATO_CROG_2ND,
		STAKATO_CROG_3RD
	}, DEMONIC_VENOM, new ItemHolder[]
	{
		DEMONIC_VENOM_CONTRIBUTOR,
		DEMONIC_VENOM_FIRST_HIT,
		DEMONIC_VENOM_LAST_HIT,
		DEMONIC_VENOM_1ST,
		DEMONIC_VENOM_2ND,
		DEMONIC_VENOM_3RD
	}, AVENGER_GRAFF, new ItemHolder[]
	{
		AVENGER_GRAFF_CONTRIBUTOR,
		AVENGER_GRAFF_FIRST_HIT,
		AVENGER_GRAFF_LAST_HIT,
		AVENGER_GRAFF_1ST,
		AVENGER_GRAFF_2ND,
		AVENGER_GRAFF_3RD
	}, MAD_CULLAN, new ItemHolder[]
	{
		MAD_CULLAN_CONTRIBUTOR,
		MAD_CULLAN_FIRST_HIT,
		MAD_CULLAN_LAST_HIT,
		MAD_CULLAN_1ST,
		MAD_CULLAN_2ND,
		MAD_CULLAN_3RD
	}, ARROGANT_LEBRUUM, new ItemHolder[]
	{
		ARROGANT_LEBRUUM_CONTRIBUTOR,
		ARROGANT_LEBRUUM_FIRST_HIT,
		ARROGANT_LEBRUUM_LAST_HIT,
		ARROGANT_LEBRUUM_1ST,
		ARROGANT_LEBRUUM_2ND,
		ARROGANT_LEBRUUM_3RD
	});
	private static final Set<Integer> NON_GOLEM_RAID = REWARD_MAP.keySet();
	private static final Set<Integer> GOLEM_RAID = Set.of(GOLEM_17, GOLEM_18);
	private final Map<Integer, DamageInfo> raidBossDamageMap = new HashMap<>();
	
	public DropSystem()
	{
		addAttackId(NON_GOLEM_RAID);
		addAttackId(GOLEM_RAID);
		addKillId(NON_GOLEM_RAID);
		addKillId(GOLEM_RAID);
	}
	
	@Override
	public void onAttack(Npc npc, Player attacker, int damage, boolean isSummon)
	{
		final int npcId = npc.getId();
		raidBossDamageMap.putIfAbsent(npcId, new DamageInfo());
		final DamageInfo damageInfo = raidBossDamageMap.get(npcId);
		if (damageInfo.firstAttacker == null)
		{
			damageInfo.firstAttacker = attacker;
		}
		
		damageInfo.totalDamage.put(attacker, damageInfo.totalDamage.getOrDefault(attacker, 0L) + damage);
		
		if (NON_GOLEM_RAID.contains(npcId))
		{
			if (damageInfo.totalDamage.get(attacker) > 100000)
			{
				damageInfo.top200.put(attacker, damageInfo.totalDamage.get(attacker));
				if (damageInfo.top200.size() > 200)
				{
					final Player minPlayer = Collections.min(damageInfo.top200.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
					damageInfo.top200.remove(minPlayer);
				}
			}
		}
		else
		{
			damageInfo.top100.put(attacker, damageInfo.totalDamage.get(attacker));
			if (damageInfo.top100.size() > 100)
			{
				Player minPlayer = Collections.min(damageInfo.top100.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
				damageInfo.top100.remove(minPlayer);
			}
		}
		
		damageInfo.updateTop3(attacker, damageInfo.totalDamage.get(attacker));
	}
	
	@Override
	public void onKill(Npc npc, Player killer, boolean isPet)
	{
		final int npcId = npc.getId();
		final DamageInfo damageInfo = raidBossDamageMap.get(npcId);
		if (damageInfo == null)
		{
			return;
		}
		
		if (NON_GOLEM_RAID.contains(npcId))
		{
			final ItemHolder[] rewards = REWARD_MAP.get(npcId);
			if (killer != null)
			{
				giveItems(killer, rewards[2]); // Last Hit
			}
			
			if (damageInfo.firstAttacker != null)
			{
				giveItems(damageInfo.firstAttacker, rewards[1]); // First Hit
			}
			
			// Top 3 rewards
			for (int i = 0; i < Math.min(3, damageInfo.top3.size()); i++)
			{
				giveItems(damageInfo.top3.get(i).player, rewards[3 + i]);
			}
			
			// Top 200 contributors
			for (Player player : damageInfo.top200.keySet())
			{
				giveItems(player, rewards[0]);
			}
		}
		else
		{
			// Golem reward
			for (Player player : damageInfo.top100.keySet())
			{
				giveItems(player, GOLEM_REWARD);
			}
		}
		
		raidBossDamageMap.remove(npcId);
	}
	
	private static class DamageInfo
	{
		Player firstAttacker = null;
		final Map<Player, Long> totalDamage = new ConcurrentHashMap<>();
		final Map<Player, Long> top100 = new ConcurrentHashMap<>();
		final Map<Player, Long> top200 = new ConcurrentHashMap<>();
		final List<PlayerDamage> top3 = new ArrayList<>(3);
		
		void updateTop3(Player player, long damage)
		{
			if (player == null)
			{
				return;
			}
			
			synchronized (top3)
			{
				top3.removeIf(pd -> (pd != null) && player.equals(pd.player));
				top3.add(new PlayerDamage(player, damage));
				top3.removeIf(Objects::isNull);
				top3.sort(Comparator.reverseOrder());
				if (top3.size() > 3)
				{
					top3.subList(3, top3.size()).clear();
				}
			}
		}
	}
	
	private static class PlayerDamage implements Comparable<PlayerDamage>
	{
		final Player player;
		final long damage;
		
		PlayerDamage(Player player, long damage)
		{
			this.player = player;
			this.damage = damage;
		}
		
		@Override
		public int compareTo(PlayerDamage o)
		{
			return Long.compare(o.damage, this.damage);
		}
	}
	
	public static void main(String[] args)
	{
		new DropSystem();
	}
}
