package fr.disneycraft.scores;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Scores extends JavaPlugin{
	
	String name;
	private File file;
    private FileConfiguration fileConfig;
    
	
	@Override
    public void onEnable(){
        // Actions � effectuer au d�marrage du plugin, c'est-�-dire :
        //   - Au d�marrage du serveur
        //   - Apr�s un /reload
		
		System.out.println("Activation du systeme de scores");
		getServer().getPluginManager().registerEvents(new LoginListener(), this);
		this.loadConfigFile();
    }
	
	@Override
    public void onDisable(){
        // Actions � effectuer � la d�sactivation du plugin
        //   - A l'extinction du serveur
        //   - Pendant un /reload
		System.out.println("Desactivation du systeme de scores");
    }
	
	public final class LoginListener implements Listener {
	    @EventHandler
	    public void onLogin(PlayerLoginEvent event) {
	    	Player player = event.getPlayer();
            if(!player.hasPermission("dcscores.notlogin")){
            	Date actuelle = new Date();
             	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
             	String dat = dateFormat.format(actuelle);
            	fileConfig = YamlConfiguration.loadConfiguration(file);
            	if(!fileConfig.contains(player.getName()))
            	{
            		fileConfig.set(player.getName(),dat);
            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dcscores init "+player.getName());
            		try
                    {
                            // On essait de sauvegarder
                            fileConfig.save(file);
                    }
                    catch(IOException ex)
                    {
                    }
            	}
            	else
            	{
            		if(!fileConfig.getString(player.getName()).equals(dat))
                	{
                		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dcscores loginadd "+player.getName()+" 1");
                		fileConfig.set(player.getName(), null);
                		fileConfig.set(player.getName(),dat);
                		try
                        {
                                // On essai de sauvegarder
                                fileConfig.save(file);
                        }
                        catch(IOException ex)
                        {
                        }
                	}
            		
            	}	
            }
	    }
	}
	
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
		 if(commandLabel.equals("dcscores")); // Si la commande est bien dcscores, on continue
        {
                if(args.length == 1) // On v�rifie combien il y a d'arguments
                {
                        String cmd = args[0]; // Si il y en a un seul, on le met dans une variable.
                        
                        if(cmd.equals("help")) // On la compare pour savoir si c'est message
                        {
                                sender.sendMessage("Gestion des scores Disneycraft"); // On envoit un message au joueur qui a tap�e la commande, pour vous prouver que ca marche!
                                return true; // Tous a bien march�, on retourne vrai
                        }
                }
                
               
                if(args.length == 2){
                	String cmd = args[0];
                	String player = args[1];
                	
                	if(cmd.equals("init")){
                		//-- TODO : Initialiser le score � 0 dans la base de donn�e pour le joueur player
                		String post="init=true&player="+player;
                		post(post);
                	}
                }
	            if(sender.hasPermission("dcscores.manage")){
	                if(args.length == 3){
	                	String cmd = args[0];
	                	String player = args[1];
	                	int value = Integer.parseInt(args[2]);
	                	
	                	if(cmd.equals("add")){
	                		//-- TODO : Incr�menter le score du joueur player de value dans la base de donn�e
	                		String post="login=0&player="+player+"&value="+value;
	                		post(post);
	                	}
	                	if(cmd.equals("loginadd")){
	                		//-- TODO : Incr�menter le score du joueur player de value dans la base de donn�e
	                		String post="login=1&player="+player+"&value="+value;
	                		post(post);
	                	}
	                }
                }
        }
        return false;
    }
	
	
	public void loadConfigFile()
    {
	    // On initialise le fichier
	    file = new File(getDataFolder(), "config.yml");
	    
	    if(!file.exists()) // Si le fichier n'existe pas
	    {
	            // On initialise le fileConfig en ouvrant une configuration, ici notre fichier
	            fileConfig = YamlConfiguration.loadConfiguration(file);
	            
	            	Date actuelle = new Date();
	            	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            	String dat = dateFormat.format(actuelle);
	            		
	            fileConfig.set("tyhupo", dat);
	            
	            try
	            {
	                    // On essait de sauvegarder
	                    fileConfig.save(file);
	            }
	            catch(IOException ex)
	            {
	            }
	    }
    }
	
	public void post(String post){
		String ur = "www.disneycraft.fr\\scores.php";
		try{
			URL url = new URL(ur);
			URLConnection  conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(post);
			writer.flush();
		}
		catch(Exception e){}
	}
}

