package storytests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import client.LibClient;
import server.network.LibServer;
import utilities.Config;

public class TestRenewLoan {
	
	private LibClient testClient = null;
	private LibServer testServer = null;
	private ByteArrayOutputStream testOut = null;
	private PrintStream old = System.out;
	
	@Before
	public void setupTestAddLoan() {
		testServer = new LibServer(Config.DEFAULT_PORT+9);
		testClient = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT+9);
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}
	
	@Test
	public void test() {
		// renew loan to user
		testServer.handle(testClient.getID(), "");
		testServer.handle(testClient.getID(), "clerk");
		testServer.handle(testClient.getID(), "admin");
		testServer.handle(testClient.getID(), "Loan Renew");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781442668584,1");
		String[] testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Success!", testStr[testStr.length-1].toString());
		
		// already remewed loan to user
		testServer.handle(testClient.getID(), "Loan Renew");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781442668584,1");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("Renewed Item More Than Once for the Same Loan!", testStr[testStr.length-1].toString());
		
		// loan not existing
		testServer.handle(testClient.getID(), "Loan Renew");
		testServer.handle(testClient.getID(), "Zhibo@carleton.ca,9781611687910,2");
		testStr = testOut.toString().split("\r\n");
		testServer.handle(testClient.getID(), "");
		assertEquals("The loan does not exist!", testStr[testStr.length-1].toString());
	}

}