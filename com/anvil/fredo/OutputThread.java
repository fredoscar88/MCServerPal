package com.anvil.fredo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class OutputThread extends Thread implements Runnable {

	Thread t;
	String threadName;
	int serverID;
	BufferedReader br;
	BufferedWriter bw;
	OutputInterpret oi;
	
	
	public OutputThread(Process p, String name, int id) {
		
		br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		serverID = id;
		
		threadName = name;
	
	}
	
	public void run() {
		
		File OutputLog = new File("OutputLog.txt");	//Remember to copy this over into a backup when the end script is called
		
		try {
			oi = new OutputInterpret(serverID);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		try {
			bw = new BufferedWriter(new FileWriter(OutputLog));
			System.out.println("bw is now writing to OutputLog");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String output;
		
		try {
			while (!t/*.currentThread()*/.isInterrupted() && ((output = br.readLine()) != null)) {
				System.out.println(output);
				oi.Interpret(output);
				
				try {
					bw.write(output);
					bw.newLine();
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
	
			}
			
			System.out.println("OutputThread termination message ('try' success)");
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("OutputThread termination message ('try' failure)");
		}
		System.out.println("OutputThread termination message. This thread should close! And be the last one to close!"
				+ "\n Hell when any given server closes it should attempt to shutdown the output thread first!");
		
		return;
		
	}
	
	public void start( ) {
		System.out.println("Starting...");
		
		if (t==null) {
			t = new Thread (this, threadName);
			t.start();
		}
		
	}
	
	/*public void start() {
		
		if (t == null) {
			
			t = new Thread(this);
			t.start();
			
		}
		
	}*/
	
}
