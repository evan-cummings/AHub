package fletchplugins.ahub;

import fletchplugins.ahub.util.PluginCache;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

        String playerName=event.getPlayer().getName();

        if(PluginCache.alreadyTeleporting.contains(playerName)){
            if(event.getFrom().getBlockX() != event.getTo().getBlockX() ||
                    event.getFrom().getBlockY() != event.getTo().getBlockY() ||
                    event.getFrom().getBlockZ() != event.getTo().getBlockZ()){

                PluginCache.alreadyTeleporting.remove(playerName);
                PluginCache.playerHasMoved.add(playerName);
                event.getPlayer().sendMessage(ChatColor.RED+"You moved! Cancelling teleport.");

            }
        }
    }
}
