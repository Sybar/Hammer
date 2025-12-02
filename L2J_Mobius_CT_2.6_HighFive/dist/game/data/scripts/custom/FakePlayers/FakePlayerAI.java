package custom.FakePlayers;

import ai.AbstractNpcAI;
import org.l2jmobius.gameserver.managers.FakePlayerEngine;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.data.xml.NpcData;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * Script that turns certain NPCs into Fake Players.
 *
 * Old version:
 *   - kept a long hardcoded list of NPC IDs:
 *       80000, 80001, 80002, ...
 *   - every time we added a new Fake Player, we had to edit this file.
 *
 * New version:
 *   - reserves a whole ID range for fake players (e.g. 80000–80999)
 *   - automatically registers ALL NPCs in that range
 *   - adding new Fake Players only requires XML/data changes, not Java edits.
 */
public class FakePlayerAI extends AbstractNpcAI
{
    /**
     * First NPC ID reserved for fake players.
     * Make sure you do NOT use this range for normal NPCs.
     */
    private static final int FAKE_NPC_ID_START = 80000;

    /**
     * Last NPC ID reserved for fake players (inclusive).
     * You now have IDs 80000..80999 available for Fake Player NPCs.
     */
    private static final int FAKE_NPC_ID_END = 80999;

    public FakePlayerAI()
    {
        // In your Mobius branch AbstractNpcAI has a no-arg constructor.
        super();

        // Automatically register only existing NPCs in the reserved range.
        for (int id = FAKE_NPC_ID_START; id <= FAKE_NPC_ID_END; id++)
        {
            final NpcTemplate template = NpcData.getInstance().getTemplate(id);
            if (template == null)
            {
                // No NPC template with this ID – skip it.
                continue;
            }

            // If you later want to check only NPCs that actually have <fakePlayer>,
            // you can add an extra condition here. For now, all existing templates
            // in this range will be handled as Fake Players.
            addSpawnId(id);
        }
    }

    /**
     * Called whenever an NPC with a registered ID spawns in the world.
     * Here we attach the FakePlayerEngine “brain” to that NPC.
     */
    @Override
    public void onSpawn(Npc npc)
    {
        // Register this NPC as a fake player in the engine (gives it a brain).
        FakePlayerEngine.getInstance().register(npc);

        // Call the parent implementation (if it does something useful).
        super.onSpawn(npc);
    }

    /**
     * Entry point for the script loader.
     */
    public static void main(String[] args)
    {
        new FakePlayerAI();
    }

    // If you had any extra logic before (onTalk, onFirstTalk, etc.),
    // keep it below this point, unchanged. The only thing we replaced
    // is the long FAKE_PLAYER_IDS array with the ID range approach above.
}
