package server.logic.tables.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.logic.tables.FeeTable;
import server.logic.tables.LoanTable;

public class FeeTableTest {

	FeeTable testTable = null;
	LoanTable testLoanTable = null;
	
	@Before
	public void setupFeeTable() {
		testTable = FeeTable.instanceOf();
		testLoanTable = LoanTable.instanceOf();
		testTable.initialization();
		testLoanTable.initialization();
	}
	
	@Test
	public void testInstanceOf() {
		testTable = FeeTable.instanceOf();
		assertNotEquals(null, testTable);
	}

	@Test
	public void testLookUp() {
		assertEquals(false, testTable.lookUp(0));
		assertEquals(true, testTable.lookUp(-1));
	}

	@Test
	public void testLookupFee() {
		assertEquals(5, testTable.lookupFee(0));
	}

	@Test
	public void testApplyFee() {
		testTable.applyFee(0, 1234567);
		assertEquals(20, testTable.lookupFee(0));
		testTable.applyFee(0, -1234567);
		assertEquals(20, testTable.lookupFee(0));
		testTable.applyFee(100, 1234567);
		assertEquals(15, testTable.lookupFee(100));
	}

	@Test
	public void testPayFine() {
		testTable.applyFee(100, 0);
		assertEquals(0, testTable.lookupFee(100));
		testTable.applyFee(100, 1234567);
		assertEquals("success", testTable.payFine(100));
		assertNotEquals("success", testTable.payFine(0));
	}
	
	@Test
	public void testGetFeeTable() {
		assertNotEquals(null, testTable.getFeeTable());
	}
}
