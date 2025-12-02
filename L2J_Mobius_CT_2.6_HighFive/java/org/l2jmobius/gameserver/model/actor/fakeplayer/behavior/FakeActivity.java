package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

public interface FakeActivity
{
    /**
     * Should this activity act on this tick?
     * Keep it fast: just small checks or simple timers.
     */
    boolean shouldRunNow();

    /**
     * Perform the activity: move, social, talk, etc.
     */
    void run();
}
