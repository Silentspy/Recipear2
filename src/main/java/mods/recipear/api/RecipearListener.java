package mods.recipear.api;

import javax.swing.event.EventListenerList;

public class RecipearListener {
	
	EventListenerList listeners = new EventListenerList();
	
	public void add(IRecipear listener) 
	{
		listeners.add(IRecipear.class, listener);
	}
	public void remove(IRecipear listener) 
	{
		listeners.remove(IRecipear.class, listener);
	}
	public void trigger(RecipearEvent event) 
	{
	     for (Object listener : listeners.getListenerList()) 
	     {
	          if (listener instanceof IRecipear) 
	          {
	                ((IRecipear)listener).trigger(event);
	          }            
	     }
	}
}
