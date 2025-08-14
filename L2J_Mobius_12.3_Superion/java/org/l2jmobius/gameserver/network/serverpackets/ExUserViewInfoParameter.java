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
package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.Config;
import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.actor.stat.PlayerStat;
import org.l2jmobius.gameserver.model.item.enums.ItemGrade;
import org.l2jmobius.gameserver.model.item.enums.ShotType;
import org.l2jmobius.gameserver.model.item.type.WeaponType;
import org.l2jmobius.gameserver.model.stats.Stat;
import org.l2jmobius.gameserver.model.stats.TraitType;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;

/**
 * @author Mobius, Atronic
 */
public class ExUserViewInfoParameter extends ServerPacket
{
	private final Player _player;
	
	public ExUserViewInfoParameter(Player player)
	{
		_player = player;
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		
		ServerPackets.EX_USER_VIEW_INFO_PARAMETER.writeId(this, buffer);
		
		final PlayerStat stat = _player.getStat();
		int index = 0;
		
		// Number of parameters.
		buffer.writeInt(236);
		
		// ################################## ATTACK ##############################
		// P. Atk. (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PHYSICAL_ATTACK) * 100);
		
		// P. Atk. (num.)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getPAtk());
		
		// M. Atk. (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.MAGIC_ATTACK) * 100);
		
		// M. Atk. (num)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getMAtk());
		
		// Soulshot Damage - Activation
		buffer.writeShort(index++);
		buffer.writeInt((_player.isChargedShot(ShotType.BLESSED_SOULSHOTS) || _player.isChargedShot(ShotType.SOULSHOTS)) ? (10000 + (_player.getActiveRubyJewel() != null ? (int) _player.getActiveRubyJewel().getBonus() * 1000 : 0)) : 0);
		
		// Spiritshot Damage - Activation
		buffer.writeShort(index++);
		buffer.writeInt((_player.isChargedShot(ShotType.BLESSED_SPIRITSHOTS) || _player.isChargedShot(ShotType.SPIRITSHOTS)) ? (10000 + (_player.getActiveShappireJewel() != null ? (int) _player.getActiveShappireJewel().getBonus() * 1000 : 0)) : 0);
		
		// Soulshot Damage - Enchanted Weapons
		buffer.writeShort(index++);
		buffer.writeInt((((_player.getActiveWeaponInstance() != null) && _player.getActiveWeaponInstance().isEnchanted()) ? (int) (_player.getActiveWeaponInstance().getEnchantLevel() * (_player.getActiveWeaponItem().getItemGrade() == ItemGrade.S ? 1.6 : _player.getActiveWeaponItem().getItemGrade() == ItemGrade.A ? 1.4 : _player.getActiveWeaponItem().getItemGrade() == ItemGrade.B ? 0.7 : _player.getActiveWeaponItem().getItemGrade().equals(ItemGrade.C) ? 0.4 : _player.getActiveWeaponItem().getItemGrade().equals(ItemGrade.D) ? 0.4 : 0) * 100) : 0));
		
		// Spiritshot Damage - Enchanted Weapons
		buffer.writeShort(index++);
		buffer.writeInt((((_player.getActiveWeaponInstance() != null) && _player.getActiveWeaponInstance().isEnchanted()) ? (int) (_player.getActiveWeaponInstance().getEnchantLevel() * (_player.getActiveWeaponItem().getItemGrade() == ItemGrade.S ? 1.6 : _player.getActiveWeaponItem().getItemGrade() == ItemGrade.A ? 1.4 : _player.getActiveWeaponItem().getItemGrade() == ItemGrade.B ? 0.7 : _player.getActiveWeaponItem().getItemGrade().equals(ItemGrade.C) ? 0.4 : _player.getActiveWeaponItem().getItemGrade().equals(ItemGrade.D) ? 0.4 : 0) * 100) : 0));
		
		// Soulshot Damage - Misc.
		buffer.writeShort(index++);
		buffer.writeInt(_player.getActiveRubyJewel() != null ? (int) (_player.getActiveRubyJewel().getBonus() * 10000) : 0);
		
		// Spiritshot Damage - Misc.
		buffer.writeShort(index++);
		buffer.writeInt(_player.getActiveShappireJewel() != null ? (int) (_player.getActiveShappireJewel().getBonus() * 10000) : 0);
		
		// Basic PvP Damage
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.PVP_PHYSICAL_ATTACK_DAMAGE, 100) - 100) * 100);
		
		// P. Skill Damage in PvP
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.PVP_PHYSICAL_SKILL_DAMAGE, 100) - 100) * 100);
		
		// M. Skill Damage in PvP
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.PVP_MAGICAL_SKILL_DAMAGE, 100) - 100) * 100);
		
		// Inflicted PvP Damage
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PVP_PHYSICAL_ATTACK_DAMAGE, 0));
		
		// PvP Damage Decrease Ignore
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Basic PvE Damage
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.PVE_PHYSICAL_ATTACK_DAMAGE, 100) - 100) * 100);
		
		// P. Skill Damage in PvE
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.PVE_PHYSICAL_SKILL_DAMAGE, 100) - 100) * 100);
		
		// M. Skill Damage in PvE
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.PVE_MAGICAL_SKILL_DAMAGE, 100) - 100) * 100);
		
		// PvE Damage
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PVE_DAMAGE_TAKEN) * 100);
		
		// PvE Damage Decrease Ignore
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PVE_PHYSICAL_SKILL_DAMAGE) * 100);
		
		// TODO: Basic Power
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Skill Power
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.PHYSICAL_SKILL_POWER, 100) - 100) * 100);
		
		// M. Skill Power
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.MAGICAL_SKILL_POWER, 100) - 100) * 100);
		
		// AoE Skill Damage
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.AREA_OF_EFFECT_DAMAGE_MODIFY) * 100);
		
		// Damage Bonus - Sword
		buffer.writeShort(index++);
		buffer.writeInt(((_player.getActiveWeaponInstance() != null) && (_player.getActiveWeaponInstance().getItemType() == WeaponType.SWORD)) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Sword Two hand
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Damage Bonus - Magic Sword
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Damage Bonus - Ancient Sword
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && (_player.getActiveWeaponInstance().getItemType() == WeaponType.ANCIENTSWORD) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Dagger
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && (_player.getActiveWeaponInstance().getItemType() == WeaponType.DAGGER) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Rapier
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && (_player.getActiveWeaponInstance().getItemType() == WeaponType.RAPIER) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Blunt Weapon (one hand)
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && ((_player.getActiveWeaponInstance().getItemType() == WeaponType.ETC) || (_player.getActiveWeaponInstance().getItemType() == WeaponType.BLUNT) || (_player.getActiveWeaponInstance().getItemType() == WeaponType.DUALBLUNT)) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Blunt Weapon (two hand)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Damage Bonus - Magic Blunt Weapon (one hand)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Damage Bonus - Magic Blunt Weapon (two hand)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Damage Bonus - Spear
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && (_player.getActiveWeaponInstance().getItemType() == WeaponType.POLE) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Fists
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && ((_player.getActiveWeaponInstance().getItemType() == WeaponType.FIST) || (_player.getActiveWeaponInstance().getItemType() == WeaponType.DUALFIST)) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Dual Swords
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && (_player.getActiveWeaponInstance().getItemType() == WeaponType.DUAL) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Bow
		buffer.writeShort(index++);
		buffer.writeInt((_player.getActiveWeaponInstance() != null) && ((_player.getActiveWeaponInstance().getItemType() == WeaponType.BOW) || (_player.getActiveWeaponInstance().getItemType() == WeaponType.CROSSBOW) || (_player.getActiveWeaponInstance().getItemType() == WeaponType.TWOHANDCROSSBOW)) ? stat.getWeaponBonusPAtk() : 0);
		
		// Damage Bonus - Firearms
		buffer.writeShort(index++);
		
		// ESSENCE buffer.writeInt((_player.getActiveWeaponInstance() != null) && (_player.getActiveWeaponInstance().getItemType() == WeaponType.PISTOLS) ? stat.getWeaponBonusPAtk() : 0);
		buffer.writeInt(0);
		
		// ################################## DEFENCE ##############################
		// P. Def. (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PHYSICAL_DEFENCE) * 100);
		
		// P. Def. (num.)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getPDef());
		
		// M. Def. (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.MAGICAL_DEFENCE) * 100);
		
		// M. Def. (num.)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getMDef());
		
		// Soulshot Damage Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (100 - (stat.getValue(Stat.SOULSHOT_RESISTANCE, 1) * 100)));
		
		// Spiritshot Damage Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (100 - (stat.getValue(Stat.SPIRITSHOT_RESISTANCE, 1) * 100)));
		
		// Received PvP Damage from Basic Attacks
		buffer.writeShort(index++);
		buffer.writeInt(0 - ((int) (stat.getValue(Stat.PVP_PHYSICAL_ATTACK_DEFENCE, 100) - 100) * 100));
		
		// Received PvP P. Skill Damage
		buffer.writeShort(index++);
		buffer.writeInt(0 - ((int) (stat.getValue(Stat.PVP_PHYSICAL_SKILL_DEFENCE, 100) - 100) * 100));
		
		// Received PvP M. Skill Damage
		buffer.writeShort(index++);
		buffer.writeInt(0 - ((int) (stat.getValue(Stat.PVP_MAGICAL_SKILL_DEFENCE, 100) - 100) * 100));
		
		// Received PvP Damage
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PVP_DAMAGE_TAKEN) * 100);
		
		// PvP Damage Decrease
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received PvE Damage from Basic Attacks
		buffer.writeShort(index++);
		buffer.writeInt(0 - ((int) (stat.getValue(Stat.PVE_PHYSICAL_ATTACK_DEFENCE, 100) - 100) * 100));
		
		// Received PvE P. Skill Damage
		buffer.writeShort(index++);
		buffer.writeInt(0 - ((int) (stat.getValue(Stat.PVE_PHYSICAL_SKILL_DEFENCE, 100) - 100) * 100));
		
		// Received PvE M. Skill Damage
		buffer.writeShort(index++);
		buffer.writeInt(0 - ((int) (stat.getValue(Stat.PVE_MAGICAL_SKILL_DEFENCE, 100) - 100) * 100));
		
		// Received PvE Damage
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PVE_DAMAGE_TAKEN) * 100);
		
		// PvE Damage Decrease
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received basic damage power
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Skill Power when hit
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.PHYSICAL_SKILL_POWER) * 100);
		
		// M. Skill Power when hit
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.MAGICAL_SKILL_POWER) * 100);
		
		// AOE Damage Resistance
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.AREA_OF_EFFECT_DAMAGE_DEFENCE, 100) - 100) * 100));
		
		// Sword Resistance Nr. 60
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.SWORD) * 10000));
		
		// Shield Defense (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Shield Defence (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Shield Defence Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Dagger Weapon Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.DAGGER) * 10000));
		
		// Received Damage when Immobilized
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Blunt Weapon Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.BLUNT) * 10000));
		
		// TODO: Accessory P. Def.
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// ################################## ACCURACY / EVASION ##############################
		// P. Accuracy (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Accuracy (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Spear Weapon Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.POLE) * 10000));
		
		// Fists Weapon Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.DUALFIST) * 10000));
		
		// Dual Sword Resistance Nr. 72
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.DUAL) * 10000));
		
		// Bow Weapon Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.BOW) * 10000));
		
		// M. Evasion (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Shield Defence (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.SHIELD_DEFENCE)) * 100));
		
		// ################################## SPEED ##############################
		// Shield Defence (+)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getShldDef());
		
		// Shield Defence Rate
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.SHIELD_DEFENCE_RATE, 100) - 100) * 100));
		
		// Casting Spd. (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Casting Spd. (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Speed (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Speed (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// ################################## CRITICAL ##############################
		// Received Fixed Damage
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.REAL_DAMAGE_RESIST, 100) - 100) * 100));
		
		// Basic Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Skill Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Accuracy (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.ACCURACY_COMBAT) * 100);
		
		// P. Accuracy (num.)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getAccuracy());
		
		// M. Accuracy (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.ACCURACY_MAGIC) * 100);
		
		// M. Accuracy (num.)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getMagicAccuracy());
		
		// TODO: Received basic Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received P. Skill Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Evasion (%)
		buffer.writeShort(index++);
		buffer.writeInt(((_player.getEvasionRate() * 100) / Config.MAX_EVASION));
		
		// P. Evasion (num.)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getEvasionRate());
		
		// M. Evasion (%)
		buffer.writeShort(index++);
		buffer.writeInt(((_player.getMagicEvasionRate() * 100) / Config.MAX_EVASION));
		
		// M. Evasion (num.)
		buffer.writeShort(index++);
		buffer.writeInt(_player.getMagicEvasionRate());
		
		// P. Skill Critical Damage (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// M. Skill Critical Damage (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received Basic Critical Damage (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received P. Skill Critical Damage (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Atk. Spd. (%)
		buffer.writeShort(index++);
		buffer.writeInt(((_player.getPAtkSpd() * 100) / Config.MAX_PATK_SPEED));
		
		// Atk. Spd. (num.)
		buffer.writeShort(index++);
		buffer.writeInt(stat.getPAtkSpd());
		
		// Casting Spd. (%)
		buffer.writeShort(index++);
		buffer.writeInt((_player.getMAtkSpd() * 100) / Config.MAX_MATK_SPEED);
		
		// Casting Spd. (num.)
		buffer.writeShort(index++);
		buffer.writeInt(stat.getMAtkSpd());
		
		// Speed (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) ((_player.getMoveSpeed() * 100) / Config.MAX_RUN_SPEED));
		
		// Speed (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getMoveSpeed());
		
		// Basic Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(stat.getCriticalHit());
		
		// Basic Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(stat.getCriticalHit());
		
		// P. Skill Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.CRITICAL_RATE_SKILL, 100) - 100) * 100));
		
		// TODO: P. Skill Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// M. Skill Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.MAGIC_CRITICAL_RATE, 100) - 100) * 100));
		
		// M. Skill Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received basic Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.DEFENCE_CRITICAL_RATE, 100) - 100) * 100));
		
		// Received basic Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.DEFENCE_CRITICAL_RATE_ADD));
		
		// Received P. Skill Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.DEFENCE_PHYSICAL_SKILL_CRITICAL_RATE, 100) - 100) * 100));
		
		// Received P. Skill Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.DEFENCE_PHYSICAL_SKILL_CRITICAL_RATE_ADD));
		
		// Received M. Skill Critical Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.DEFENCE_MAGIC_CRITICAL_RATE, 100) - 100) * 100));
		
		// Received M. Skill Critical Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.DEFENCE_MAGIC_CRITICAL_RATE_ADD));
		
		// Basic Critical Damage (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.CRITICAL_DAMAGE) * 100);
		
		// MP Recovery Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Skill Critical Damage
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.PHYSICAL_SKILL_CRITICAL_DAMAGE, 100) - 100) * 100));
		
		// CP Recovery Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// ################################## ASSIST ##############################
		// M. Skill Critical Damage
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.MAGIC_CRITICAL_DAMAGE, 100) - 100) * 100));
		
		// M. Skill Cooldown (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received C. Damage from Basic Attacks
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.DEFENCE_CRITICAL_DAMAGE, 100) - 100) * 100));
		
		// P. Skill MP Consumption Decrease (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received P. Skill Critical Damage
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.DEFENCE_PHYSICAL_SKILL_CRITICAL_DAMAGE, 100) - 100) * 100));
		
		// M. Skill MP Consumption Decrease (num.)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Received M. Skill Critical Damage
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.DEFENCE_MAGIC_CRITICAL_DAMAGE, 100) - 100) * 100));
		
		// TODO: XP
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// HP ReCovery Potions' Effect (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.ADDITIONAL_POTION_HP, 100) - 100) * 100));
		
		// HP Recovery Potions' Effect (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.ADDITIONAL_POTION_HP));
		
		// MP Recovery Potions' Effect (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.ADDITIONAL_POTION_MP, 100) - 100) * 100));
		
		// MP Recovery Potions' Effect (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.ADDITIONAL_POTION_MP));
		
		// HP Recovery Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.REGENERATE_HP_RATE, 100) - 100) * 100));
		
		// HP Recovery Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(stat.getHpRegen());
		
		// TODO: Skill Mastery Rate (+)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// ################################## DEBUFFS ##############################
		// Debuff Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Debilitation Atk. Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Confusion Atk. Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Stupor Atk. Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Danger Zone Atk. Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Pull Atk. Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Petrification Atk. Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// MP Recovery Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.REGENERATE_MP_RATE, 100) - 100) * 100));
		
		// MP Recovery Rate (num.)
		buffer.writeShort(index++);
		buffer.writeInt(stat.getMpRegen());
		
		// Debilitation Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Confusion Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Stupor Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Danger Zone Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Pull Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Petrification Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Infection Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Obstruction Resistance (%)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// CP Recovery Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.REGENERATE_CP_RATE, 100) - 100) * 100));
		
		// CP Recovery Rate (num.) nr. 153
		buffer.writeShort(index++);
		buffer.writeInt(stat.getCpRegen());
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Skill Cooldown (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getReuseTypeValue(1) * 100);
		
		// M. Skill Cooldown (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getReuseTypeValue(2) * 100);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Skill MP Consumption Decrease (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getMpConsumeTypeValue(1) * 100);
		
		// M. Skill MP Consumption Decrease (%)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getMpConsumeTypeValue(2) * 100);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Skill MP Consumption Decrease (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getMpConsumeTypeValue(1) * 100);
		
		// M. Skill MP Consumption Decrease (num.)
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getMpConsumeTypeValue(2) * 100);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Debuff Resistance Nr. 173
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.RESIST_ABNORMAL_DEBUFF, 100) - 100) * 100));
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Confusion Attack Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Stupor Attack Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Pull Attack Rate
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getAttackTrait(TraitType.PULL) * 100));
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Obstruction Attack Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Confusion Resistance
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Stupor Resistance
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Pull Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.PULL) * 100));
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Obstruction Resistance
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// None
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TO DO Infection Attack Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Infection Resistance
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Weapon P. Atk.
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.WEAPON_BONUS_PHYSICAL_ATTACK, 0));
		
		// Weapon M. Atk.
		buffer.writeShort(index++);
		buffer.writeInt((int) stat.getValue(Stat.WEAPON_BONUS_MAGIC_ATTACK, 0));
		
		// TODO: Backstab Damage Up
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Damage to Immobilized Targets
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.IMMOBILE_DAMAGE_BONUS, 100) - 100) * 100);
		
		// TODO: NOT WORKING ??? Received Damage when Immobilized
		buffer.writeShort(index++);
		buffer.writeInt(0 - ((int) (stat.getValue(Stat.IMMOBILE_DAMAGE_RESIST, 100) - 100) * 100));
		
		// TODO: Armor P. Def
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Accessory M. Def
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Dual Blunt Weapon Resistance Nr. 206
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.DUALBLUNT) * 10000));
		
		// Crossbow Weapon Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.CROSSBOW) * 10000));
		
		// Dual Daggers Weapon Resistance
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getDefenceTrait(TraitType.DUALDAGGER) * 10000));
		
		// Perfection Trigger Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.PERFECTION_RATE, 100) - 100) * 100));
		
		// TODO: Perfection Trigger Rate (num)
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Perfection Power (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.PERFECTION_POWER, 100) - 100) * 100));
		
		// TODO: Perfection Power (num) Nr. 212
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// Max Hp (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.MAX_HP, 100) - 100) * 100));
		
		// Max Hp (num)
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.MAX_HP)));
		
		// Max Mp (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.MAX_MP, 100) - 100) * 100));
		
		// Max Mp (num)
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.MAX_MP)));
		
		// Max Cp (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.MAX_CP, 100) - 100) * 100));
		
		// Max Cp (num)
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.MAX_CP)));
		
		// Max HP
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.MAX_HP)));
		
		// Vitality Bonus
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.VITALITY_EXP_RATE) * 100));
		
		// XP
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.BONUS_EXP) * 100));
		
		// SP
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.BONUS_SP) * 100));
		
		// Adena Drop Rate
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.BONUS_DROP_ADENA) * 100));
		
		// Adena Drop Rate with Vitality
		buffer.writeShort(index++);
		buffer.writeInt((int) (stat.getValue(Stat.BONUS_DROP_ADENA) * 100));
		
		// Received Healing
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.HEAL_EFFECT, 100) - 100) * 100));
		
		// Healing Power
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.HEAL_EFFECT_ADD, 100) - 100) * 100));
		
		// Skill Mastery Rate (%)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.SKILL_MASTERY_RATE, 100) - 100) * 100));
		
		// Skill Mastery Rate (num)
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.SKILL_MASTERY, 100) - 100) * 100));
		
		// TODO: Debilitation Attack Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Danger Zone Attack Rate
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Petrification Attack Rate Nr. 231
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Debilitation Resistance
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Danger Zone Resistance
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// TODO: Petrification Resistance
		buffer.writeShort(index++);
		buffer.writeInt(0);
		
		// P. Debuff Resistance
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.ABNORMAL_RESIST_PHYSICAL, 100) - 100) * 100));
		
		// M. Debuff Resistance
		buffer.writeShort(index++);
		buffer.writeInt(((int) (stat.getValue(Stat.ABNORMAL_RESIST_MAGICAL, 100) - 100) * 100));
	}
}
