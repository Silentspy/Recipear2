package mods.recipear;

import java.io.Serializable;

public class BannedRecipe implements Serializable{
	
	public int id, metadata;
	public String name, type;

	public BannedRecipe(int id) 
	{ 
		this(id, -1, "DEFAULT");
	}

	public BannedRecipe(int id, int metadata) 
	{ 
		this(id, metadata, "DEFAULT");
	}

	public BannedRecipe(int id, int metadata, String type) 
	{ 
		this.id = id;
		this.metadata = metadata;
		this.name = null;
		this.type = type;
	}

	public BannedRecipe(String name) 
	{
		this(name, "DEFAULT");
	}

	public BannedRecipe(String name, String type) 
	{
		this.name = name;
		this.id = 0;
		this.metadata = -1;
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + metadata;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BannedRecipe)) {
			return false;
		}
		BannedRecipe other = (BannedRecipe) obj;
		if (id != other.id) {
			return false;
		}
		if (metadata != other.metadata) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() 
	{
		return String.format("BannedRecipe[name: %s, id: %s, metadata: %s, type: %s]", name, id, metadata, type);
	}
}
