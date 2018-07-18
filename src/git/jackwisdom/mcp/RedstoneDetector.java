package git.jackwisdom.mcp;

import com.sun.org.apache.regexp.internal.RE;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class RedstoneDetector extends JavaPlugin implements Listener {
    public static HashMap<Integer,Integer> map;
    int limit;
    String msg;
    @Override
    public void onEnable(){
        saveDefaultConfig();
        map=new HashMap<>();
        limit=getConfig().getInt("limit");
        int reset=getConfig().getInt("reset");
        msg=getConfig().getString("msg");
        Bukkit.getPluginManager().registerEvents(this,this);
        new BukkitRunnable() {
            @Override
            public void run() {
                map.clear();
            }
        }.runTaskTimerAsynchronously(this,reset,reset);
    }
    @EventHandler
    public void onBlockRedstone(BlockRedstoneEvent event){
        if(event.getBlock().getType()!=Material.REDSTONE){
            return;
        }
       int hash= event.getBlock().getLocation().hashCode();
       if(map.get(hash)==null){
           map.put(hash,1);
           return;
       }
          int amount=map.get(hash);
          if(amount>=limit) {
              event.getBlock().breakNaturally();
              map.remove(hash);
              return;
          }
          map.put(hash,amount+1);

    }
}
