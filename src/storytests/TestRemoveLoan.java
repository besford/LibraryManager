package storytests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import client.LibClient;
import server.network.LibServer;
import utilities.Config;

public class TestRemoveLoan {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupTestRemoveLoan() {
		testServer = new LibServer(Config.DEFAULT_PORT+8);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+8);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}

	@Test
	public void test() {
		// remove loan
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "Loan remove");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781442668584,1");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		//remove previously remove loan
		testServer.handle(testClient.getID(), "Loan remove");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781442668584,1");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The Loan Does Not Exist!", testStr[testStr.length-1].toString());
		
		//Remove non-existant loan
		testServer.handle(testClient.getID(), "Loan remove");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781441111111,1");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The Loan Does Not Exist!", testStr[testStr.length-1].toString());
		
		//Remove invalid loan
		testServer.handle(testClient.getID(), "Loan remove");
		testServer.handle(testClient.getID(), "Zhibo@carleton.cafasdf1");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Your input should in this format:'useremail,ISBN,copynumber'", testStr[testStr.length-1].toString());
		testServer.handle(testClient.getID(), "Loan remove");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,91,1");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Your input should in this format:'useremail,ISBN,copynumber'", testStr[testStr.length-1].toString());
		testServer.handle(testClient.getID(), "Loan remove");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781442668584,n/a");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Your input should in this format:'useremail,ISBN,copynumber'", testStr[testStr.length-1].toString());
	}
}