package server.logic.tables.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.logic.model.Title;
import server.logic.tables.TitleTable;

public class TitleTableTest {

	TitleTable testTable = null;
	
	@Before
	public void setupUserTable() {
		testTable = TitleTable.instanceOf();
		testTable.initialization();
	}
	
	@Test
	public void testGetInstance() {
		testTable = TitleTable.instanceOf();
		assertNotEquals(null, testTable);
	}

	@Test
	public void testLookup() {
		Title testTitle = new Title("0000000000000", "Test Title");
		testTable.createtitle(testTitle.getIsbn(), testTitle.getBookTitle());
		assertEquals(true, testTable.lookup(testTitle.getIsbn()));
	}

	@Test
	public void testCreateTitle() {
		Title testTitle = new Title("0000000000000", "Test Title");
		assertEquals(true, testTable.createtitle(testTitle.getIsbn(), testTitle.getBookTitle()));
		assertEquals(false, testTable.createtitle(testTitle.getIsbn(), testTitle.getBookTitle()));
	}
	
	@Test
	public void testDeleteTitle() {
		Title testTitle = new Title("0000000000000", "Test Title");
		testTable.createtitle(testTitle.getIsbn(), testTitle.getBookTitle());
		testTable.deleteTitle(testTitle.getIsbn());
		assertEquals(true, testTable.createtitle(testTitle.getIsbn(), testTitle.getBookTitle()));
	}

	@Test
	public void testGetTitleTable() {
		assertNotEquals(null, testTable.getTitleTable());
	}
}
