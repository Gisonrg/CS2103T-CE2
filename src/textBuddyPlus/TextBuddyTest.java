package textBuddyPlus;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TextBuddyTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// set up TextBuddy
		String [] input = new String[1];
		input[0] = "input.txt";
		
		TextBuddy.initializeTextBuddy(input);
	}
	
	@Test
	public void testSort() {
		String output = null;
		
		// Test null case
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add ");
		TextBuddy.executeCommand("add ");
		TextBuddy.executeCommand("add ");
		TextBuddy.executeCommand("sort");
		output = TextBuddy.executeCommand("display");
		assertEquals("There is no item in input.txt now.", output);
		
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
		
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add C");
		TextBuddy.executeCommand("add b");
		TextBuddy.executeCommand("add A");
		TextBuddy.executeCommand("sort");
		output = TextBuddy.executeCommand("display");
		assertEquals("1. A\n2. b\n3. C", output);
	}
	
	@Test
	public void testSearchTask() {
		String output = null;
		// Test null case
		TextBuddy.executeCommand("clear");
		output = TextBuddy.executeCommand("search no such word");
		assertEquals("No result found.", output);
		
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add a");
		TextBuddy.executeCommand("add b");
		TextBuddy.executeCommand("add c");
		TextBuddy.executeCommand("add d");
		TextBuddy.executeCommand("add e");
		output = TextBuddy.executeCommand("search c");
		assertEquals("3. c\n", output);
		
		// search ignoring charter case
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add applE");
		TextBuddy.executeCommand("add bOOk");
		TextBuddy.executeCommand("add sleep");
		TextBuddy.executeCommand("add dog");
		TextBuddy.executeCommand("add eAting");
		output = TextBuddy.executeCommand("search a");
		assertEquals("1. applE\n5. eAting\n", output);
	}

}
