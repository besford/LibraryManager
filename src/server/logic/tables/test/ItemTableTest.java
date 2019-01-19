package server.logic.tables.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.logic.model.Item;
import server.logic.tables.ItemTable;

public class ItemTableTest {

	ItemTable testTable = null;
	
	@Before
	public void setupItemTable() {
		testTable = ItemTable.instanceOf();
		testTable.initialization();
	}
	
	@Test
	public void testGetInstance() {
		testTable = ItemTable.instanceOf();
		assertNotEquals(null, testTable);
	}

	@Test
	public void testLookup() {
		Item testItem = new Item(0, "9781442668584", "1");
		assertEquals(true, testTable.lookup(testItem.getIsbn(), testItem.getCopyNumber()));
	}
	
	@Test
	public void testCreateitem() {
		Item testItem = new Item(0, "0000000000000", "1");
		assertEquals(true, testTable.createItem("9781442668584"));
		assertEquals(false, testTable.createItem("0000000000000"));
	}

	@Test
	public void testDeleteAll() {
		testTable.createItem("9781442668584");
		testTable.createItem("9781442668584");
		testTable.deleteAll("9781442668584");
		assertEquals(false, testTable.lookup("9781442668584", "1"));
		assertEquals(false, testTable.lookup("9781442668584", "2"));
	}
	
	@Test
	public void testDelete() {
		Item testItem = new Item(0, "0000000000000", "1");
		testTable.createItem("9781442668584");
		testTable.createItem("9781442668584");
		testTable.delete("9781442668584", "1");
		assertEquals(false, testTable.lookup("9781442668584", "1"));
		assertEquals(true, testTable.lookup("9781442668584", "n/a"));
	}

	@Test
	public void testGetItemTable() {
		assertNotEquals(null, testTable.getItemTable());
	}

}
