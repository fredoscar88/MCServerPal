package com.anvil.fredo;

//import java.text.DecimalFormat;
import java.util.Scanner;


public class FORREFERENCE {
/*
	static String ConsoleInput;
	static String ConsoleCmd;
	static Scanner ConsoleReader;
	static int Iteration = 0; 
	static boolean toNull = false;
	static boolean running = true;	//Also read this from a file
	static boolean debug = false;
	static Permute p;
	
	//We need to load these from a file
	static boolean permToSpellCheck = true;
	static boolean permToCull = true;
	
	public static void main(String[] args) {
		
		String output= "Hello";
		output = output.concat("\n" + output);
		System.out.println(output);
		
		
		
		//ConsoleRun("Permutation lister v1.0");	//Accepted parameter of ConsoleRun is the startup message
	}
	//Really need to move the permutation to a separate class
	
	//main engine
	static void ConsoleRun(String ConsoleStartMessage) {
		ConsoleReader = new Scanner(System.in);
		
		System.out.println(ConsoleStartMessage);
		System.out.println("Type \"?\" or \"help\" for help.");
		//System.out.println("Debug (should say 'null'): " + ConsoleInput);
		
		while (running) {
			
			if (ConsoleInput == null) {
				
				System.out.print(": ");
				ConsoleInput = ConsoleReader.nextLine();
				//ConsoleInput = ConsoleInput.toLowerCase();
					
				ConsoleCmd = ConsoleParse(ConsoleInput);
					
			} else {
				ConsoleCmd = ConsoleParse(ConsoleInput);
			}
			
			//Here, we'd send ConsoleCmd to be checked against a list of command words to perform console actions.
			//This is dependent on the command or action word preceding it. Some action words incorporate the succeeding 
			//words so whatever method reads these and performs actions will account for that. I think I should save this
			//console stuff somewhere :I
			//Action words that don't require succeeding words should wipe ConsoleInput to null, and invalid input should
			//inform the user of the invalid command.
			
			//For the purposes of this program though we dont need such an interactive console.
			
			ConsoleAction(ConsoleCmd);
			
			//System.out.println(ConsoleCmd + Iteration); 	//Action word that just performed
			//System.out.println(ConsoleInput + Iteration);	//Remaining command to execute
			Iteration++;
		}
	}
	
	//Theoretically, we can just run this in the Action performer, too. I might try that.
	static String ConsoleParse(String ConsoleInput) {
		String ParseNext = ConsoleInput;
		
		for (int i = 0; i < ParseNext.length(); i++) {
				
			if (ParseNext.substring(i, i+1).equals(" ")) {
				
				ConsoleInput = ParseNext.substring(i+1);
				ParseNext = ParseNext.substring(0,i);
				
			}
			else {
				ConsoleInput = null;
			}
			
		}
		
		if (ParseNext.equals("")) ParseNext = null;
		
		Main.ConsoleInput = ConsoleInput;
		return ParseNext;
	}
	//could use .equalsIgnoreCase or w/e it is instead of 2 ConsoleParses..
	static String ConsoleParse(String ConsoleInput, boolean toLowerCase) {
		String ParseNext;
		
		if (toLowerCase) {
			ParseNext = ConsoleInput.toLowerCase();
		}
		else {
			ParseNext = ConsoleInput;
		}
		
		for (int i = 0; i < ParseNext.length(); i++) {
				
			if (ParseNext.substring(i, i+1).equals(" ")) {
				
				ConsoleInput = ParseNext.substring(i+1);
				ParseNext = ParseNext.substring(0,i);
				
			}
			else {
				ConsoleInput = null;
			}
			
		}
		
		Main.ConsoleInput = ConsoleInput;
		return ParseNext;
	}
	
	static void ConsoleAction(String ConsoleCmd) {
		
		try {
			switch (ConsoleCmd) {
			case "?":
			case "help": System.out.println("Input any variety of characters to create a permutation list"); listCommands(); ConsoleInputNull(); break;
			case "permute": p = new Permute(ConsoleParse(Main.ConsoleInput)); ConsoleInputNull(); break;
			case "permlist": p.listPermute(permToSpellCheck, permToCull); break;
			case "spellcheck": spellcheck(ConsoleParse(Main.ConsoleInput)); break;
			case "repeat": cull(ConsoleParse(Main.ConsoleInput)); break;
			case "print": System.out.println(ConsoleParse(Main.ConsoleInput)); ConsoleInputNull(); break;
			case "easteregg": System.out.println("You've found easter!"); break;
			case "settings": listSettings(); break;
			case "save": break;
				
			//add in single quotes command interpretation (i.e inputting 'hello there', "hello there" would be the output of the parse
			case "aaron foulkrod": System.out.println("Congratulations, you figured out my crush. Because of your insolence I'm going to disable the use of this program on your device!"); Main.running = false; break;//write to file 'running: false' too
			case "": System.out.println("Oh dear :c (The string is null. This can happen if the ConsoleParse is called more than once during an iteration of ConsoleRun, or if the input string is a space)"); ConsoleInputNull(); break;	//This case will (should) never be reached
			case "stop": Main.running = false; System.out.println("Program terminated."); break;
			default: ConsoleInputNull(); System.out.println("Command not found, type \"help\" or \"?\" for a list of commands.");
			}
		} catch (Exception e) {
			System.out.println("Bad command! (Did you remember all of the syntax?)");
			ConsoleInputNull();
		}
	}
	
	static void listCommands() {
		System.out.println("List of commands:"
				+ "\npermute <string>: Returns a list of all possible permutations of the input string (limit in size to 16 chars)"
				+ "\nprint {<string>|'<string>'}: Returns input"//right now single quotes aren't supported
				+ "\nspellcheck <true/false>: Disables spell-check on the \"permute\" command"
				+ "\nstop: Stops the program"
				+ "\nhashclear: Clears the hashset");
	}
	
	static void spellcheck(String action) {
		
		switch (action) {
		case "toggle": Main.permToSpellCheck = !Main.permToSpellCheck; break;
		case "true": Main.permToSpellCheck = true; break;
		case "false": Main.permToSpellCheck = false; break;
			default: System.out.println("Invalid action!");
		}
	}
	
	static void cull(String action) {
		switch (action) {
		case "toggle": Main.permToCull = !Main.permToCull; break;
		case "true": Main.permToCull = false; break;
		case "false": Main.permToCull = true; break;
			default: System.out.println("Invalid action!");
		}
	}
		
	static void listSettings() {
		System.out.println("Permutation settings:"
				+ "\nSpellcheck enabled: " + Main.permToSpellCheck
				+ "\nRepeats enabled: " + !Main.permToCull);
	}
	
	
	
	
	static void ConsoleInputNull() {
		Main.ConsoleInput = null;
	}

	static void Debug(String message) {
		
		if (debug) System.out.println(message);
		
	}
	
	*/
}
