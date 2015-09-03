package com.anvil.fredo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//THIS IS WHERE WE MODULARIZE THE CONSOLE, BUT NOT NOW. FOR NOW IM JUST GOING TO MAKE A NEW ONE IN SERVER AND SAVE THIS SHIT FOR LATER.


public class Console {

	Scanner ConsoleReader;
	List<String> cmd;
	String ConsoleInput;
	String Parse;
	String ConsoleCmd; //May be deprecated
	
	String ConsoleStartMessage;
	
	public Console(String name) {
		ConsoleStartMessage = name;
		
		System.out.println(ConsoleStartMessage);
		System.out.println("Type \"?\" or \"help\" for help.");
		
	}
	
	//Please note: WHEN we move over to a UI we won't be handling input to the console the same way.
	//It will come from a text field rather than the console itself, and will output to the same pane that
	//The server is outputting from, prefixed probably by [MCServerPal]
	//We probably don't have to change much in this class, thank goodness. All we change is how we input to
	//this class.
	List<String> ConsoleRun() throws IOException, InterruptedException {
		ConsoleReader = new Scanner(System.in);
		
		if (ConsoleInput == null) {
			
			System.out.print(": ");
			ConsoleInput = ConsoleReader.nextLine();
			//ConsoleInput = ConsoleInput.toLowerCase();
				
			ConsoleParseCmd(ConsoleInput);	//soon... (divides the string into a list of the commands syntax)
			ConsoleInput = null;	//For when we use the string divvier
			//ConsoleParse(ConsoleInput);	//Here is where the string should be divied up
			// ^ Deprecated
				
		} else {
			ConsoleParseCmd(ConsoleInput);
		}
		
		/*if (cmd.get(0).equals("stop")) {	//Ehhhhhhhhhhhhhhh maybe not in this class
			System.out.println("The main thread has been stopped! I decided not to opt to returning to just running the command console");
			canRun = false;
		}*/
		
		return cmd;
	}
	
	List<String> ConsoleRun (String cmdInput) throws IOException, InterruptedException {
		
		ConsoleInput = cmdInput;
		
		return ConsoleRun();
	}
	
	//Keeping this for legacy purposes
	/*boolean ConsoleParse(String ConsoleInput) {
		//NOTE BIEN! We should change this to use a list! We can parse out each value of a command
		//and plug them in to their specific syntax spots!
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
		/*for (int i = 0; i < ParseNext.length() && runFor; i++) {
				
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
	}*/
	
	void ConsoleParseCmd(String ConsoleInput) {
		
		boolean jRun;
		boolean iRun;
		//System.out.println("CPC ran");
		cmd = new ArrayList<String>();
		
		try {
			iRun = true;
			for (int i =0; iRun; i++) {
				jRun=true;
				for (int j = 0; jRun; j++) {
					if (ConsoleInput.replace(" ", "_").equals(ConsoleInput)) {
						cmd.add(ConsoleInput);
						iRun = false;
						jRun = false;
					}
					
					if (ConsoleInput.substring(j, j+1).equals(" ")) {
						cmd.add(/*i, */ConsoleInput.substring(0, j));
						ConsoleInput = ConsoleInput.substring(j+1);
						jRun = false;
					}
					
				}
				
			}
			
		} finally {
			/*System.out.println(cmd.size());
			for (int i = 0;i < cmd.size();i++) {
				System.out.print(cmd.get(i) + " ");
			}
			System.out.println();*/
		}
		
	}
	
	
}

//All of the below is blah. Atm we're gunna change it to where any command can grab the syntax from the console. It's not the best solution (for ex. it doesn't really allow for multiple option flags, that would require constant parsing (i.e "--<optionname>" flags))
		//Here, we'd send ConsoleCmd to be checked against a list of command words to perform console actions.
		//This is dependent on the command or action word preceding it. Some action words incorporate the succeeding 
		//words so whatever method reads these and performs actions will account for that. I think I should save this
		//console stuff somewhere :I
		//Action words that don't require succeeding words should wipe ConsoleInput to null, and invalid input should
		//inform the user of the invalid command.
		//For text adventure games we might need something different, something also capable of unlocking new command
		//words, with an adaptive help menu
