package org.l2jmobius.gameserver.model.actor.fakeplayer.behavior;

import org.l2jmobius.commons.util.Rnd;
import org.l2jmobius.gameserver.ai.Intention;
import org.l2jmobius.gameserver.geoengine.GeoEngine;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Npc;

public class DwarfHomeRoutineActivity implements FakeActivity
{
    // How far from the NPC we want the dwarf to stop (in game units).
    private static final int STAND_OFF_DISTANCE = 60;

    private static final Location[] DWARVEN_VILLAGE_POINTS =
            {
                    new Location(115741, -181645, -1320), // 27200 Krudel Lizardman (Quest Monster)

                    new Location(116192, -181072, -1336), // 30516 Reep (Weapon Merchant)
                    new Location(115900, -177316, -930),  // 30517 Shari (Armor Merchant)
                    new Location(114883, -177437, -896),  // 30518 Garita (Accessory Merchant)
                    new Location(115326, -181480, -1344), // 30519 Mion (Grocer)
                    new Location(115761, -182372, -1448), // 30521 Murdoc (Warehouse Keeper)
                    new Location(114958, -178142, -832),  // 30523 Gouph (Collector)
                    new Location(114918, -178099, -832),  // 30524 Pippi (Collector)
                    new Location(116135, -178109, -948),  // 30525 Bronk (Head Blacksmith)
                    new Location(116132, -178675, -948),  // 30526 Brunon (Blacksmith)

                    new Location(116707, -182503, -1512), // 30530 Kluto (Guard Captain)
                    new Location(116244, -183400, -1496), // 30531 Rukain (Guard)
                    new Location(115650, -183595, -1496), // 30532 Belkadhi (Guard)
                    new Location(116744, -181490, -1496), // 30533 Garita (Guard)
                    new Location(116744, -181119, -1496), // 30534 Jerin (Guard)

                    new Location(115096, -182145, -1448), // 30535 Archer Captain (Guard)
                    new Location(115327, -182368, -1448), // 30536 Archer Captain (Guard)
                    new Location(115327, -182098, -1448), // 30537 Archer Captain (Guard)
                    new Location(115541, -181932, -1448), // 30538 Archer Captain (Guard)
                    new Location(115795, -182145, -1448), // 30539 Archer Captain (Guard)

                    new Location(117051, -181652, -1408), // 30540 Archer Captain (Guard)
                    new Location(117860, -182299, -1528), // 30540 Archer Captain (Guard)
                    new Location(116635, -184344, -1560), // 30540 Archer Captain (Guard)

                    new Location(117870, -182606, -1528), // 30542 Archer Captain (Guard)
                    new Location(117468, -182138, -1528), // 30543 Archer Captain (Guard)
                    new Location(117163, -182399, -1520), // 30544 Archer Captain (Guard)
                    new Location(117480, -182437, -1528), // 30545 Archer Captain (Guard)
                    new Location(117232, -182973, -1552), // 30546 Archer Captain (Guard)

                    new Location(116602, -181813, -1408), // 30547 Archer Captain (Guard)
                    new Location(116382, -181651, -1408), // 30548 Archer Captain (Guard)
                    new Location(116286, -181935, -1408), // 30549 Archer Captain (Guard)
                    new Location(116444, -182118, -1408), // 30550 Archer Captain (Guard)
                    new Location(116696, -182144, -1408), // 30551 Archer Captain (Guard)

                    new Location(115439, -182052, -1448), // 30572 Bard Rukal (Newbie Guide)
                    new Location(116013, -181952, -1448), // 30573 Rayan (Newbie Guide)
                    new Location(116013, -182156, -1448), // 30574 Gilbert (Newbie Guide)

                    new Location(117061, -181867, -1408), // 30671 Croto (Clan Hall Manager)

                    new Location(116186, -182974, -1520), // 30993 Black Marketeer (Black Marketeer)

                    new Location(116189, -182156, -1520), // 31035 Fishing Guild Member (Fisherman)

                    new Location(116903, -182696, -1528), // 31362 Gatekeeper (Gatekeeper)
                    new Location(116913, -182397, -1520), // 31363 Gatekeeper (Gatekeeper)

                    new Location(116249, -182362, -1512), // 31688 Adventurers Guide (Adventurers' Guide)

                    new Location(116514, -181972, -1512), // 31756 Grand Olympiad Mgr (Grand Olympiad Manager)
                    new Location(116488, -181926, -1512), // 31757 Monument of Heroes (Monument of Heroes)

                    new Location(116220, -181719, -1512), // 31772 Dimensional Merchant (Dimensional Merchant)

                    new Location(117043, -181880, -1408), // 31782 Auctioneer (Auctioneer)
                    new Location(117101, -181880, -1408), // 31783 Auctioneer (Auctioneer)

                    new Location(116568, -182117, -1512), // 32478 Fantasy Isle Npc (PC Bang Point Seller)
                    new Location(116636, -182185, -1512), // 32486 Event Npc (Event Manager)

                    // Fake dwarves (cluster – can be used as "tavern" spot, etc.)
                    new Location(116626, -182352, -1512), // 80010 Borik
                    new Location(116627, -182353, -1512), // 80011 Grenda
                    new Location(116628, -182354, -1512), // 80012 Drogun
                    new Location(116629, -182355, -1512), // 80013 Bruni
            };

