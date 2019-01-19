package server.logic.model.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import server.logic.model.Loan;



public class LoanTest {

	@Test
	public void testLoan() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		assertEquals(1, testLoan.getUserId());
		assertEquals("0000000000000", testLoan.getIsbn());
		assertEquals("1", testLoan.getCopyNumber());
		assertEquals(testDate, testLoan.getDate());
		assertEquals("test", testLoan.getRenewState());
	}

	@Test
	public void testGetUserid() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		assertEquals(1, testLoan.getUserId());
	}

	@Test
	public void testSetUserid() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		testLoan.setUserId(2);
		assertEquals(2, testLoan.getUserId());
	}

	@Test
	public void testGetDate() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		assertEquals(testDate, testLoan.getDate());
	}

	@Test
	public void testSetDate() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		Date testDate2 = new Date();
		testLoan.setDate(testDate2);
		assertEquals(testDate2, testLoan.getDate());
	}

	@Test
	public void testGetIsbn() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		assertEquals("0000000000000", testLoan.getIsbn());
	}

	@Test
	public void testSetIsbn() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		testLoan.setIsbn("1000000000000");
		assertEquals("1000000000000", testLoan.getIsbn());
	}

	@Test
	public void testGetCopynumber() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		assertEquals("1", testLoan.getCopyNumber());
	}

	@Test
	public void testSetCopynumber() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		testLoan.setCopyNumber("100");
		assertEquals("100", testLoan.getCopyNumber());
	}

	@Test
	public void testGetRenewstate() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		assertEquals("test", testLoan.getRenewState());
	}

	@Test
	public void testSetRenewstate() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		testLoan.setRenewState("test again!");
		assertEquals("test again!", testLoan.getRenewState());
	}

	@Test
	public void testToString() {
		Date testDate = new Date();
		Loan testLoan = new Loan(1, "0000000000000", "1", testDate, "test");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String testString = "["+testLoan.getUserId()+","+testLoan.getIsbn()+","+testLoan.getCopyNumber()+","+format.format(testDate)+","+testLoan.getRenewState()+"]";
		assertEquals(testString, testLoan.toString());
	}
}
