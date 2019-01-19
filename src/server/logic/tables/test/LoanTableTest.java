package server.logic.tables.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import server.logic.model.Loan;
import server.logic.tables.LoanTable;

public class LoanTableTest {

	private LoanTable testTable = null;
	
	@Before
	public void setupLoanTable() {
		testTable = LoanTable.instanceOf();
		testTable.initialization();
	}
	
	@Test
	public void testInstanceOf() {
		assertNotEquals(null, LoanTable.instanceOf());
	}

	@Test
	public void testCreateloan() {
		assertEquals("Outstanding Fee Exists", testTable.createloan(0, "9781611687910", "1", new Date()));
		assertEquals("ISBN Invalid", testTable.createloan(0, "1000", "1", new Date()));
		assertEquals("User Invalid", testTable.createloan(-1, "1000", "1", new Date()));
		assertEquals("success", testTable.createloan(3, "9781611687910", "1", new Date()));
		assertEquals("Copynumber Invalid", testTable.createloan(3, "9781442668584", "-1", new Date()));
		assertEquals("The Item is Not Available", testTable.createloan(4, "9781442668584", "1", new Date()));
	}

	@Test
	public void testLookup() {
		testTable.createloan(3, "9781442668584", "1", new Date());
		assertEquals(false, testTable.lookup(0, "9781442668584", "1"));
		assertEquals(true, testTable.lookup(0, "9781442668584", "2"));
	}

	@Test
	public void testCheckLimit() {
		assertEquals(true, testTable.checkLimit(0));
		Loan loan = new Loan(0,"9781442668584", "1", new Date(), "0");
    	testTable.getLoanTable().add(loan);
    	loan = new Loan(0,"9781442668584", "2", new Date(), "0");
    	testTable.getLoanTable().add(loan);
    	loan = new Loan(0,"9781442668584", "3", new Date(), "0");
    	testTable.getLoanTable().add(loan);
		assertEquals(false, testTable.checkLimit(0));
	}

	@Test
	public void testRenewal() {
		Loan loan = new Loan(3,"9781442668584", "1", new Date(), "0");
		testTable.getLoanTable().add(loan);
		assertEquals("success" , testTable.renewal(3, "9781442668584", "1", new Date()));
		assertEquals("Outstanding Fee Exists", testTable.renewal(0, "9781442668584", "1", new Date()));
		assertEquals("Renewed Item More Than Once for the Same Loan" , testTable.renewal(3, "9781442668584", "1", new Date()));
		assertEquals("The loan does not exist", testTable.renewal(3, "1000", "1", new Date()));
		loan = new Loan(3, "9781442668584", "4", new Date(), "0");
		testTable.getLoanTable().add(loan);
		loan = new Loan(3, "9781442668584", "5", new Date(), "0");
		testTable.getLoanTable().add(loan);
		loan = new Loan(3, "9781442668584", "6", new Date(), "0");
		testTable.getLoanTable().add(loan);
		assertEquals("The Maximun Number of Items is Reached", testTable.renewal(3, "9781442668584", "1", new Date()));
	}

	@Test
	public void testReturnItem() {
		Loan loan = new Loan(3,"9781442668584", "1", new Date(), "0");
		testTable.getLoanTable().add(loan);
		assertEquals("success" , testTable.returnItem(3, "9781442668584", "1", new Date()));
		assertNotEquals("success" , testTable.renewal(4, "9781442668584", "1", new Date()));
	}

	@Test
	public void testLookLimit() {
		assertEquals(true , testTable.lookLimit(3));
		Loan loan = new Loan(3,"9781442668584", "1", new Date(), "0");
		testTable.getLoanTable().add(loan);
		loan = new Loan(3, "9781442668584", "4", new Date(), "0");
		testTable.getLoanTable().add(loan);
		loan = new Loan(3, "9781442668584", "5", new Date(), "0");
		testTable.getLoanTable().add(loan);
		loan = new Loan(3, "9781442668584", "6", new Date(), "0");
		testTable.getLoanTable().add(loan);
		assertEquals(false , testTable.lookLimit(3));
	}

	@Test
	public void testCheckLoan() {
		assertEquals(true, testTable.checkLoan(3));
		assertEquals(true, testTable.checkLoan("9781442668584", "n/a"));
		assertEquals(true, testTable.checkLoan("1000000000000"));
		Loan loan = new Loan(3,"9781442668584", "1", new Date(), "0");
		testTable.getLoanTable().add(loan);
		assertEquals(false, testTable.checkLoan(3));
		assertEquals(false, testTable.checkLoan("9781442668584", "1"));
		assertEquals(false, testTable.checkLoan("9781442668584"));
	}

	@Test
	public void testGetLoanTable() {
		assertNotEquals(null, testTable.getLoanTable());
	}

}
