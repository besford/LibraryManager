package server.logic.tables.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.logic.model.User;
import server.logic.tables.UserTable;

public class UserTableTest {

	UserTable testTable = null;
	
	@Before
	public void setupUserTable() {
		testTable = UserTable.instanceOf();
		testTable.getUserTable().clear();
	}
	
	@Test
	public void testInstanceOf() {
		testTable = UserTable.instanceOf();
		assertNotEquals(null, testTable);
	}

	@Test
	public void testLookUpInt() {
		User testUser = new User(0 ,"testerbob@carleton.ca", "test");
		testTable.createUser(testUser.getUsername(), testUser.getPassword());
		assertEquals(true, testTable.lookUp(testUser.getUserId()));
	}

	@Test
	public void testLookUpString() {
		User testUser = new User(0 ,"testerbob@carleton.ca", "test");
		testTable.createUser(testUser.getUsername(), testUser.getPassword());
		assertEquals(testUser.getUserId(), testTable.lookUp(testUser.getUsername()));
	}

	@Test
	public void testCheckUser() {
		User testUser = new User(0 ,"testerbob@carleton.ca", "test");
		testTable.createUser(testUser.getUsername(), testUser.getPassword());
		assertEquals(testUser.getUserId(), testTable.checkUser(testUser.getUsername(), testUser.getPassword()));
	}

	@Test
	public void testCreateUser() {
		User testUser = new User(0 ,"testerbob@carleton.ca", "test");
		assertEquals(true, testTable.createUser(testUser.getUsername(), testUser.getPassword()));
		assertEquals(false, testTable.createUser(testUser.getUsername(), testUser.getPassword()));
		assertEquals(testUser.getUserId(), testTable.checkUser(testUser.getUsername(), testUser.getPassword()));
	}
	
	@Test
	public void testDeleteUser() {
		User testUser = new User(0 ,"testerbob@carleton.ca", "test");
		testTable.createUser(testUser.getUsername(), testUser.getPassword());
		testTable.delete(testUser.getUserId());
		assertEquals(true, testTable.createUser(testUser.getUsername(), testUser.getPassword()));
	}
	
	@Test
	public void testGetUserTable() {
		assertNotEquals(null, testTable.getUserTable());
	}
}
