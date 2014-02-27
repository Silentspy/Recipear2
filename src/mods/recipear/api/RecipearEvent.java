package mods.recipear.api;

import cpw.mods.fml.relauncher.Side;

public class RecipearEvent{
	
	Side side;
	boolean server;
	boolean modify;
	
	public RecipearEvent(Side side, boolean modify) {
		this.side = side;
		this.server = (side.equals(Side.SERVER)) ? true : false;
		this.modify = modify;
	}

	/**
	 * @return the side
	 */
	public Side getSide() {
		return side;
	}

	/**
	 * @return the server
	 */
	public boolean isServer() {
		return server;
	}

	/**
	 * @return the modify
	 */
	public boolean isModify() {
		return modify;
	}
}
