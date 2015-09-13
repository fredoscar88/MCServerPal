package com.anvil.fredo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Main {

	static String ConsoleInput/* = "start"*/; 	//temporarily defaults to start //(not right now)
	static String ConsoleCmd;	//
	static Scanner ConsoleReader;	//Scanner to acquire input
	static String Parse;	//next value pulled from console input. The next "word"
	static boolean running;	//If the main thread is running. If false should close all other threads.
	static List<String> cmd;
	static int parseInt;
	
	static File dirServers;	//Servers directory
	static File dirRes;	//Resources directory
	static File dataServerData;	//General server data file	(Contains meta data like # of servers)
	
	static BufferedWriter fileWriter;	//May not be needed
	static String fileReadoutValue;
	
	static FileUpdater MainThreadFileUpdater;
	
	static Console mainConsole;
	
	
	//I eventually want the main class to open a new console so I don't have to run the program from
	//	a batch file.
	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println("Running MCServerPal!");
		
		MainThreadFileUpdater = new FileUpdater();	//For acquiring/changing data from a file (for the main thread)
		
		addDirectories();	//Adds directories, stores them in variables.
		
		cmd = new ArrayList<String>();
		
		//cnsAutostart();	//This method, when called with no parameters, will automatically start whatever server is specified, if any
						//if called with parameters it changes the autostart option
		
		mainConsole = new Console("Main Console");
		mainRunning(mainConsole);
		
		output("MCServerPal closing");
		
	
	}
	
	static void mainRunning(Console console) throws IOException, InterruptedException {
		
		running = true;
		
		while (running) {
			
			cmd.clear();
			cmd = console.ConsoleRun();
			ConsoleAction(cmd);
			
			
		}
		
	}
	
	//We need to make the console more modular...
	
	static void ConsoleAction (String command) throws IOException, InterruptedException {
		
		switch (command) {
		case "help": System.out.println("Type \"help <command>\" for more information on a specific command"); break;
		//case "create": if (ConsoleParse(ConsoleInput)) {createServer(Parse);} Main.ConsoleInput = null; break;
		case "exit": running=false; break; //Exits console, stops all servers (by sending stop commands to their own ConsoleAction menus)
		case "start": StartServer(); break;
		case "autostart": //Changes the value in dataServerData for Autostart. The if REMOVE is typed, clears out Autostart=
		default : System.out.println("Type \"Help\" or \"?\" for help"); Main.ConsoleInput = null;
		
		}
		
	}
	
	static void ConsoleAction (List<String> cmd) throws IOException, InterruptedException {
		
		
		try {
			//System.out.println(cmd.get(0) + " " + cmd.get(1));
			switch (cmd.get(0)) {
			case "help": help(cmd); break;
			case "create": createServer(cmd.get(1)); break;
			case "exit": running=false; break; //Exits console, stops all servers (by sending stop commands to their own ConsoleAction menus, we'll create a function for this)
			case "start": StartServer(); break;
			case "autostart": cnsAutostart(cmd.get(1)); break;//Changes the value in dataServerData for Autostart. The if REMOVE is typed, clears out Autostart. If RETURN is typed, returns the current autostarted server
			
			case "getID": System.out.println(Server.getID(cmd.get(1))); break;
			//before I changed this, a 6 printed out, and I don't know why
			default : System.out.println("Type \"Help\" or \"?\" for help"); Main.ConsoleInput = null;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("[MCServerPal] Error: Missing syntax");
			getSyntax(cmd.get(0));
			e.printStackTrace();
		}
		
	}
	
	
	//(REMOVE)The whole start server is getting overhauled. Until I remove this comment, we're focusing on setting up a new server.
	static void StartServer() throws IOException, InterruptedException {
		
		/*if (true) {
			System.out.println("Running server: " + Parse);
			
			//new Server(Parse);
		}
		else {
			System.out.println("Running default server name");
			new Server(0);	//This, this will not be here. We should only start servers with their name.
							//(Or rather use their name to locate their id from the data file. c: )
			System.out.println("Server has closed, this program SHOULD terminate. (3)");
			
		}*/
		
		//The idea here is once the server is closed we don't need to use this program anymore, but I might change this
		System.out.println("So it should stop now.");
		Main.ConsoleInput = "stop";
		
		
	}

	static void addDirectories() throws IOException {

		//Puts the directories/files into objects for use
		dirServers = new File("Servers");
		dirRes = new File("res");
		dataServerData = new File("res" + File.separator + "ServerData");
		
		//Creates the directories/files if they don't exist
		dirServers.mkdirs();
		dirRes.mkdirs();
		
		if (dataServerData.createNewFile()) {
			
			//When we make a new file, we should really be copying it from the jar.
			//For now I'm just going to write the info, but this info should be readily copyable
			//so it can A) be easily changed and B) I don't have to hard-code default settings for
			//files
			
			//dataServerData = copyServerData;
			MainThreadFileUpdater.write(dataServerData, "Number_Of_Servers=0\nAutostart=");
			
			/*fileWriter = new BufferedWriter(new FileWriter(dataServerData));
			fileWriter.write("Number_Of_Servers=0\nAutostart=\n");
			fileWriter.flush();*/
		}
	}

	static void output (String message) {
		System.out.println("[MCServerPal] " + message);
	}
	
	//Console actions, stuff from ConsoleAction() V:
	static void createServer(String serverName) throws IOException {
		String servFolderPath = "Servers" + File.separator + serverName + File.separator;
		int NumberOfServers;
		
		new Server(serverName, servFolderPath);	
		MainThreadFileUpdater.changeSetting(dataServerData, "Number_Of_Servers", Integer.toString(Server.getNoOfServ()));
		
		
		//Other dirs here :<
	}
	
	static void StartServer(String serverToStart) throws IOException, InterruptedException {
		
	}
	
	static void cnsAutostart() throws IOException, InterruptedException {
		
		String serverToStart = MainThreadFileUpdater.getSetting(dataServerData, "Autostart");
		
		if (serverToStart == null) {
			System.out.println("No server to autostart found");
			return;
		}	else {
			List<String> temp = new ArrayList<String>();
			temp.add("start");
			temp.add(serverToStart);
			System.out.println(temp.get(0) + " " + temp.get(1));
			ConsoleAction(temp);
		}
	}
	static void cnsAutostart(String server) throws IOException {
		String temp = MainThreadFileUpdater.getSetting(dataServerData, "Autostart");
		
		switch (server) {
		case "REMOVE": MainThreadFileUpdater.changeSetting(dataServerData, "Autostart", ""); output("Autostart cleared"); break;
		case "RETURN": if (temp == null) {output("No server on autostart");} else {output(temp);}; break;
		default: MainThreadFileUpdater.changeSetting(dataServerData, "Autostart", server);
				output("Server added to autostart: " + MainThreadFileUpdater.getSetting(dataServerData, "Autostart"));
		}
	}
	static void help(List<String> cmd) {
		//Not a string. This help menu specifically prints to the main console and therefore no need to do that.
		
		
		try {
			
			getSyntax(cmd.get(1));
			return;
			
		} catch (Exception e) {
			System.out.println("Type \"help <command>\" for more information on a specific command");
			System.out.println("List of commands:"
					+ "\ncreate"
					+ "\nstart"
					+ "\nautostart"
					+ "\nexit");
		}
		
	}
	static void getSyntax(String command) {
		
		switch (command) {
		case "create":
			System.out.println("Usage: \"create <server name>\""
					+ "\nCreates a new server with given name");
			break;
		case "start":
			System.out.println("Usage: \"start <server name>\""
					+ "\nStarts the server that has the given name");
			break;
		case "autostart":
			System.out.println("Usage: \"autostart <server name | \"RETURN\"| \"REMOVE\">\""
					+ "\nFor server name, Sets the server to be started upon opening of MCServerPal"
					+ "\nFor \"RETURN\", returns the current autostarted server"
					+ "\nFor \"REMOVE\", clears the autostart option for servers (no server will automatically start)");
			break;
		case "exit":
			System.out.println("Usage: \"exit\""
					+ "\nExits MCServerPal");
			break;
		default: output("Not a command");
		
		}
	}
	
	
}
