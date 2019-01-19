package server.logic.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.logic.model.Fee;

public class FeeTest {

	@Test
	public void testFee() {
		Fee testFee = new Fee(0, 0);
		
		assertEquals(0, testFee.getUserId());
		assertEquals(0, testFee.getFee());
	}

	@Test
	public void testGetUserid() {
		Fee testFee = new Fee(0, 0);
		assertEquals(0, testFee.getUserId());
	}

	@Test
	public void testSetUserid() {
		Fee testFee = new Fee(0, 0);
		testFee.setUserId(1);
		assertEquals(1, testFee.getUserId());
	}

	@Test
	public void testGetFee() {
		Fee testFee = new Fee(0, 0);
		assertEquals(0, testFee.getFee());
	}

	@Test
	public void testSetFee() {
		Fee testFee = new Fee(0, 0);
		testFee.setFee(1);
		assertEquals(1, testFee.getFee());
	}

	@Test
	public void testToString() {
		Fee testFee = new Fee(0, 0);
		String testString = "["+testFee.getUserId()+","+testFee.getFee()+"]";
		assertEquals(testString, testFee.toString());
	}
}
