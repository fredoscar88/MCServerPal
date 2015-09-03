package com.anvil.fredo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*	For some unknown reason, the torun.jar is looking at folders in the SCI folder, not the Server folder. I don't know why.
 * Like, I really don't know.. :V
 * 
 */

//Right I think we can store the servers in a List or Set, to reference 'em. I hope. Yeah we can!

public class Server {

	private static List<Object> serverList = new ArrayList<Object>();
	private static int NumberOfServers;
	boolean serverClassInitialized = false;
	
	Scanner CmdSender;
	static PrintWriter cmdSend;
	Process p;
	BufferedReader fileReader;
	
	int serverID;
	String serverName;
	
	File MCSPPlayers;
	File MCSPScripts;
	File MCSPServFolder;
	File dirServ;
	
	boolean running;
	static boolean[] Drunning; 
	
	/*
	 * There are lots of files that we might want to access. Each instance of Server will have them
	 * stored for that server. For example, server.properties will be declared as a file,
	 * probably ops.json, etc. That's why we need the servFolderPath, we'll use it as our base to find
	 * these things. Note that it doesn't lead straight to the directory where the files of the server
	 * are, it leads to that server's folder as declared by MCServerPal. To reach the directory of the
	 * server files it's just servFolderPath\Server. Simple.
	 */
	
	public Server(String servName, String servFolderPath) {
		
		//serverList.add(this);	//Adds this server to the list
		if (!serverClassInitialized) {
			serverList.add("Index 0. This is not a server.");
		}
		
		NumberOfServers++;
		serverID = NumberOfServers;	//This is fine and dandy until we start removing servers. We need to come up with a better way of assigning ids, because a server's id might not always be the next one to be given.
		this.serverName = servName;	//the this is included to indicate that it is a variable in the main class and not a method
		System.out.println(this);
		serverList.add(serverID, this);	//Adds this server to the list
		
		System.out.println(servFolderPath);
		
		dirServ = new File(servFolderPath);
		dirServ.mkdirs();
		MCSPPlayers= new File(servFolderPath + "Players");
		MCSPPlayers.mkdirs();
		MCSPScripts = new File(servFolderPath + "Scripts");
		MCSPScripts.mkdirs();
		MCSPServFolder = new File(servFolderPath + "Server");
		MCSPServFolder.mkdirs();
		
	}
	
	//Yo dawg, don't put everything into a constructor. We need to make the output method separate.
	public Server(int id) throws IOException, InterruptedException {
		
		serverList = new ArrayList<Object>();
		
		serverID = id;
		Drunning = new boolean[/*noOfServs*/1];
		Drunning[serverID] = true;
		
		CmdSender = new Scanner(System.in);
		
		
		//File Input = new File("Input.txt");
		//File Input2 = new File("Input2.txt");
		File Startup = new File("StartupScript.txt");
		
		
		String defaultJar = "torun.jar";
		File dir = new File("C:" + File.separator + "Workspace" + File.separator + "ECLIPSE" + File.separator + "MC" + File.separator + "SCI" + File.separator + "Server" + File.separator + "Test" + File.separator +defaultJar);
		
		ProcessBuilder server = new ProcessBuilder("java","-jar", dir.getAbsolutePath()/*,"nogui"*/);
		
		System.out.println("directory: " + server.directory()); //<(REMOVE)debug should be null
		
		File serverDir = new File("Server" + File.separator + "Test"); //I hope this just gets pushed onto the end of the current directory
		
		if (!serverDir.exists()) {serverDir.mkdirs(); System.out.println("Directory made!");}
		//Adds the directory, if it doesn't exist (this might not work)
		
		server.directory(serverDir);
		
		System.out.println("directory: " + server.directory());	//<(REMOVE)Debug
		
		System.out.println(server.environment());
		
		//server.redirectOutput(OutputLog);
		//server.redirectInput(Input);
		//server.redirectInput();
		
		try {
			p = server.start();
			
			/*BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));*/
			cmdSend = new PrintWriter(p.getOutputStream());
			
			//OutputStreamWriter osw = new OutputStreamWriter(p.getOutputStream());
			
			PrintWriter test2 = new PrintWriter(p.getOutputStream());
			
			String output;

			//Runs startup script. We'll move this into the scripts class once that exists. That class will
			//be able to execute any script file. for now it's a method. and not even that. I just can't deal with
			//hoping it works while I work on output interpreting.
			//Startup();
			if (Startup.exists()) {
				fileReader = new BufferedReader(new FileReader(Startup));
				
				while (Drunning[serverID]) {
					
					try {
						output = fileReader.readLine();
						
						if (output.startsWith("print")) {
							output = output.substring(5).trim();
							System.out.println(output);
						} else {
						
							test2.write(output);
							test2.write("\n");
							test2.flush();
						}
						
					} catch (NullPointerException e) {
						Drunning[serverID] = false;	//I don't know why this is here?
						System.out.println(Drunning[serverID] + " -Startup script executed, not sure why this is set to false");
					}
					
				}
				
			}
			
			OutputThread O1 = new OutputThread(p, "OutputT1", serverID);
			O1.start();
			
			Drunning[serverID] = true;
			
			while (Drunning[serverID]) {
				
				output = CmdSender.nextLine();
				
				if (output.equals("end")) {
					
					test2.write("stop");
					test2.write("\n");
					test2.flush();
					
					Drunning[serverID] = false;
					System.out.println("Server has closed, this program SHOULD terminate. (1)");
					
				} else {
					test2.write(output);
					test2.write("\n");
					test2.flush();
					
				}
				
				
				//osw.write(output);
			}
			
			
			/*String line;
			while((line = br.readLine()) != null){
			    System.out.println(line);
			}*/
			
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		System.out.println("Server has closed, this program SHOULD terminate. (2)");
	}
	
	
	
	//aghhh this is atrocious. Have it reference the ServerList[] and get the running from that!!!
	static public boolean getRunning(int id) {
		
		boolean tempBool;
		tempBool = ((Server)serverList.get(id)).running;	//<--- What we will eventually use
		return tempBool;
	}
	
	
	static public void sendCommand(String command) throws IOException {
		
		cmdSend.write(command);
		cmdSend.write("\n");
		cmdSend.flush();
		
	}
	
	public int getID() {
		return serverID;
	}
	static public int getID(String serverName) {
		int tempID = 0;
		boolean tempRun = true;
		System.out.println(serverList.size());
		
		for (int i = 1; (i < serverList.size()) && tempRun; i++) {
			
			System.out.println(((Server)serverList.get(i)).serverName);
			
			if (((Server)serverList.get(i)).serverName.equals(serverName)) {
				tempID = ((Server)serverList.get(i)).serverID;
			}
			
		}
		
		System.out.println("Error: That server does not exist");
		return 0;	//0 will never be an ID
		
		
		//return tempID;
	}
	static public int getNoOfServ() {
		return NumberOfServers;
	}
	
	static boolean doesExist() {
		
		return false;
	}
	
	/*public void Startup() {
		
		if (Startup.exists()) {
			fileReader = new BufferedReader(new FileReader(Startup));
			
			while (running[serverID]) {
				
				try {
					output = fileReader.readLine();
					
					if (output.startsWith("print")) {
						output = output.substring(5).trim();
						System.out.println(output);
					} else {
					
						test2.write(output);
						test2.write("\n");
						test2.flush();
					}
					
				} catch (NullPointerException e) {
					running[serverID] = false;
				}
				
			}
			
		}
	}*/
	
}
