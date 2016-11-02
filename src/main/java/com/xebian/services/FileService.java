package com.xebian.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.xebian.impl.Direction;
import com.xebian.impl.Orientation;
import com.xebian.impl.Surface;
import com.xebian.impl.Tondeuse;

/**
 * @author TK
 *
 */
public class FileService {

	public static List<Callable<Tondeuse>> executeFile(String path) throws Exception {
		BufferedReader br = null;

		List<Callable<Tondeuse>> taches = new ArrayList<Callable<Tondeuse>>();

		try {
			String line = "";
			Surface s = null;

			br = new BufferedReader(new FileReader(path));

			// Creation de la surface
			if ((line = br.readLine()) != null && !line.isEmpty()) {
				s = createSurface(line);
			} else {
				throw new Exception("Empty File detected !!");
			}

			while ((line = br.readLine()) != null) {
				// suppose instructions are always on the next line after
				// Tondeuse line
				String commandes = br.readLine();
				if (commandes == null || commandes.isEmpty()) {
					throw new Exception("File format not accepted : no commandes detected after " + line);
				}
				Tondeuse t = createTondeuse(line, commandes, s);
				taches.add(t);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return taches;
	}

	/**
	 * Tondeuse creation
	 * 
	 * @param line
	 * @param commandes
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static Tondeuse createTondeuse(String line, String commandes, Surface s) throws Exception {
		String[] tondeuseLine = line.split(" ");
		int xt = Integer.parseInt(tondeuseLine[0]);
		int yt = Integer.parseInt(tondeuseLine[1]);
		Orientation o;

		// check orientation
		if (!isOrientation(tondeuseLine[2])) {
			throw new IllegalArgumentException("Orientation :" + tondeuseLine[2] + " is not correct !!");
		} else {
			o = Orientation.valueOf(tondeuseLine[2]);
		}

		// check commandes
		if (!checkCommandes(commandes)) {
			throw new IllegalArgumentException("Instructions :" + commandes + " are not correct !!");
		}
		return new Tondeuse(xt, yt, o, s, commandes);
	}

	/**
	 * Surface creation
	 * 
	 * @param line
	 * @return
	 */
	public static Surface createSurface(String line) {
		String[] sufaceLine = line.split(" ");
		int x = Integer.parseInt(sufaceLine[0]);
		int y = Integer.parseInt(sufaceLine[1]);
		return new Surface(x, y);
	}

	public static boolean checkCommandes(String line) {
		String[] commandes = line.split("");

		for (String str : commandes) {
			if (!isDirection(str))
				return false;
		}
		return true;
	}

	public static boolean isDirection(String test) {

		for (Direction d : Direction.values()) {
			if (d.name().equals(test)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOrientation(String test) {

		for (Orientation o : Orientation.values()) {
			if (o.name().equals(test)) {
				return true;
			}
		}
		return false;
	}
}
