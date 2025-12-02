package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;

public final class FakeBehaviorFactory
{
    private FakeBehaviorFactory()
    {
    }

    public static FakeBehavior createBehavior(Npc npc)
    {
        // Read race from NPC template parameters. Fallback to "UNKNOWN".
        final StatSet params = npc.getTemplate().getParameters();
        final String race = (params != null) ? params.getString("CitizenRace", "UNKNOWN") : "UNKNOWN";

        if ("DWARF".equalsIgnoreCase(race))
        {
            return createDwarfBehavior(npc);
        }

        // Fallback: generic villager behaviour
        return new GenericVillagerBehavior(npc);
    }

    private static FakeBehavior createDwarfBehavior(Npc npc)
    {
        final List<FakeActivity> activities = new ArrayList<>();

        activities.add(new IdleSocialActivity(npc));
        activities.add(new DwarfHomeRoutineActivity(npc)); // now: NPC-to-NPC wandering
        activities.add(new DwarfCraftActivity(npc));

        return new CompositeBehavior(activities, 3000L, 10000L);
    }
}
