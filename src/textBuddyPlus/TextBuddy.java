package textBuddyPlus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class is used to manipulate different texts in a text file. You could
 * add, delete, display and clear text in any given file.
 * 
 * The text you added in the file are named task in the program.
 * 
 * You should enter a full and valid command for the program to execute the
 * operation The valid commands are listed below:
 * 
 * Add Text Command input: add text_to_be_added E.g add Some event to be added
 * (Valid command) add (invalid command, no text to be added)
 * 
 * Delete Text Command input: delete index_to_be_deleted E.g delete 3 (Valid
 * command) delete -1 / delete aaa (invalid command, the index is invalid)
 * 
 * Display Text Command input: display
 * 
 * Clear Text Command input: clear
 * 
 * Exit Program Command input: exit
 * 
 * If the input text file the program received does not exist, then we assume
 * that a new file would be created using the provided name.
 * 
 * @author Jiang Sheng
 * 
 */

public class TextBuddy {
	// Message Constant
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy! %1$s is ready for use.";
	private static final String MESSAGE_ENTER_COMMAND = "Enter command:";
	private static final String MESSAGE_GOODBYE = "Thank you for using TextBuddy!";
	private static final String MESSAGE_ADDED = "Added to %1$s: \"%2$s\".";
	private static final String MESSAGE_DELETED = "Deleted from %1$s: \"%2$s\".";
	private static final String MESSAGE_CLEAR = "All content deleted from %1$s.";
	private static final String MESSAGE_EMPTY = "There is no item in %1$s now.";
	private static final String MESSAGE_SORT = "Tasks sorted.";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "No result found.";

	// Error Constant
	private static final String ERROR_INVALID_COMMAND = "The command you entered is invalid!";
	private static final String ERROR_EMPTY_TASK = "Cannot add an empty task!";
	private static final String ERROR_INVALID_INDEX = "Please enter a valid index number!";
	private static final String ERROR_IO = "Error when trying to read/write the file.";

	// This constant is used for fixing the difference between index in the
	// Task List (Starting from 1) and index stored in the LinkedList (Starting
	// from 0)
	private static final int INDEX_SHIFT_FIX = 1;

	// This is used to store the given file name
	private static String fileName = null;
	
	// These two Reader and Writer are used to access and manipulate the given
	// text file
	private static BufferedReader reader;
	private static BufferedWriter writer;
	
	// This is used to read in user input command
	private static Scanner scanner = new Scanner(System.in);

	// This is used to store tasks that read from a file
	private static LinkedList<String> tasks = new LinkedList<String>();

	public static void main(String[] args) {
		initializeTextBuddy(args);
		while (true) {
			String command = readUserCommand();
			String userCommand = command;
			String feedback = executeCommand(userCommand);
			showToUser(feedback);
		}

	}

	/**
	 * This method initializes the program, get access to the file and loads
	 * task from the file.
	 * 
	 * @param args the arguments input from the terminal
	 */
	public static void initializeTextBuddy(String[] args) {
//		String inputFile = args[0];
		String inputFile = "input.txt";
		fileName = inputFile;
		initializeFileOperator(fileName);
		loadTasks();
		showToUser(String.format(MESSAGE_WELCOME, fileName));
	}

	/**
	 * This method initialize the reader and writer for I/O purpose, if the
	 * given file exist, then it will access the file; otherwise, it will create
	 * a new file and access it.
	 * 
	 * @param fileName the file name input from the terminal
	 */
	private static void initializeFileOperator(String fileName) {
		File inputFile = new File(fileName);
		try {
			if (inputFile.exists()) {
				reader = new BufferedReader(new FileReader(inputFile));
				writer = new BufferedWriter(new FileWriter(inputFile, true));
			} else {
				// if the file does not exist, we create a new file
				writer = new BufferedWriter(new FileWriter(inputFile, false));
				reader = new BufferedReader(new FileReader(inputFile));
			}
		} catch (Exception e) {
			throw new Error(ERROR_IO);
		}
	}

	/**
	 * Loading existing tasks from files Put them into a task list for later use
	 * 
	 */
	private static void loadTasks() {
		String input = null;
		try {
			while ((input = reader.readLine()) != null) {
				tasks.add(input.trim());
			}
		} catch (IOException e) {
			throw new Error(ERROR_IO);
		}
	}

	/**
	 * This method execute task manipulation function according to the user
	 * command. And return the corresponding feedback message.
	 * 
	 * @param userCommand user's input command
	 * @return a feedback message
	 */
	public static String executeCommand(String userCommand) {
		// decide the command category based on the first word of user input.
		String commandType = getFirstWord(userCommand);

		if (commandType.equalsIgnoreCase("add")) {
			return addTask(userCommand);
		} else if (commandType.equalsIgnoreCase("delete")) {
			return deleteTask(userCommand);
		} else if (commandType.equalsIgnoreCase("display")) {
			return displayTask();
		} else if (commandType.equalsIgnoreCase("clear")) {
			return clearTask();
		} else if (commandType.equalsIgnoreCase("sort")) {
			return sortTask();
		} else if (commandType.equalsIgnoreCase("search")) {
			return searchTask(userCommand);
		} else if (commandType.equalsIgnoreCase("exit")) {
			exitTextBuddy();
			System.exit(0);
			return null; // This line will never be reach, just a dummy value
		} else {
			return ERROR_INVALID_COMMAND;
		}
	}
	
	private static String searchTask(String userCommand) {
		
		return MESSAGE_SEARCH_NOT_FOUND;
	}

