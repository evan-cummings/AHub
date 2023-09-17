package fletchplugins.ahub;

import fletchplugins.ahub.util.PluginCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CommandClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(command.getName().equalsIgnoreCase("hub")){

                Plugin plugin= Bukkit.getServer().getPluginManager().getPlugin("AHub");
                String serverTpCommand = "server "+plugin.getConfig().getString("hubServer");
                Player player=(Player) sender;

                //System.out.println(serverTpCommand);
                if(!PluginCache.alreadyTeleporting.contains(player.getName())) {
                    int delay = plugin.getConfig().getInt("commandTimer");
                    PluginCache.alreadyTeleporting.add(player.getName());
                    sender.sendMessage(ChatColor.GREEN + "Sending you to the hub in " + delay + " seconds, move to cancel the teleport!");


                    BukkitRunnable runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!PluginCache.playerHasMoved.contains(player.getName()) && PluginCache.alreadyTeleporting.contains(player.getName())) {
                                String server=plugin.getConfig().getString("hubServer");
                                try (
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        DataOutputStream dos = new DataOutputStream(baos)
                                ){

                                    dos.writeUTF("Connect");
                                    dos.writeUTF(server);
                                    player.sendPluginMessage(plugin, "BungeeCord", baos.toByteArray());
                                } catch (final IOException e){
                                    e.printStackTrace();
                                }

                                PluginCache.alreadyTeleporting.remove(player.getName());

                            }
                            PluginCache.playerHasMoved.remove(player.getName());

                        }
                    };
                    if(player.hasPermission("ahub.adminbypass")){
                        runnable.runTask(plugin);
                    }
                    else{
                        runnable.runTaskLater(plugin, delay * 20L);
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED+"You're already teleporting bossman give it a minute.");
                }

            }
            if(command.getName().equalsIgnoreCase("serverhubreload")){
                Plugin plugin=Bukkit.getServer().getPluginManager().getPlugin("AHub");
                plugin.reloadConfig();
            }

        }
        return true;
    }
}
