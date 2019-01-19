package storytests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import client.LibClient;
import server.network.LibServer;
import utilities.Config;

public class TestCollectFines {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupTestCollectFines() {
		testServer = new LibServer(Config.DEFAULT_PORT+6);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+6);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@Test
	public void testTestCollectFines() {
		// Collect fines for existing user with no loans
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "Fines Collect");
		testServer.handle(testClient.getID(), "Yu@carleton.ca");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		// collect fines from outstanding loans
		testServer.handle(testClient.getID(), "Fines Collect");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Borrowing Items Exist!", testStr[testStr.length-1].toString());
		
		// collect fines from invalid user
		testServer.handle(testClient.getID(), "Fines Collect");
		testServer.handle(testClient.getID(), "notauser@carleton.ca");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The User Does Not Exist!", testStr[testStr.length-1].toString());
	}

}
