package textBuddyPlus;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TextBuddyTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// set up TextBuddy
//		TextBuddy.main(null);
		TextBuddy.initializeTextBuddy(null);
		
	}
	
	@Test
	public void testGetLineCount() {
		TextBuddy.executeCommand("clear");
		assertEquals(0, TextBuddy.getLineCount());
		
		TextBuddy.executeCommand("add Task1");
		assertEquals(1, TextBuddy.getLineCount());
		
		TextBuddy.executeCommand("add Task2");
		assertEquals(2, TextBuddy.getLineCount());
		
		TextBuddy.executeCommand("clear");
		assertEquals(0, TextBuddy.getLineCount());
	}
	
	@Test
	public void testSort() {
		String output = null;
		TextBuddy.executeCommand("clear");
		
		TextBuddy.executeCommand("add c");
		TextBuddy.executeCommand("add b");
		TextBuddy.executeCommand("add a");
		TextBuddy.executeCommand("sort");
		output = TextBuddy.executeCommand("display");
		assertEquals("1. a\n2. b\n3. c", output);
		
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add Water Z");
		TextBuddy.executeCommand("add Ahead G");
		TextBuddy.executeCommand("add Girl D");
		TextBuddy.executeCommand("sort");
		output = TextBuddy.executeCommand("display");
		assertEquals("1. Ahead G\n2. Girl D\n3. Water Z", output);
		

	}
}
