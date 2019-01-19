package storytests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.LibClient;
import server.network.LibServer;
import utilities.Config;

public class TestAddTitle {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupTestAddTitle() {
		testServer = new LibServer(Config.DEFAULT_PORT+2);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+2);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@After
	public void destroyAddTitle() {
		System.setOut(old);
	}
	
	@Test
	public void testAddTitle() {
		// New Title
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "Create Title");
		testServer.handle(testClient.getID(), "1000000000000, Stuff and Things");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		// Existing Title
		testServer.handle(testClient.getID(), "Create Title");
		testServer.handle(testClient.getID(), "1000000000000, Stuff and Things");
		testStr = testOut.toString().split("\r\n");
		assertEquals("The Title Already Exists!", testStr[testStr.length-1].toString());
		
		// Invalid Title
		testServer.handle(testClient.getID(), "Create Title");
		testServer.handle(testClient.getID(), "100000Stuff and Things");
		testStr = testOut.toString().split("\r\n");
		assertEquals("Your input should in this format:'ISBN,title',ISBN should be a 13-digit number", testStr[testStr.length-1].toString());
	}
}
