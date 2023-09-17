package fletchplugins.ahub;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public final class AHub extends JavaPlugin implements PluginMessageListener {
    PlayerMoveListener playerMoveListener=new PlayerMoveListener();

    @Override
    public void onEnable() {

        System.out.println("Starting Fletch's Server Hub Plugin");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.getCommand("hub").setExecutor(new CommandClass());
        this.getCommand("serverhubreload").setExecutor(new CommandClass());
        this.getServer().getPluginManager().registerEvents(playerMoveListener, this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel")) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
        }
    }
}
