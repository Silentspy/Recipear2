package mods.recipear.api;

import cpw.mods.fml.relauncher.Side;

public class RecipearEvent{
	
	Side side;
	boolean server;
	boolean output;

	public RecipearEvent(Side side, boolean output) {
		this.side = side;
		this.server = (side.equals(Side.SERVER)) ? true : false;
		this.output = output;
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
	public boolean isOutput() {
		return output;
	}
	
	@Override
	public String toString() {
		return "RecipearEvent[side=" + side + ", server=" + server
				+ ", output=" + output + "]";
	}
	
	
}
