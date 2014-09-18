package textBuddyPlus;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TextBuddyTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// set up TextBuddy
//		TextBuddy.main(null);
		TextBuddy.main(null);
		
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
}
