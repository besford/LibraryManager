package utilities.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import utilities.Trace;

public class TraceTest {
	
	Trace testTrace = null;
	
	@Before
	public void setupTrace() throws FileNotFoundException {
		testTrace = Trace.instanceOf();
	}
	
	@Test
	public void testInstanceOf() {
		testTrace = Trace.instanceOf();
		assertNotEquals(null, testTrace);
	}
	
	@Test
	public void testGetLogger() {
		testTrace = Trace.instanceOf();
		Logger testLogger = testTrace.getLogger(this);
		testLogger.info("Running Test!");
		assertNotEquals(null, testLogger);
	}
}
