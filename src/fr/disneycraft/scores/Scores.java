package fr.disneycraft.scores;

import org.bukkit.plugin.java.JavaPlugin;

public class Scores extends JavaPlugin{
	
	
	@Override
    public void onEnable(){
        // Actions � effectuer au d�marrage du plugin, c'est-�-dire :
        //   - Au d�marrage du serveur
        //   - Apr�s un /reload
    }
	
	@Override
    public void onDisable(){
        // Actions � effectuer � la d�sactivation du plugin
        //   - A l'extinction du serveur
        //   - Pendant un /reload
    }
}
