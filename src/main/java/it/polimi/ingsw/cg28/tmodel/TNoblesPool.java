package it.polimi.ingsw.cg28.tmodel;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

public class TNoblesPool implements Serializable{
		
	private static final long serialVersionUID = 3738220987764214769L;
	private final List<Color> pool;
	
	public TNoblesPool(List<Color> pool) {
		this.pool=pool;
	}

	public List<Color> getPool() {
		return pool;
	}
	
	public Color getPool(int index) {
		return pool.get(index);
	}

	public int size() {
		return pool.size();
	}
	
}
