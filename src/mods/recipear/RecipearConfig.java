package mods.recipear;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraftforge.common.Configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RecipearConfig {
	public static boolean debug = false;
	public static boolean removeclient = false;
	public static boolean removeIngame = false;
	public static int removeIngameInterval = 60;
	public static String removeIngameMsg = "$9%s $eis $cbanned$e, removing from inventory...";
	public static String placeholderName = "$cBanned Recipe";
	public static String placeholderDescription = "$eThis item has been disabled by the server";
	public static File recipearDataFolder;
	
	public static void init(FMLPreInitializationEvent event) 
	{
		recipearDataFolder = new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + "Recipear");
		recipearDataFolder.mkdir();
		
		reload();
	}
	
	public static void reload() {
		Properties();
		BannedRecipesProperties();
	}

	private static void Properties() 
	{
		RecipearLogger.info("loading Recipear" + File.separator + "Core.cfg");
		Configuration cfg = new Configuration(new File(recipearDataFolder + File.separator + "Core.cfg"));
		try
		{
			cfg.load();
			removeclient = cfg.get("Features", "RemoveClient", removeclient, "Set this to true if you want the items to be fully removed from the client in addition to the server, rather than being just a placeholder").getBoolean(false);
			removeIngame = cfg.get("Features", "RemoveIngame", removeIngame, "Set this to true if you want to remove banned items from players at login and every interval").getBoolean(false);
			removeIngameInterval = cfg.get("Features", "RemoveIngameInterval", removeIngameInterval, "Interval in seconds to check if player have a banned item, default is 60 seconds").getInt(60);
			removeIngameMsg = cfg.get("Features", "RemoveIngameMsg", removeIngameMsg).getString();
			debug  = cfg.get(cfg.CATEGORY_GENERAL, "Debug", debug, "Turns on debug output in console/log, good if you need to see the inner workings.").getBoolean(false);
			placeholderDescription = cfg.get("BannedItem", "Description", placeholderDescription, "Description of banned item, supports color formatting").getString();
			placeholderName = cfg.get("BannedItem", "Name", placeholderName, "Name of the item that is banned, supports color formatting").getString();
		}
		catch (Exception e) 
		{
			RecipearLogger.severe("has a problem loading Recipear" + File.separator + "Core.cfg: " + e.getMessage());
		}
		finally 
		{
			cfg.save();
		}
	}

	private static void BannedRecipesProperties() {
		try
		{
			RecipearLogger.info("loading Recipear" + File.separator + "BannedRecipes.cfg");
			File file = new File(recipearDataFolder + File.separator + "BannedRecipes.cfg");
			String comment = "# Check out https://github.com/Silentspy/Recipear2#introduction\r\n"
					+ "# for up-to-date examples and introduction\r\n";

			if(createCfgFile(file, comment)) {
				List<String> lines = Files.readLines(file, Charsets.ISO_8859_1);

				int count = 0, ITEMID = 0, METADATA = -1;
				String NAME = null;
				
				BannedRecipes.getBannedRecipes().clear();

				for(String line : lines) {
					count++;
					if((line.length() > 0) && (!line.substring(0,1).contains("#"))) 
					{
						// Clean from comments and whitespace
						line = line.replaceAll("\\s+","").split("#")[0];
						
						String delim = ":";
						String esc = "\\";
						String regex = "(?<!" + Pattern.quote(esc) + ")" + Pattern.quote(delim);

						String[] BannedRecipeRaw = line.split(regex);

						NAME = null;
						ITEMID = 0;
						METADATA = -1;
						String TYPE = "DEFAULT";

						try 
						{
							if(RecipearUtil.isInteger(BannedRecipeRaw[0]) && (Integer.valueOf(BannedRecipeRaw[0]) > 0)) {
								ITEMID = Integer.parseInt(BannedRecipeRaw[0]);
								if((BannedRecipeRaw.length > 1) && (RecipearUtil.isInteger(BannedRecipeRaw[1]))) {
									METADATA = (BannedRecipeRaw.length > 1) ? Integer.parseInt(BannedRecipeRaw[1]) : -1;
									TYPE = (BannedRecipeRaw.length > 2) ? BannedRecipeRaw[2].toUpperCase() : "DEFAULT";
								} else {
									TYPE = (BannedRecipeRaw.length > 1) ? BannedRecipeRaw[1].toUpperCase() : "DEFAULT";
								}
							} else {
								NAME = BannedRecipeRaw[0].toLowerCase().replace("\\", "");
								TYPE = (BannedRecipeRaw.length > 1) ? BannedRecipeRaw[1].toUpperCase() : "DEFAULT";
							}
						}
						catch (Exception e) 
						{
							RecipearLogger.warning("Failed to add BannedRecipe at line " + count + ", check your formatting");
							continue;
						}

						BannedRecipe BANNEDRECIPE = new BannedRecipe(ITEMID, METADATA, TYPE);

						if(ITEMID == 0) {
							BANNEDRECIPE.name = NAME;
						}
							
						BannedRecipes.AddBannedRecipe(BANNEDRECIPE);

						RecipearLogger.info("Added: " + BANNEDRECIPE.toString());
					}
				}
			}
		} 
		catch (Exception e) 
		{
			RecipearLogger.severe("has a problem loading Recipear" + File.separator + "BannedRecipes.cfg: " + e.getMessage());
		}
	}

	private static boolean createCfgFile(File file, String comment) {
		if(!file.exists()) 
		{
			try 
			{
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(comment);
				bw.close();
				return true;
			}
			catch (Exception e) 
			{
				RecipearLogger.info(e.getMessage());
				return false;
			}
		}

		return true;
	}
}
