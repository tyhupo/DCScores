package fr.disneycraft.scores;

import org.bukkit.plugin.java.JavaPlugin;

public class Scores extends JavaPlugin{
	
	
	@Override
    public void onEnable(){
        // Actions à effectuer au démarrage du plugin, c'est-à-dire :
        //   - Au démarrage du serveur
        //   - Après un /reload
    }
	
	@Override
    public void onDisable(){
        // Actions à effectuer à la désactivation du plugin
        //   - A l'extinction du serveur
        //   - Pendant un /reload
    }
}
