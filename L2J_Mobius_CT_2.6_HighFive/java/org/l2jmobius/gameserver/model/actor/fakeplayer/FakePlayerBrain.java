package org.l2jmobius.gameserver.model.actor.fakeplayer;

import org.l2jmobius.Config;
import org.l2jmobius.commons.util.Rnd;
import org.l2jmobius.gameserver.ai.Intention;
import org.l2jmobius.gameserver.geoengine.GeoEngine;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.actor.enums.creature.Race;
import org.l2jmobius.gameserver.model.actor.fakeplayer.behavior.FakeBehavior;
import org.l2jmobius.gameserver.model.actor.fakeplayer.behavior.FakeBehaviorFactory;
import org.l2jmobius.gameserver.network.enums.ChatType;
import org.l2jmobius.gameserver.network.serverpackets.NpcSay;
import org.l2jmobius.gameserver.network.serverpackets.SocialAction;

/**
 * Fake Player brain (race-based Phase 2 + Phase 3 dwarves):
 * - Periodic decisions (move / social / idle)
 * - Random wandering around home with geodata
 * - Run/walk variation
 * - Random social actions
 * - Idle turning
 * - Looking at nearby players
 * - Behaviour tuned per race
 * - Dwarves can use advanced composite behavior (Phase 3)
 */
public class FakePlayerBrain
{
    private final Npc _npc;
    private final Location _home;
    private long _nextActionTime = 0;

    // Per-fake-player behaviour values, derived from race
    private final int _moveChance;
    private final int _socialChance;
    private final int _idleTurnChance;
    private final int _lookAtPlayerChance;

    // Optional advanced behavior: currently used for dwarves only (Phase 3)
    private final FakeBehavior _dwarfBehavior;

    public FakePlayerBrain(Npc npc)
    {
        _npc = npc;
        _home = new Location(npc.getX(), npc.getY(), npc.getZ());
        // First decision in 3–10 seconds
        _nextActionTime = System.currentTimeMillis() + Rnd.get(3000, 10000);

        // Start from global defaults
        int move = Config.FAKE_PLAYER_MOVE_CHANCE;
        int social = Config.FAKE_PLAYER_SOCIAL_CHANCE;
        int idleTurn = Config.FAKE_PLAYER_IDLE_TURN_CHANCE;
        int lookAt = 40; // base chance to look at nearby player when idle

        // Race-based template
        Race race = null;
        try
        {
            race = _npc.getTemplate().getRace();
        }
        catch (Exception e)
        {
            // Some custom templates might not have race set; fall back to defaults.
        }

        if (race != null)
        {
            switch (race)
            {
                case HUMAN:
                {
                    // Balanced behaviour
                    move += 0;
                    social += 0;
                    idleTurn += 0;
                    lookAt += 10; // humans tend to look at others a bit more
                    break;
                }
                case ELF:
                {
                    // Calm, gentle, less movement, more social / idle
                    move -= 10;
                    social += 10;
                    idleTurn += 10;
                    lookAt += 10;
                    break;
                }
                case DARK_ELF:
                {
                    // Stoic, shadowy: moderate movement, low social, high watchfulness
                    move -= 5;
                    social -= 10;
                    idleTurn += 10;
                    lookAt += 20;
                    break;
                }
                case DWARF:
                {
                    // Energetic, practical: lots of movement, some socials
                    move += 15;
                    social += 0;
                    idleTurn += 0;
                    lookAt += 0;
                    break;
                }
                case ORC:
                {
                    // Bold warriors: very high movement, lower idle, less subtle social
                    move += 20;
                    social -= 5;
                    idleTurn -= 5;
                    lookAt += 0;
                    break;
                }
                case KAMAEL:
                {
                    // Quiet, watchful: less social, more looking around
                    move -= 5;
                    social -= 5;
                    idleTurn += 10;
                    lookAt += 20;
                    break;
                }
                default:
                {
                    // Other races (if any) use defaults
                    break;
                }
            }
        }

        // Clamp to sane ranges
        _moveChance = Math.max(0, Math.min(100, move));
        _socialChance = Math.max(0, Math.min(100, social));
        _idleTurnChance = Math.max(0, Math.min(100, idleTurn));
        _lookAtPlayerChance = Math.max(0, Math.min(100, lookAt));

        // Phase 3: dwarves get advanced composite behavior via FakeBehaviorFactory
        if (race == Race.DWARF)
        {
            _dwarfBehavior = FakeBehaviorFactory.createBehavior(_npc);
        }
        else
        {
            _dwarfBehavior = null;
        }
    }

