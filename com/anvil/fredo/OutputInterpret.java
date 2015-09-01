package com.anvil.fredo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OutputInterpret {
	//It's console output, but player/etc. input
	int serverID;
	File OutputR;
	BufferedWriter bw;
	String input;
	String txt;
	
	BufferedReader fileReader;
	String output;
	boolean scriptRun;
	
	String playerName;
	int cmdIssuer;
	final int PLAYER_ISSUED_TXT = 0;
	
	public OutputInterpret(int id) throws IOException {
		serverID = id;
		OutputR = new File("OutputReduced.txt");
		bw = new BufferedWriter(new FileWriter(OutputR));
		
		System.out.println("Output Interpreter starting up");
		Server.sendCommand("say hello");
		
		playerName = "";
	
	}
	
	public void Interpret(String serverInput) throws IOException {
		
		if (!Server.getRunning(serverID)) {return;}
		
		//System.out.println("Successfully ran interpret!");
		
		try {
			
			this.input = serverInput;
			Reduce();				//removes nonuseful information from input
			bw.write(input);		//Writes reduced input to output log
			bw.newLine();			//"resets" buffered writer
			bw.flush();
			
		} catch (Exception e) {
			System.out.println("The server probably threw an error and the interpreter wasn't able to figure it out");
		}
		
		//System.out.println(input);	//Prints reduced information to the console
		
		//For player issued commands (The '<' indicates a player just spoke. Tellraws don't get sent to the input stream so they shouldn't be a bother.)
		if (input.charAt(0) == '<') {
			
			cmdIssuer = PLAYER_ISSUED_TXT;
			
			for (int i = 3; i < input.length(); i++) {
				if (input.charAt(i)== '>') {
					playerName = input.substring(1, i);	//Gets which player entered input
					
					txt = input.substring(i+2);	//Gets what the player inputted
					//System.out.println();	//Prints a line for legibility's sake
					
				}
			}
			
			if (!txt.startsWith("!")) {return;} //Cuts interpretation if the player hasn't sent a command
			//else :V
			
			scriptRun = true;
			File scriptExclaim = new File("ExclaimScript");
			fileReader = new BufferedReader(new FileReader(scriptExclaim));
			
			while (scriptRun) {
			
				try {
					output = fileReader.readLine();
					output = output.replace("@playername", playerName);
					
					if (output.startsWith("print")) {
						output = output.substring(5).trim();
						System.out.println(output);
					} else {
				
						Server.sendCommand(output);
					
					}
				
				} catch (NullPointerException e) {
					scriptRun = false;
					System.out.println("A player has executed the \"!\" ");
				}
				
			}
			//If a command HAS been spoken, we'd at this point find out what the command was and check player permissions
			//For now, anytime a player enters the ! it will run a testing script (to practice scripts and just because)
			//Server.sendCommand("say YO!!!!! " + playerName + "! How YOU doin'?");
		}
		
		
		
		
		//we have to interpret the command AND the player THEN see if the player is allowed to use it
		//Hell player perms might reset too, we have to keep it updated. Say they're in a game, the !TP command
		//would not be the same.
		
		//input = input.substring(33);
		//System.out.println(input);
		//input = input.startsWith("");
		
		
		
	}
	
	private void Reduce() throws IOException {
		
		input = input.substring(11);
		
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ']') {
				input = input.substring(i+3);	//We need to do some try catching here, as some of the strings have input out of bounds. We can't interpret that. so if it fails, have the catch jut return some bs :)
				bw.write(input);
				bw.newLine();
				return;
			}
		}
		
	}
	
	
	//Check the players' allowed commands
	private boolean canExec(Player pl, String command) {
		
		return false;
	}
	
}
