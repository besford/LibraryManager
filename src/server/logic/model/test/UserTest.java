package server.logic.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.logic.model.User;

public class UserTest {

	@Test
	public void testNewUser() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		
		assertEquals("testerbob@carleton.ca", testUser.getUsername());
		assertEquals(0, testUser.getUserId());
		assertEquals("test", testUser.getPassword());
	}

	@Test
	public void testGetUserid() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		assertEquals(0, testUser.getUserId());
	}

	@Test
	public void testSetUserid() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		testUser.setUserId(1);
		assertEquals(1, testUser.getUserId());
	}

	@Test
	public void testGetUsername() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		assertEquals("testerbob@carleton.ca", testUser.getUsername());
	}

	@Test
	public void testSetUsername() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		testUser.setUsername("tester@carleton.ca");
		assertEquals("tester@carleton.ca", testUser.getUsername());
	}

	@Test
	public void testGetPassword() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		assertEquals("test", testUser.getPassword());
	}

	@Test
	public void testSetPassword() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		testUser.setPassword("test!");
		assertEquals("test!", testUser.getPassword());
	}
	
	@Test
	public void testToString() {
		User testUser = new User(0, "testerbob@carleton.ca", "test");
		String testString = "["+testUser.getUserId()+","+testUser.getUsername()+","+testUser.getPassword()+"]";
		assertEquals(testString, testUser.toString());
	}
}
