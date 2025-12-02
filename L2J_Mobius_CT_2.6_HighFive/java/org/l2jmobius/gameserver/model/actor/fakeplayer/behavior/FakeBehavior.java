package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

public interface FakeBehavior
{
    void think();

    long getNextThinkDelay();
}
