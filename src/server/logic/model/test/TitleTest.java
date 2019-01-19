package server.logic.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.logic.model.Title;

public class TitleTest {

	@Test
	public void testTitle() {
		Title testTitle = new Title("0000000000000", "Test Title");
		assertEquals("0000000000000", testTitle.getIsbn());
		assertEquals("Test Title", testTitle.getBookTitle());
	}

	@Test
	public void testGetIsbn() {
		Title testTitle = new Title("0000000000000", "Test Title");
		assertEquals("0000000000000", testTitle.getIsbn());
	}

	@Test
	public void testSetIsbn() {
		Title testTitle = new Title("0000000000000", "Test Title");
		testTitle.setIsbn("1000000000000");
		assertEquals("1000000000000", testTitle.getIsbn());
	}

	@Test
	public void testGetBookTitle() {
		Title testTitle = new Title("0000000000000", "Test Title");
		assertEquals("Test Title", testTitle.getBookTitle());
	}

	@Test
	public void testSetBookTitle() {
		Title testTitle = new Title("0000000000000", "Test Another Title");
		testTitle.setBookTitle("Test Another Title");
		assertEquals("Test Another Title", testTitle.getBookTitle());
	}

	@Test
	public void testToString() {
		Title testTitle = new Title("0000000000000", "Test Another Title");
		String testString = "["+testTitle.getIsbn()+","+testTitle.getBookTitle()+"]";
		assertEquals(testString, testTitle.toString());
	}

}
