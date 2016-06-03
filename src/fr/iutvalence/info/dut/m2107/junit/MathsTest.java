package fr.iutvalence.info.dut.m2107.junit;

import fr.iutvalence.info.dut.m2107.toolbox.Maths;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MathsTest extends TestCase {
	
	public MathsTest(String name) {
		super(name);
	}
	
	public void testTruncate() {
		assertEquals(1f, Maths.truncate(1.12345f, 0));
		assertEquals(1.1f, Maths.truncate(1.12345f, 1));
		assertEquals(1.12f, Maths.truncate(1.12345f, 2));
		assertEquals(1.123f, Maths.truncate(1.12345f, 3));
		assertEquals(1.1234f, Maths.truncate(1.12345f, 4));
	}
	
	public void testPow() {
		assertEquals(4f, Maths.pow(2f, 2));
		assertEquals(256f, Maths.pow(2f, 8));
		assertEquals(16f, Maths.pow(4f, 2));
		assertEquals(216f, Maths.pow(6f, 3));
		assertEquals(1f, Maths.pow(6f, 0));
	}
	
	public void testFastAbs() {
		assertEquals(1f, Maths.fastAbs(1f));
		assertEquals(1f, Maths.fastAbs(-1f));
		assertEquals(145f, Maths.fastAbs(145f));
		assertEquals(.42f, Maths.fastAbs(-.42f));
		assertEquals(1516451651f, Maths.fastAbs(1516451651f));
	}
	
	public void testFastFloor() {
		assertEquals(1, Maths.fastFloor(1.42f));
		assertEquals(512, Maths.fastFloor(512.9999f));
		assertEquals(-6, Maths.fastFloor(-5.1f));
		assertEquals(0, Maths.fastFloor(.42f));
		assertEquals(-1912, Maths.fastFloor(-1911.9999f));
	}
	
	public void testRound() {
		assertEquals(1.11f, Maths.round(1.106f, 2));
		assertEquals(1.12f, Maths.round(1.116f, 2));
		assertEquals(-101.666f, Maths.round(-101.6664f, 3));
		assertEquals(0f, Maths.round(0.000001f, 4));
		assertEquals(1.5f, Maths.round(1.5f, 1));
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTest(new MathsTest("testTruncate"));
		suite.addTest(new MathsTest("testPow"));
		suite.addTest(new MathsTest("testFastAbs"));
		suite.addTest(new MathsTest("testFastFloor"));
		suite.addTest(new MathsTest("testRound"));
		
		return suite;
	}
	
}
