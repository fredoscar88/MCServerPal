package com.anvil.fredo;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

@SuppressWarnings("unused")
public class Main {

	static String ConsoleInput/* = "start"*/; 	//temporarily defaults to start //(not right now)
	static String ConsoleCmd;
	static Scanner ConsoleReader;
	static String Parse;
	static boolean running = true;
	
	//I eventually want the main class to open a new console so I don't have to run the program from
	//	a batch file.
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		//Upon every start we need to check to see if we have all of the files/directories that we need, if not we need to create them
		System.out.println("Running MyServerPal!");
		
		//We also need to test where these directories are being made!
		File testDir = new File("Test" + File.separator + "DoubleTest");
		testDir.mkdirs();
		
		
		//ConsoleInput = "start";
		
		//Temp disabling
		//ConsoleRun("Server Command Inserter", running);	//Accepted parameter of ConsoleRun is the startup message
	}
	
	//We need to make the console more modular...
	static void ConsoleRun(String ConsoleStartMessage, boolean canRun) throws IOException, InterruptedException {
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
				System.out.println("The main thread has been stopped! I decided not to opt to returning to just running the command console");
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
	
	static void ConsoleAction (String command) throws IOException, InterruptedException {
		
		switch (command) {
		case "help": System.out.println("Hah there is no help for you"); break;
		case "create": if (ConsoleParse(ConsoleInput)) {createServer(Parse);} Main.ConsoleInput = null; break;
		case "start": StartServer(); break;
		default : System.out.println("Type \"Help\" or \"?\" for help"); Main.ConsoleInput = null;
		
		}
		
	}
	
	static void createServer(String serverName) {
		
	}
	
	//(REMOVE)The whole start server is getting overhauled. Until I remove this comment, we're focusing on setting up a new server.
	static void StartServer() throws IOException, InterruptedException {
		
		
		
		if (ConsoleParse(Main.ConsoleInput)) {
			System.out.println("Running server: " + Parse);
			
			new Server(Parse);
		}
		else {
			System.out.println("Running default server name");
			new Server(0);	//This, this will not be here. We should only start servers with their name.
							//(Or rather use their name to locate their id from the data file. c: )
			System.out.println("Server has closed, this program SHOULD terminate. (3)");
			
		}
		
		//The idea here is once the server is closed we don't need to use this program anymore, but I might change this
		System.out.println("So it should stop now.");
		Main.ConsoleInput = "stop";
		
		
	}
	
	static void runTrue() {	//For when we stop a server. Frees us to use the main console again.
							//It basically overwrites the stop setting that we instigate when we run the server
		Main.ConsoleInput = null;
	}
	
	static boolean ConsoleParse(String ConsoleInput) {
		
		if (ConsoleInput == null) {
			System.out.println("Console input is null!");
			Parse = null;
			return false;
		}	
		
		ConsoleInput = ConsoleInput.trim();	//removes any extra whitespace on the front and end
		String ParseNext = ConsoleInput;	//Sets the ParseNext to ConsoleInput
		ConsoleInput = ConsoleInput.replace(" ", "_");	//Replaces remaining spaces with underscore
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
		
		//Parses out the next word
		for (int i = 0; i < ParseNext.length() && runFor; i++) {
				
			if (ParseNext.substring(i, i+1).equals(" ")) {
				
				ConsoleInput = ParseNext.substring(i+1);	//Removes the string we've just parsed from the rest of the input
				ParseNext = ParseNext.substring(0,i);	//Removes whatever we don't want from our parse
				runFor = false;							//Stops running
			}
			else if ((i == (ParseNext.length() - 1)) && runFor) {
				
				System.out.println("Hello, world, you should never see me!");
				return false;	//Theoretically we'll never see this. This case can only be reached if the parser runs and doesn't find a space, but if there are no spaces to be found it is caught earlier
				
			}
			
		}
		
		
		Main.ConsoleInput = ConsoleInput;
		Parse = ParseNext;
		return true;
	}
	
	

}
