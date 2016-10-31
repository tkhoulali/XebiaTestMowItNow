package com.xebian.impl;

import java.util.concurrent.Callable;


/**
 * @author TK
 * Tondeuse Object with manipulation methods
 */

public class Tondeuse implements Callable<Tondeuse>  {
	int x, y;
	Orientation orientation;
	Surface surface;
	String commandes;


	/**
	 * @param x : position X
	 * @param y : position Y
	 * @param orientation : Enum : N, W, S, E
	 * @param surface 
	 * @param commandes
	 */
	public Tondeuse(int x, int y, Orientation orientation, Surface surface, String commandes) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.surface = surface;
		this.commandes = commandes;
	}

	public String getCommandes() {
		return commandes;
	}

	public void setCommandes(String commandes) {
		this.commandes = commandes;
	}

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(Surface surface) {
		this.surface = surface;
	}

	public int getPositionX() {
		return x;
	}

	public void setPositionX(int x) {
		if (x < 0)
			this.x = 0;
		else
			this.x = x;
	}

	public int getPositionY() {
		return y;
	}

	public void setPositionY(int y) {
		if (y < 0)
			this.y = 0;
		else
			this.y = y;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * set commande to tandeuse.
	 * @param t
	 * @param d : Enum :  G gauche, D droite, A avancer
	 * Algo : 
	 * N+G = W, N+D = E, S+G= E, S+D = W
	 * W+G = S, W+D = N, E+G = N, E+D = S
	 */
	public void setCommande() {

		Direction d;
		Orientation o;
		String[] orders = this.commandes.split("");
		
		for(String str : orders) {

			d = Direction.valueOf(str);
			
			if (d == Direction.A) {
				setCommandeA();
			} else {
				o = this.getOrientation();
				switch (o) {
				case N:
					if (d == Direction.G)
						this.setOrientation(Orientation.W);
					else if (d == Direction.D)
						this.setOrientation(Orientation.E);
					break;
				case S:
					if (d == Direction.G)
						this.setOrientation(Orientation.E);
					else if (d == Direction.D)
						this.setOrientation(Orientation.W);
					break;
				case W:
					if (d == Direction.G)
						this.setOrientation(Orientation.S);
					else if (d == Direction.D)
						this.setOrientation(Orientation.N);
					break;
				case E:
					if (d == Direction.G)
						this.setOrientation(Orientation.N);
					else if (d == Direction.D)
						this.setOrientation(Orientation.S);
					break;
				}
			}
		}
	}

	/**
	 * @param t
	 * Algo :
	 * N = (x, y+1)
	 * S = (x, y-1)
	 * w = (x-1, y)
	 * E = (x+1, y)
	 */
	public void setCommandeA() {
		Orientation o = this.getOrientation();
		Surface s = this.getSurface();
		switch (o) {
		case N:
			if (s.checkCase(this.getPositionX(), this.getPositionY() + 1))
				this.setPositionY(this.getPositionY() + 1);
			break;
		case S:
			if (s.checkCase(this.getPositionX(), this.getPositionY() - 1))
				this.setPositionY(this.getPositionY() - 1);
			break;
		case W:
			if (s.checkCase(this.getPositionX() - 1, this.getPositionY()))
				this.setPositionX(this.getPositionX() - 1);
			break;
		case E:
			if (s.checkCase(this.getPositionX() + 1, this.getPositionY())) {
				this.setPositionX(this.getPositionX() + 1);
			}
			break;
		}
	}

	public Tondeuse call() {
		System.out.println("Started task");
		setCommande();
		return this;
	}
}
