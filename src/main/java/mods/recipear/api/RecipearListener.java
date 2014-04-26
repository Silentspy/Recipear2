package mods.recipear.api;

import javax.swing.event.EventListenerList;

import org.apache.commons.lang3.StringUtils;

import mods.recipear.BannedRecipes;
import mods.recipear.RecipearLogger;

public class RecipearListener {
	
	EventListenerList listeners = new EventListenerList();
	
	public void add(IRecipear listener) 
	{
		RecipearLogger.info(listener.getName() + " module loaded[" + addTypes(listener) + "]");
		listeners.add(IRecipear.class, listener);
	}
	
	public void remove(IRecipear listener) 
	{
		RecipearLogger.info(listener.getName() + " module unloaded[" + removeTypes(listener) + "]");
		listeners.remove(IRecipear.class, listener);
	}
	public void trigger(RecipearEvent event) 
	{
		long startTime = System.currentTimeMillis();
		RecipearLogger.info("Event triggered " + event.toString());
		for (Object listener : listeners.getListenerList()) 
		{
		     if (listener instanceof IRecipear) 
		     {
		    	 ((IRecipear)listener).trigger(event);
		     }       
		}
		RecipearLogger.info("Event finished in " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	public String addTypes(IRecipear listener) 
	{
		BannedRecipes.AddBannedRecipeType((listener).getTypes());
		return StringUtils.join((listener).getTypes(), ", ");
	}
	
	public String removeTypes(IRecipear listener) 
	{
		for(String type : (listener).getTypes()) { 
			BannedRecipes.getBannedRecipeTypes().remove(type);
		}
		return StringUtils.join((listener).getTypes(), ", ");
	}
}
