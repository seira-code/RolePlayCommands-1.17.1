package ml.meddiwale.rpcommands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.regex.Pattern;

public final class RPcommands extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        if(getConfig().getString("lang").equalsIgnoreCase("ru"))
            getLogger().info("Успешно загрузил РП команды!");
        else
            getLogger().info("Load Successful");

        getCommand("me").setExecutor(this);
        getCommand("do").setExecutor(this);
        getCommand("todo").setExecutor(this);
        getCommand("try").setExecutor(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    public void fo(Location l, String text) {
        for(Player pl : l.getNearbyPlayers(15)) {
            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            if(getConfig().getString("lang").equalsIgnoreCase("ru"))
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&' ,"&cТолько игрок может использовать эту команду!"));
            else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&' ,"&сOnly the player can use this command!"));
            return true;
        }
        if(args.length == 0) return false;

        Player p = (Player) sender;

        StringBuilder str = new StringBuilder();
        for(int i=0; i<args.length; i++) {
            str.append(args[i]).append(" ");
        }
        String joinArgs = str.toString();

        switch(label) {
            case "me":
                fo(p.getLocation(), "&d*" + p.getName() + " " + joinArgs);
                break;
            case "do":
                fo(p.getLocation(), "&7" + joinArgs + " |&8 " + p.getName());
                break;
            case "todo":
                String[] args2 = joinArgs.split(Pattern.quote("*"));
                if(args2.length != 2) return false;
                if(getConfig().getString("lang").equalsIgnoreCase("ru"))
                    fo(p.getLocation(), '"' + args2[0] + '"' + ", - сказал(-а) " + p.getName() + ", &d" + args2[1]);
                else
                    fo(p.getLocation(), '"' + args2[0] + '"' + ", - said " + p.getName() + ", &d" + args2[1]);
                break;
            case "try":
                Random r = new Random();
                int x = r.nextInt((2 - 1) + 1) + 1; // r.nextInt((max - min) + 1) + min;
                if(x == 1) {
                    if(getConfig().getString("lang").equalsIgnoreCase("ru"))
                        fo(p.getLocation(), "&e" + p.getName() + " &7" + joinArgs + " &a| Удачно");
                    else
                        fo(p.getLocation(), "&e" + p.getName() + " &7" + joinArgs + " &a| Successful");
                }
                else {
                    if(getConfig().getString("lang").equalsIgnoreCase("ru"))
                        fo(p.getLocation(), "&e" + p.getName() + " &7" + joinArgs + " &4| Удачно");
                    else
                        fo(p.getLocation(), "&e" + p.getName() + " &7" + joinArgs + " &4| Unsuccessful");
                }
                break;
        }

        return true;
    }
}
