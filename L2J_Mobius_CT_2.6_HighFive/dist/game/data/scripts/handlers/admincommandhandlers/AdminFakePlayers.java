package handlers.admincommandhandlers;

import org.l2jmobius.gameserver.data.xml.FakePlayerData;
import org.l2jmobius.gameserver.handler.IAdminCommandHandler;
import org.l2jmobius.gameserver.managers.FakePlayerChatManager;
import org.l2jmobius.gameserver.managers.FakePlayerEngine;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Player;

/**
 * @author Mobius
 */
public class AdminFakePlayers implements IAdminCommandHandler
{
    private static final String[] ADMIN_COMMANDS =
            {
                    "admin_fakechat",
                    "admin_respawn_fakeplayers"
            };

    @Override
    public boolean onCommand(String command, Player activeChar)
    {
        if (command.startsWith("admin_fakechat"))
        {
            final String[] words = command.substring(15).split(" ");
            if (words.length < 3)
            {
                activeChar.sendSysMessage("Usage: //fakechat playername fpcname message");
                return false;
            }

            final Player player = World.getInstance().getPlayer(words[0]);
            if (player == null)
            {
                activeChar.sendSysMessage("Player not found.");
                return false;
            }

            final String fpcName = FakePlayerData.getInstance().getProperName(words[1]);
            if (fpcName == null)
            {
                activeChar.sendSysMessage("Fake player not found.");
                return false;
            }

            String message = "";
            for (int i = 0; i < words.length; i++)
            {
                if (i < 2)
                {
                    continue;
                }

                message += (words[i] + " ");
            }

            FakePlayerChatManager.getInstance().sendChat(player, fpcName, message);
            activeChar.sendSysMessage("Your message has been sent.");
        }
        else if (command.equals("admin_respawn_fakeplayers"))
        {
            FakePlayerEngine.getInstance().respawnAllFakePlayers();
            activeChar.sendSysMessage("Fake players have been respawned.");
        }

        return true;
    }

    @Override
    public String[] getCommandList()
    {
        return ADMIN_COMMANDS;
    }
}
