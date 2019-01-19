package server.logic.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.logic.model.Item;

public class ItemTest {

	@Test
	public void testItem() {
		Item testItem = new Item(1, "0000000000000", "1");
		assertEquals(1, testItem.getItemId());
		assertEquals("0000000000000", testItem.getIsbn());
		assertEquals("1", testItem.getCopyNumber());
	}

	@Test
	public void testGetItemid() {
		Item testItem = new Item(1, "0000000000000", "1");
		assertEquals(1, testItem.getItemId());
	}

	@Test
	public void testSetItemid() {
		Item testItem = new Item(1, "0000000000000", "1");
		testItem.setItemId(2);
		assertEquals(2, testItem.getItemId());
	}

	@Test
	public void testGetISBN() {
		Item testItem = new Item(1, "0000000000000", "1");
		assertEquals("0000000000000", testItem.getIsbn());
	}

	@Test
	public void testSetISBN() {
		Item testItem = new Item(1, "0000000000000", "1");
		testItem.setIsbn("1000000000000");
		assertEquals("1000000000000", testItem.getIsbn());
	}

	@Test
	public void testGetCopynumber() {
		Item testItem = new Item(1, "0000000000000", "1");
		assertEquals("1", testItem.getCopyNumber());
	}

	@Test
	public void testSetCopynumber() {
		Item testItem = new Item(1, "0000000000000", "1");
		testItem.setCopyNumber("100");
		assertEquals("100", testItem.getCopyNumber());
	}

	@Test
	public void testToString() {
		Item testItem = new Item(1, "0000000000000", "1");
		String testString = "["+testItem.getItemId()+","+testItem.getIsbn()+","+testItem.getCopyNumber()+"]";
		assertEquals(testString, testItem.toString());
	}
}
