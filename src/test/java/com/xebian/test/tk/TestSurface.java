package com.xebian.test.tk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.xebian.impl.Surface;

public class TestSurface {

	@Test
	public void testInitOKSurface() {
		Surface surface = new Surface(10, 10);
		assertEquals(surface.checkCase(5, 5), true);
	}

	@Test
	public void testInitKOSurface() {

		try {
			new Surface(-1, 10);

			fail("This should have thrown an exception");

		} catch (NegativeArraySizeException e) {
			Assert.assertTrue(e.getMessage().equals("params should be positive"));
		}
	}

	@Test
	public void testOutOfBoundSurface() {
		Surface surface = new Surface(20, 20);
		assertEquals(surface.setCase(50, 50, 3), false);
	}

	@Test
	public void testInBoundSurface() {
		Surface surface = new Surface(20, 20);
		assertEquals(surface.setCase(15, 18, 3), true);
	}

	@Test
	public void testCaseNotExists() {
		Surface surface = new Surface(20, 20);
		assertEquals(surface.checkCase(50, 50), false);
	}

	@Test
	public void testCaseExists() {
		Surface surface = new Surface(60, 60);
		assertEquals(surface.checkCase(50, 50), true);
	}
}
