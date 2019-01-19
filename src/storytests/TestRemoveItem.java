package storytests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import client.LibClient;
import server.network.LibServer;
import utilities.Config;

public class TestRemoveItem {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupTestRemoveItem() {
		testServer = new LibServer(Config.DEFAULT_PORT+3);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+3);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@Test
	public void testRemoveItem() {
		// Existing item
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "Delete Item");
		testServer.handle(testClient.getID(), "9781442668584,1");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		// Previously Existing item
		testServer.handle(testClient.getID(), "Delete Item");
		testServer.handle(testClient.getID(), "9781317594277,1");
		testStr = testOut.toString().split("\r\n");
		assertEquals("The Item Does Not Exist!", testStr[testStr.length-1].toString());
		
		// Non Existant Title
		testServer.handle(testClient.getID(), "Delete Item");
		testServer.handle(testClient.getID(), "1000000000000,1");
		testStr = testOut.toString().split("\r\n");
		assertEquals("The Item Does Not Exist!", testStr[testStr.length-1].toString());
		
		// Invalid Title
		testServer.handle(testClient.getID(), "Delete Item");
		testServer.handle(testClient.getID(), "100000Stuff and Things");
		testStr = testOut.toString().split("\r\n");
		assertEquals("Your input should in this format:'ISBN,copynumber',ISBN should be a 13-digit number", testStr[testStr.length-1].toString());
	}

}
