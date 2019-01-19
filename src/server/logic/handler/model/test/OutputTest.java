package server.logic.handler.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.logic.handler.model.Output;

public class OutputTest {

	@Test
	public void testOutput() {
		Output testOutput = new Output("test", 0);
		assertEquals("test", testOutput.getOutput());
		assertEquals(0, testOutput.getState());
	}

	@Test
	public void testGetOutput() {
		Output testOutput = new Output("test", 0);
		assertEquals("test", testOutput.getOutput());
	}

	@Test
	public void testSetOutput() {
		Output testOutput = new Output("test", 0);
		testOutput.setOutput("test out");
		assertEquals("test out", testOutput.getOutput());
	}

	@Test
	public void testGetState() {
		Output testOutput = new Output("test", 0);
		assertEquals(0, testOutput.getState());
	}

	@Test
	public void testSetState() {
		Output testOutput = new Output("test", 0);
		testOutput.setState(-1);
		assertEquals(-1, testOutput.getState());
	}
}
