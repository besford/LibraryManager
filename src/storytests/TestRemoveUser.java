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

public class TestRemoveUser {

	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupRemoveUser() {
		testServer = new LibServer(Config.DEFAULT_PORT+5);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+5);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@After
	public void destroyAddUser() {
		System.setOut(old);
	}
	
	@Test
	public void testNewUser() {
		// Delete user
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "delete user");
		testServer.handle(testClient.getID(), "Michelle@carleton.ca");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		// Delete previously existing user
		testServer.handle(testClient.getID(), "delete user");
		testServer.handle(testClient.getID(), "Michelle@carleton.ca");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The User Does Not Exist!", testStr[testStr.length-1].toString());
		
		// Delete non-existent user
		testServer.handle(testClient.getID(), "delete user");
		testServer.handle(testClient.getID(), "test@carleton.ca");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The User Does Not Exist!", testStr[testStr.length-1].toString());
		
		// Delete user with outstanding loan
		testServer.handle(testClient.getID(), "delete user");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Active Loan Exists!", testStr[testStr.length-1].toString());
		
		// Delete invalid user
		testServer.handle(testClient.getID(), "delete user");
		testServer.handle(testClient.getID(), "123456 ");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Your input should in this format:'useremail'", testStr[testStr.length-1].toString());
	}
}
