package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

import org.l2jmobius.commons.util.Rnd;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.network.enums.ChatType;
import org.l2jmobius.gameserver.network.serverpackets.NpcSay;

public class DwarfCraftActivity implements FakeActivity
{
    private final Npc _npc;
    private long _nextTime;

    public DwarfCraftActivity(Npc npc)
    {
        _npc = npc;
        _nextTime = System.currentTimeMillis() + Rnd.get(20000, 40000);
    }

    @Override
    public boolean shouldRunNow()
    {
        return System.currentTimeMillis() >= _nextTime;
    }

    @Override
    public void run()
    {
        // 1) Play hammer / craft social
        _npc.broadcastSocialAction(15); // hammer (use 16 if your client prefers that for crafting)

        // 2) Occasionally say a dwarven line (40% chance)
        if (Rnd.get(100) < 40)
        {
            _npc.broadcastPacket(new NpcSay(_npc, ChatType.NPC_GENERAL, "Steel must sing before it’s worthy!"));
        }

        _nextTime = System.currentTimeMillis() + Rnd.get(30000, 60000);
    }
}
