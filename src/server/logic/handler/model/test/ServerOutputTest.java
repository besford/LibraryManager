package server.logic.handler.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.logic.handler.model.ServerOutput;

public class ServerOutputTest {

	@Test
	public void testServerOutput() {
		ServerOutput testServer = new ServerOutput("test", 0);
		assertEquals("test", testServer.getOutput());
		assertEquals(0, testServer.getState());
	}

	@Test
	public void testGetOutput() {
		ServerOutput testServer = new ServerOutput("test", 0);
		assertEquals("test", testServer.getOutput());
	}

	@Test
	public void testSetOutput() {
		ServerOutput testServer = new ServerOutput("test", 0);
		testServer.setOutput("test out");
		assertEquals("test out", testServer.getOutput());
	}

	@Test
	public void testGetState() {
		ServerOutput testServer = new ServerOutput("test", 0);
		assertEquals(0, testServer.getState());
	}

	@Test
	public void testSetState() {
		ServerOutput testServer = new ServerOutput("test", 0);
		testServer.setState(-1);
		assertEquals(-1, testServer.getState());
	}
}
