package mods.recipear.api;

import java.util.EventListener;

public interface IRecipear extends EventListener {
	public void trigger(RecipearEvent event);
}