    public Npc getNpc()
    {
        return _npc;
    }

    public void think()
    {
        if (_npc.isDead() || _npc.isInCombat())
        {
            return;
        }

        final long now = System.currentTimeMillis();

        // Not yet time for the next decision
        if (now < _nextActionTime)
        {
            return;
        }

        // Let current movement finish if already moving
        if (_npc.isMoving())
        {
            return;
        }

        // Phase 3: if this NPC has a dwarf-specific composite behavior,
        // delegate logic to it and skip the old race-based logic.
        if (_dwarfBehavior != null)
        {
            _dwarfBehavior.think();
            _nextActionTime = now + _dwarfBehavior.getNextThinkDelay();
            return;
        }

        // ---- Original Phase 2 race-based logic for non-dwarves ----

        final int roll = Rnd.get(100);

        // MOVE?
        if (roll < _moveChance)
        {
            final Location target = getRandomNearbyLocation();

            // Avoid useless tiny moves
            if (_npc.calculateDistance3D(target) > 20)
            {
                // Decide run or walk for this move
                final boolean willRun = (Rnd.get(100) < Config.FAKE_PLAYER_RUN_CHANCE);

                if (willRun)
                {
                    _npc.setRunning();
                }
                else
                {
                    _npc.setWalking();
                }

                _npc.getAI().setIntention(Intention.MOVE_TO, target);
            }
        }
        // SOCIAL?
        else if (roll < (_moveChance + _socialChance))
        {
            doRandomSocialAction();

            // 50% chance to also say something when emoting
            if (Rnd.get(100) < 50)
            {
                doRandomTalk();
            }
        }
        // IDLE?
        else
        {
            // Sometimes just idle-turn
            if (Rnd.get(100) < _idleTurnChance)
            {
                doIdleTurn();
            }

            // Sometimes look at a nearby player
            maybeLookAtNearbyPlayer();
        }

        // Schedule next decision in 3–10 seconds
        _nextActionTime = now + Rnd.get(3000, 10000);
    }

    /**
     * Picks a random nearby location around the home position and corrects it via GeoEngine.
     */
    private Location getRandomNearbyLocation()
    {
        final int radius = Config.FAKE_PLAYER_WANDER_RADIUS;

        final double angleRad = Math.toRadians(Rnd.get(0, 360));
        final int distance = Rnd.get(radius / 4, radius);

        final int dx = (int) Math.round(Math.cos(angleRad) * distance);
        final int dy = (int) Math.round(Math.sin(angleRad) * distance);

        final int targetX = _home.getX() + dx;
        final int targetY = _home.getY() + dy;
        final int targetZ = _home.getZ();

        // Ask GeoEngine for the last valid point along the line from current pos to target.
        return GeoEngine.getInstance().getValidLocation(
                _npc.getX(),
                _npc.getY(),
                _npc.getZ(),
                targetX,
                targetY,
                targetZ,
                _npc.getInstanceId());
    }

