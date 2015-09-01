package com.anvil.fredo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/*	For some unknown reason, the torun.jar is looking at folders in the SCI folder, not the Server folder. I don't know why.
 * Like, I really don't know.. :V
 * 
 */

//Right I think we can store the servers in a List or Set, to reference 'em. I hope. Yeah we can!

public class Server {

	private static List<Object> serverList;
	
	static void createServer(String serverName, int id) {
		//blah blah we do file i/o shit here
		//basically this method is called when we add in a new server. We need to work out how2do that
		
		
		
	}
	
	
	Scanner CmdSender;
	static PrintWriter cmdSend;
	Process p;
	BufferedReader fileReader;
	int serverID;
	
	static boolean[] running; 
	
	//Yo dawg, don't put everything into a constructor. We need to make the output method separate.
	public Server(int id) throws IOException, InterruptedException {
		
		serverID = id;
		running = new boolean[/*noOfServs*/1];
		running[serverID] = true;
		
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
						running[serverID] = false;	//I don't know why this is here?
						System.out.println(running[serverID] + " -Startup script executed, not sure why this is set to false");
					}
					
				}
				
			}
			
			OutputThread O1 = new OutputThread(p, "OutputT1", serverID);
			O1.start();
			
			running[serverID] = true;
			
			while (running[serverID]) {
				
				output = CmdSender.nextLine();
				
				if (output.equals("end")) {
					
					test2.write("stop");
					test2.write("\n");
					test2.flush();
					
					running[serverID] = false;
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
	
	public Server(String servName) {
		File server = new File(servName);	//LOL like this is a thing we need
		
	}
	
	
	static public boolean getRunning(int id) {
		
		return running[id];
	}
	
	
	static public void sendCommand(String command) throws IOException {
		
		cmdSend.write(command);
		cmdSend.write("\n");
		cmdSend.flush();
		
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
