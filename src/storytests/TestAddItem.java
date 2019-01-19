package storytests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import client.LibClient;
import server.network.LibServer;
import utilities.Config;

public class TestAddItem {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupTestAddItem() {
		testServer = new LibServer(Config.DEFAULT_PORT+1);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+1);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@Test
	public void testAddItem() {
		// Add item
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "Create item");
		testServer.handle(testClient.getID(), "9781442668584");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		// Add non-existent item
		testServer.handle(testClient.getID(), "Create item");
		testServer.handle(testClient.getID(), "1000000000111");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The Title Does Not Exists!", testStr[testStr.length-1].toString());
		
		// Add invalid item
		testServer.handle(testClient.getID(), "Create item");
		testServer.handle(testClient.getID(), "100 test");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Your input should in this format:'ISBN',ISBN should be a 13-digit number", testStr[testStr.length-1].toString());
	}

}
