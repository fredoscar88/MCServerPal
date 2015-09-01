package com.anvil.fredo;

import java.util.Scanner;
//THIS IS WHERE WE MODULARIZE THE CONSOLE, BUT NOT NOW. FOR NOW IM JUST GOING TO MAKE A NEW ONE IN SERVER AND SAVE THIS SHIT FOR LATER.


public class Console {
/*
	static String ConsoleInput;
	static String ConsoleCmd;
	static Scanner ConsoleReader;
	static String Parse;
	static boolean running = true;
	
	//We need to make the console more modular...
	
	public Console(String title) {
		
		ConsoleRun(title, true);
	}
	
	private void ConsoleRun(String ConsoleStartMessage, boolean canRun) {
		ConsoleReader = new Scanner(System.in);
		
		System.out.println(ConsoleStartMessage);
		System.out.println("Type \"?\" or \"help\" for help.");
		
		while (canRun) {
			
			if (ConsoleInput == null) {
				
				System.out.print(": ");
				ConsoleInput = ConsoleReader.nextLine();
				ConsoleInput = ConsoleInput.toLowerCase();
					
				ConsoleParse(ConsoleInput);
				ConsoleCmd = Parse;
					
			} else {
				ConsoleParse(ConsoleInput);
				ConsoleCmd = Parse;
			}
			
			if (ConsoleCmd.equals("stop")) {
				canRun = false;
			} else {
			
				ConsoleAction(ConsoleCmd);
			}
			
			//Here, we'd send ConsoleCmd to be checked against a list of command words to perform console actions.
			//This is dependent on the command or action word preceding it. Some action words incorporate the succeeding 
			//words so whatever method reads these and performs actions will account for that. I think I should save this
			//console stuff somewhere :I
			//Action words that don't require succeeding words should wipe ConsoleInput to null, and invalid input should
			//inform the user of the invalid command.
			//For text adventure games we might need something different, something also capable of unlocking new command
			//words, with an adaptive help menu
			
		}
	}
	
	/*static void ConsoleAction (String command) {
		
		switch (command) {
		case "help": System.out.println("Hah there is no help for you"); break;
		default : System.out.println("Type \"Help\" or \"?\" for help"); Main.ConsoleInput = null;
		
		}
		
	}*/
	
	/*private boolean ConsoleParse(String ConsoleInput) {
		
		if (ConsoleInput == null) {
			System.out.println("Console input is null!");
			Parse = null;
			return false;
		}	
		
		ConsoleInput = ConsoleInput.trim();
		String ParseNext = ConsoleInput;
		ConsoleInput = ConsoleInput.replace(" ", "_");
		boolean runFor = true;
		
		
		if (ParseNext.equals(ConsoleInput)) {	//This condition checks to see if the input is the only command word
			
			Main.ConsoleInput = null;	//Since we've just parsed the entirety of the command, there is no more left, so we reset it
			Parse = ParseNext;	
			return true;
		}
		
		
		
		
		/*if (ConsoleInput.trim().equals(ParseNext.trim())) {
			System.out.println("DEBUG: There are no spaces in the input command");
			Parse = ParseNext;
			return true;
		}*/
		
		
		/*for (int i = 0; i < ParseNext.length() && runFor; i++) {
				
			if (ParseNext.substring(i, i+1).equals(" ")) {
				
				ConsoleInput = ParseNext.substring(i+1);
				ParseNext = ParseNext.substring(0,i);
				runFor = false;
			}
			else if ((i == (ParseNext.length() - 1)) && runFor) {
				
				System.out.println("Hello, world");
				return false;	//Theoretically we'll never see this. This case can only be reached if the parser runs and doesn't find a space, but if there are no spaces to be found it is caught earlier
				
			}
			
		}
		
		
		Main.ConsoleInput = ConsoleInput;
		Parse = ParseNext;
		return true;
	}
	
	
	//Public methods
	
	public void runTrue() {	//For when we stop a server. Frees us to use the main console again.
		//It basically overwrites the stop setting that we instigate when we run the server
		Main.ConsoleInput = null;
	}
	
	
	public String GetParseNext() {
		
		
		return Parse;
	}*/
}