    /**
     * Performs a random social action (emote), biased by race.
     */
    private void doRandomSocialAction()
    {
        // Default generic set.
        int[] actions = { 1, 2, 3, 4, 5, 6 };

        Race race = null;
        try
        {
            race = _npc.getTemplate().getRace();
        }
        catch (Exception e)
        {
            // ignore, use default set
        }

        if (race != null)
        {
            switch (race)
            {
                case ELF:
                    // graceful / calm – bow, think, clap
                    actions = new int[]
                            {
                                    2, 3, 4
                            };
                    break;
                case DARK_ELF:
                    // stoic / shadowy – think, maybe “cool pose”
                    actions = new int[]
                            {
                                    4, 6
                            };
                    break;
                case DWARF:
                    // energetic – victory, cheer, clap
                    actions = new int[]
                            {
                                    1, 3, 5
                            };
                    break;
                case ORC:
                    // loud – strong gestures
                    actions = new int[]
                            {
                                    1, 3
                            };
                    break;
                case KAMAEL:
                    // reserved – bow, think
                    actions = new int[]
                            {
                                    2, 4
                            };
                    break;
                case HUMAN:
                default:
                    // balanced: keep the full set
                    actions = new int[]
                            {
                                    1, 2, 3, 4, 5, 6
                            };
                    break;
            }
        }

        final int actionId = actions[Rnd.get(actions.length)];
        _npc.broadcastPacket(new SocialAction(_npc.getObjectId(), actionId));
    }

    private void doRandomTalk()
    {
        Race race = null;
        try
        {
            race = _npc.getTemplate().getRace();
        }
        catch (Exception e)
        {
        }

        String[] lines;

        if (race == null)
        {
            lines = new String[]
                    {
                            "Another day in this strange world...",
                            "I wonder what tomorrow will bring."
                    };
        }
        else
        {
            switch (race)
            {
                case HUMAN:
                    lines = new String[]
                            {
                                    "Discipline and steel, that is the path.",
                                    "Have you trained today, traveler?",
                                    "These streets have seen many battles."
                            };
                    break;
                case ELF:
                    lines = new String[]
                            {
                                    "Even here, I can hear the whisper of the trees.",
                                    "May harmony guide your steps.",
                                    "The world still heals from old wounds."
                            };
                    break;
                case DARK_ELF:
                    lines = new String[]
                            {
                                    "Shadows speak louder than the sun.",
                                    "Power is nothing without control.",
                                    "Not all darkness is your enemy."
                            };
                    break;
                case DWARF:
                    lines = new String[]
                            {
                                    "A good hammer solves many problems!",
                                    "I could forge a better blade than that.",
                                    "Where's the nearest tavern around here?"
                            };
                    break;
                case ORC:
                    lines = new String[]
                            {
                                    "Where is the battle?! Grulk grows bored!",
                                    "Strength decides who is right.",
                                    "I smell fear... or is that just the city?"
                            };
                    break;
                case KAMAEL:
                    lines = new String[]
                            {
                                    "The sky remembers every war.",
                                    "My eyes are always watching.",
                                    "We were made as weapons... but we choose our path."
                            };
                    break;
                default:
                    lines = new String[]
                            {
                                    "Another day in this strange world...",
                                    "I wonder what tomorrow will bring."
                            };
                    break;
            }
        }

        final String text = lines[Rnd.get(lines.length)];

        // Correct Mobius HF chat packet
        _npc.broadcastPacket(new NpcSay(_npc.getObjectId(), ChatType.NPC_GENERAL, _npc.getId(), text));
    }

    /**
     * Turns the NPC to a random heading while idle.
     */
    private void doIdleTurn()
    {
        final int newHeading = Rnd.get(0, 65535);
        _npc.setHeading(newHeading);
        _npc.broadcastInfo();
    }

    /**
     * Occasionally look at the nearest player while idling.
     */
    private void maybeLookAtNearbyPlayer()
    {
        final int range = 300; // how far they "notice" players

        Player nearest = null;
        double nearestDist = Double.MAX_VALUE;

        for (Player player : World.getInstance().getVisibleObjects(_npc, Player.class))
        {
            if ((player == null) || player.isDead())
            {
                continue;
            }

            final double dist = _npc.calculateDistance2D(player);
            if ((dist <= range) && (dist < nearestDist))
            {
                nearestDist = dist;
                nearest = player;
            }
        }

        if (nearest == null)
        {
            return;
        }

        // Race-based look probability
        if (Rnd.get(100) < _lookAtPlayerChance)
        {
            // 3-argument version is widely available; if not, use calculateHeadingTo(player.getLocation()).
            final int heading = _npc.calculateHeadingTo(nearest.getLocation());
            _npc.setHeading(heading);
            _npc.broadcastInfo();
        }
    }
}
