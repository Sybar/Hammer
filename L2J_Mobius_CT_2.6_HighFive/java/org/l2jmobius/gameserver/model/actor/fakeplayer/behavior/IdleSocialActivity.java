package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

import org.l2jmobius.commons.util.Rnd;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.network.enums.ChatType;
import org.l2jmobius.gameserver.network.serverpackets.NpcSay;

public class IdleSocialActivity implements FakeActivity
{
    private final Npc _npc;
    private long _nextTime;

    public IdleSocialActivity(Npc npc)
    {
        _npc = npc;
        _nextTime = System.currentTimeMillis() + Rnd.get(5000, 15000);
    }

    @Override
    public boolean shouldRunNow()
    {
        return System.currentTimeMillis() >= _nextTime;
    }

    @Override
    public void run()
    {
        final int roll = Rnd.get(100);

        if (roll < 40)
        {
            _npc.broadcastSocialAction(Rnd.get(2, 5)); // bow / clap / laugh etc.
        }
        else if (roll < 55)
        {
            _npc.broadcastPacket(new NpcSay(_npc, ChatType.NPC_GENERAL, "Another day passes..."));
        }

        _nextTime = System.currentTimeMillis() + Rnd.get(8000, 20000);
    }
}
