package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

import java.util.List;

public class CompositeBehavior implements FakeBehavior
{
    private final List<FakeActivity> _activities;
    private final long _minDelay;
    private final long _maxDelay;

    public CompositeBehavior(List<FakeActivity> activities, long minDelay, long maxDelay)
    {
        _activities = activities;
        _minDelay = minDelay;
        _maxDelay = maxDelay;
    }

    @Override
    public void think()
    {
        for (FakeActivity activity : _activities)
        {
            if (activity.shouldRunNow())
            {
                activity.run();
                return;
            }
        }
    }

    @Override
    public long getNextThinkDelay()
    {
        // You can use Config.FAKE_PLAYER_AI_TICK if you want.
        return org.l2jmobius.commons.util.Rnd.get((int) _minDelay, (int) _maxDelay);
    }
}
