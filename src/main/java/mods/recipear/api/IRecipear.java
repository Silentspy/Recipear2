package mods.recipear.api;

import java.util.EventListener;

public interface IRecipear extends EventListener {
	public void trigger(RecipearEvent event);
	public String[] getTypes();
	public String getName();
	public String getFullName();
	public String getModID();
}
