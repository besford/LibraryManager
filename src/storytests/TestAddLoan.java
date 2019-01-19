package storytests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import client.LibClient;
import server.network.LibServer;
import utilities.Config;

public class TestAddLoan {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupTestAddLoan() {
		testServer = new LibServer(Config.DEFAULT_PORT+7);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+7);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@Test
	public void test() {
		// add loan to user
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "Loan add");
		testServer.handle(testClient.getID(), "Yu@carleton.ca,9781611687910,1");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		// add already loaned item to user
		testServer.handle(testClient.getID(), "Loan add");
		testServer.handle(testClient.getID(), "Yu@carleton.ca,9781611687910,1");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The Item is Not Available!", testStr[testStr.length-1].toString());
		
		// add loan with invalid copy number
		testServer.handle(testClient.getID(), "Loan add");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781611687910,2");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Copynumber Invalid!", testStr[testStr.length-1].toString());
		
		// add loan with unavailable copy
		testServer.handle(testClient.getID(), "Loan add");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781611687910,1");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The Item is Not Available!", testStr[testStr.length-1].toString());
		
		// add invalid loan to user
		testServer.handle(testClient.getID(), "Loan add");
		testServer.handle(testClient.getID(), "Yu@carleton.ca oh no!");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Your input should in this format:'useremail,ISBN,copynumber'", testStr[testStr.length-1].toString());
	}
}
