package com.xebian.impl;

/**
 * @author TK
 */
public class Surface {

	int matrice[][];
	
	public Surface(int ligne, int colonne) {
		try {
			this.matrice = new int[ligne][colonne];
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("invalid params");
		} catch (NegativeArraySizeException e) {
			throw new NegativeArraySizeException("params should be positive");
		}
	}

	/**
	 * affect val to matrice[x][y]
	 * can use it to check if matrice[x][y] is correct
	 * @param x
	 * @param y
	 * @param val
	 * @return boolean
	 */
	public boolean setCase(int x, int y, int val) {
		try {
			this.matrice[x][y] = val;
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (NegativeArraySizeException e) {
			return false;
		}
	}

	/**
	 * check if matrice[x][y] is correct
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean checkCase(int x, int y) {
		if (x <= this.matrice.length && y <= this.matrice[0].length)
			return true;
		else
			return false;
	}

}