	/*
	 * Sort tasks alphabetically
	 * 'alphabetically' means uppercase and lowercase don't affect sort results.
	 * For example, 'apple' should be in front of 'Book', even though 'B' has a 
	 * bigger ASCii value than 'a' 
	 */
	private static String sortTask() {
		
		class SortComparator implements Comparator<String> {
			@Override
			public int compare(String task1, String task2) {
				String task1FirstChar = task1.substring(0, 1);
				String task2FirstChar = task2.substring(0, 1);
				
				return task1FirstChar.compareToIgnoreCase(task2FirstChar);
			}
		}
		
		Collections.sort(tasks, new SortComparator());
		
		// save changes to the file
		writeChanges();
		return MESSAGE_SORT;
	}

	/**
	 * This method adds a task to the file.
	 * 
	 * @param userCommand the user input
	 * @return a feedback string indicating the status of the operation
	 */
	private static String addTask(String userCommand) {
		// get the task that needs to added to the file
		String newTask = removeFirstWord(userCommand);

		// if the task to be added is empty, show the error message
		if (newTask.isEmpty()) {
			return ERROR_EMPTY_TASK;
		}

		tasks.add(newTask);

		// writing new missions to the file
		writeToFile(newTask);
		return String.format(MESSAGE_ADDED, fileName, newTask);
	}

	/**
	 * This methods delete a task from the file
	 * 
	 * @param userCommand the user input
	 * @return a feedback string indicating the status of the operation
	 */
	private static String deleteTask(String userCommand) {
		// check if the index is numeric.
		int indexToDelete;
		try {
			indexToDelete = Integer.parseInt(removeFirstWord(userCommand));
		} catch (NumberFormatException e) {
			return ERROR_INVALID_INDEX;
		}

		// validate indexToDelete
		if (isTaskIndexInRange(indexToDelete)) {
			String deletedTask = tasks.remove(indexToDelete - INDEX_SHIFT_FIX);
			writeChanges();
			return String.format(MESSAGE_DELETED, fileName, deletedTask);
		} else {
			// show the error message
			return ERROR_INVALID_INDEX;
		}
	}

	/**
	 * This method shows current task to the user
	 * 
	 * @return the list of tasks
	 */
	private static String displayTask() {
		if (tasks.isEmpty()) {
			return String.format(MESSAGE_EMPTY, fileName);
		}

		return buildTaskList(tasks);
	}

	/**
	 * This method prepares the task list for print on the screen
	 * 
	 * @return a nice-formatted task list String
	 */
	private static String buildTaskList(LinkedList<String> tasks) {
		StringBuilder taskList = new StringBuilder();
		for (int i = 0; i < tasks.size(); i++) {
			int indexForDisplay = i + INDEX_SHIFT_FIX;
			taskList.append(indexForDisplay);
			taskList.append(". ");
			taskList.append(tasks.get(i));
			if (i != (tasks.size() - INDEX_SHIFT_FIX)) {
				// If reach the last task of the list, don't output the new line
				taskList.append("\n");
			}
		}
		return taskList.toString();
	}

	/**
	 * This class clear all the task from the file
	 * 
	 * @return a feedback message indicating if the operation is successful.
	 */
	private static String clearTask() {
		clearFiles();
		tasks.clear();
		return String.format(MESSAGE_CLEAR, fileName);
	}
	
	/**
	 * This method saves the changes to the file, and close all the I/O operator for
	 * exiting program.
	 */
	private static void exitTextBuddy() {
		writeChanges();
		scanner.close();
		try {
			reader.close();
			writer.close();
		} catch (Exception e) {
			throw new Error(ERROR_IO);
		}
		showToUser(MESSAGE_GOODBYE);
	}

	/**
	 * This method writes a new task to the file in a new line.
	 * 
	 * @param task the task to be wrote
	 */
	private static void writeToFile(String task) {
		try {
			writer.write(task);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			throw new Error(ERROR_IO);
		}
	}
	
	/**
	 * This method write changes to the file
	 */
	private static void writeChanges() {
		clearFiles();
		// read current tasks from the task list and write them to the file
		for (int i = 0; i < tasks.size(); i++) {
			writeToFile(tasks.get(i));
		}
	}
	
	/**
	 * This method will clear the file's content
	 */
	private static void clearFiles() {
		try {
			// rewrite the file immediately
			writer = new BufferedWriter(new FileWriter(fileName, false));
		} catch (Exception e) {
			throw new Error(ERROR_IO);
		}
	}
	
	/**
	 * This method checks how many lines are in the input file
	 * (For testing purpose)
	 */
	public static int getLineCount() {
		int lines = 0;
		try {
			BufferedReader countReader = new BufferedReader(new FileReader(fileName));
			while (countReader.readLine() != null) lines++;
			countReader.close();
		} catch (Exception e) {
			throw new Error(ERROR_IO);
		}
		return lines;
	}

	/**
	 * This method checks if a task index is valid
	 * 
	 * @param indexToDelete the index to be checked
	 * @return true if the index is valid in the task list
	 */
	private static boolean isTaskIndexInRange(int taskIndex) {
		return taskIndex > 0 && taskIndex <= tasks.size();
	}
	
	/**
	 * This method will remove the command type(the first word), and return the command content
	 */
	private static String removeFirstWord(String userCommand) {
		return userCommand.replaceFirst(getFirstWord(userCommand), "").trim();
	}
	
	/**
	 * This method will get the command type(the first word) from user input command 
	 */
	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	/**
	 * This methods reads user command
	 */
	private static String readUserCommand() {
		System.out.print(MESSAGE_ENTER_COMMAND);
		String command = scanner.nextLine();
		return command;
	}

	/**
	 * This method shows feedback message to the user
	 */
	private static void showToUser(String text) {
		System.out.println(text);
	}
	
}