    private final Npc _npc;
    private long _nextTime;
    private int _lastIndex = -1;

    public DwarfHomeRoutineActivity(Npc npc)
    {
        _npc = npc;
        _nextTime = System.currentTimeMillis() + Rnd.get(5000, 15000);
    }

    @Override
    public boolean shouldRunNow()
    {
        if (_npc.isMoving())
        {
            return false;
        }
        return System.currentTimeMillis() >= _nextTime;
    }

    @Override
    public void run()
    {
        if (DWARVEN_VILLAGE_POINTS.length == 0)
        {
            return;
        }

        // Pick a random target NPC position (anchor), avoid repeating the last index if possible.
        int index;
        if (DWARVEN_VILLAGE_POINTS.length > 1)
        {
            do
            {
                index = Rnd.get(DWARVEN_VILLAGE_POINTS.length);
            }
            while (index == _lastIndex);
        }
        else
        {
            index = 0;
        }

        _lastIndex = index;
        final Location anchor = DWARVEN_VILLAGE_POINTS[index];

        // Vector from dwarf -> NPC
        final int sx = _npc.getX();
        final int sy = _npc.getY();
        final int sz = _npc.getZ();

        final int tx = anchor.getX();
        final int ty = anchor.getY();
        final int tz = anchor.getZ();

        final int dx = tx - sx;
        final int dy = ty - sy;
        final double dist = Math.sqrt((dx * dx) + (dy * dy));

        Location dest;

        if (dist > (STAND_OFF_DISTANCE + 10))
        {
            // Move towards the NPC, but stop STAND_OFF_DISTANCE units before it.
            final double scale = (dist - STAND_OFF_DISTANCE) / dist;
            final int destX = sx + (int) Math.round(dx * scale);
            final int destY = sy + (int) Math.round(dy * scale);
            final int destZ = tz;

            dest = GeoEngine.getInstance().getValidLocation(
                    sx,
                    sy,
                    sz,
                    destX,
                    destY,
                    destZ,
                    _npc.getInstanceId());
        }
        else
        {
            // Already very close: pick a small ring around the NPC instead.
            final double angle = Math.toRadians(Rnd.get(0, 360));
            final int r = STAND_OFF_DISTANCE;
            final int destX = tx + (int) Math.round(Math.cos(angle) * r);
            final int destY = ty + (int) Math.round(Math.sin(angle) * r);
            final int destZ = tz;

            dest = GeoEngine.getInstance().getValidLocation(
                    sx,
                    sy,
                    sz,
                    destX,
                    destY,
                    destZ,
                    _npc.getInstanceId());
        }

        _npc.getAI().setIntention(Intention.MOVE_TO, dest);

        // Next move decision in 15–30 seconds.
        _nextTime = System.currentTimeMillis() + Rnd.get(15000, 30000);
    }
}
