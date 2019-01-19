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

public class TestAddUser {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupAddUser() {
		testServer = new LibServer(Config.DEFAULT_PORT);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@After
	public void destroyAddUser() {
		System.setOut(old);
	}
	
	@Test
	public void testNewUser() {
		// New user
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "create user");
		testServer.handle(testClient.getID(), "testerbob@carleton.ca, test");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		//testServer.handle(testClient.getID(), "Exit");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		//Existing user
		testServer.handle(testClient.getID(), "create user");
		testServer.handle(testClient.getID(), "testerbob@carleton.ca, test");
		testStr = testOut.toString().split("\r\n");
		assertEquals("The User Already Exists!", testStr[testStr.length-1].toString());
		
		//Invalid user
		testServer.handle(testClient.getID(), "create user");
		testServer.handle(testClient.getID(), "testerbob@carletontest");
		testStr = testOut.toString().split("\r\n");
		assertEquals("Your input should in this format:'username,password'", testStr[testStr.length-1].toString());
	}
}
