package com.xebian.test.tk;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.xebian.impl.Orientation;
import com.xebian.impl.Surface;
import com.xebian.impl.Tondeuse;
import com.xebian.services.TasksService;

public class TestTondeuse {


	@Test
	public void testInitTondeuse() {
		Surface surface = new Surface(10, 10);
		Tondeuse tondeuse = new Tondeuse(10, 10, Orientation.N, surface, "");
		assertEquals(tondeuse.getPositionX(), 10);
		assertEquals(tondeuse.getPositionY(), 10);
		assertEquals(tondeuse.getOrientation(), Orientation.N);
	}

	// execution of multiple tasks  sequentially 
	@Test
	public void testCommandeMultipleTondeuses() throws InterruptedException {
		
		// One thread to make a sequentiel execution 
		ExecutorService executor = Executors.newSingleThreadExecutor(); 
		List<Callable<Tondeuse>> taches = new ArrayList<Callable<Tondeuse>>();
		Surface surface = new Surface(5, 5);

		Tondeuse tondeuse = new Tondeuse(1, 2, Orientation.N, surface, "GAGAGAGAA");
		Tondeuse tondeuse2 = new Tondeuse(3, 3, Orientation.E, surface, "AADAADADDA");
		Tondeuse tondeuse3 = new Tondeuse(3, 3, Orientation.E, surface, "GA");

		taches.add(tondeuse);
		taches.add(tondeuse2);
		taches.add(tondeuse3);

		TasksService.execute(executor, taches, "testCommandeMultipleTondeuses");

		assertEquals(tondeuse.getOrientation(), Orientation.N);
		assertEquals(tondeuse.getPositionX(), 1);
		assertEquals(tondeuse.getPositionY(), 3);
		
		assertEquals(tondeuse2.getOrientation(), Orientation.E);
		assertEquals(tondeuse2.getPositionX(), 5);
		assertEquals(tondeuse2.getPositionY(), 1);

		assertEquals(tondeuse3.getOrientation(), Orientation.N);
		assertEquals(tondeuse3.getPositionX(), 3);
		assertEquals(tondeuse3.getPositionY(), 4);

	}

	// test of one task
	@Test
	public void testCommandeOneTondeuse() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		List<Callable<Tondeuse>> taches = new ArrayList<Callable<Tondeuse>>();
		Surface surface = new Surface(5, 5);
		Tondeuse tondeuse = new Tondeuse(3, 3, Orientation.E, surface, "AADAADADDA");
		
		taches.add(tondeuse);
		TasksService.execute(executor, taches, "testCommandeOneTondeuse");
		
		assertEquals(tondeuse.getOrientation(), Orientation.E);
		assertEquals(tondeuse.getPositionX(), 5);
		assertEquals(tondeuse.getPositionY(), 1);
	}

}
