package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

import org.l2jmobius.Config;
import org.l2jmobius.commons.util.Rnd;
import org.l2jmobius.gameserver.ai.Intention;
import org.l2jmobius.gameserver.geoengine.GeoEngine;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Npc;

public class GenericVillagerBehavior implements FakeBehavior
{
    private final Npc _npc;
    private final Location _home;

    public GenericVillagerBehavior(Npc npc)
    {
        _npc = npc;
        _home = new Location(npc.getX(), npc.getY(), npc.getZ());
    }

    @Override
    public void think()
    {
        if (_npc.isMoving())
        {
            return;
        }

        // This is a simplified version of your existing FakePlayerBrain logic.
        final int roll = Rnd.get(100);

        // Move
        if (roll < Config.FAKE_PLAYER_MOVE_CHANCE)
        {
            final int radius = Config.FAKE_PLAYER_WANDER_RADIUS;
            final int x = _home.getX() + Rnd.get(-radius, radius);
            final int y = _home.getY() + Rnd.get(-radius, radius);
            final int z = GeoEngine.getInstance().getHeight(x, y, _home.getZ());

            final Location loc = new Location(x, y, z);
            _npc.getAI().setIntention(Intention.MOVE_TO, loc);
            return;
        }

        // Social / idle – you can copy more from your existing brain
        if (roll < (Config.FAKE_PLAYER_MOVE_CHANCE + Config.FAKE_PLAYER_SOCIAL_CHANCE))
        {
            _npc.broadcastSocialAction(Rnd.get(2, 5));
            return;
        }

        // Otherwise idle / maybe rotate etc. (keep it empty for now)
    }

    @Override
    public long getNextThinkDelay()
    {
        return Rnd.get(Config.FAKE_PLAYER_AI_TICK, Config.FAKE_PLAYER_AI_TICK * 2);
    }
}
