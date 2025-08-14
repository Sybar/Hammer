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
package org.l2jmobius.gameserver.model.actor.holders.npc;

/**
 * @author Mobius
 */
public class LimitedDropHolder
{
	private final int _itemId;
	private final int _minDropAmount;
	private final int _maxDropAmount;
	private final int _minPlayerLevel;
	private final int _maxPlayerLevel;
	private final int _dailyLimit;
	private final double _chance;
	
	public LimitedDropHolder(int itemId, int minDropAmount, int maxDropAmount, int minPlayerLevel, int maxPlayerLevel, int dailyLimit, double chance)
	{
		_itemId = itemId;
		_minDropAmount = minDropAmount;
		_maxDropAmount = maxDropAmount;
		_minPlayerLevel = minPlayerLevel;
		_maxPlayerLevel = maxPlayerLevel;
		_dailyLimit = dailyLimit;
		_chance = chance;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public int getMinDropAmount()
	{
		return _minDropAmount;
	}
	
	public int getMaxDropAmount()
	{
		return _maxDropAmount;
	}
	
	public int getMinPlayerLevel()
	{
		return _minPlayerLevel;
	}
	
	public int getMaxPlayerLevel()
	{
		return _maxPlayerLevel;
	}
	
	public int getDailyLimit()
	{
		return _dailyLimit;
	}
	
	public double getChance()
	{
		return _chance;
	}
}
