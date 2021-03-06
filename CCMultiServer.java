package server;
/*

Main function file DOCUMENTATION

This is the file that contains the main function, not much to it other than that it forces an update of the 
Client based on whatever file lies in the path "/root/CCMultiServer/Client/src/client/ccBetaClient.java", and 
that it Starts a socket on a new CCMultiServerThread Thread.

You may notice a good bit of code commented out on line 29. This code was an attempt at creating an update capability 
that would allow for the Client to be in any area at any time, and as long as it was named CCBetaClient, the server would
automatically find the Client and update it, so you don't have to worry about ever changing the filePath variable. As
you can see, it was a failed attempt, but if you would like to work on it yourself and fix this, you can create a pull
request, and I would greatly appreciate the help.

*/

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class CCMultiServer {

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("Usage: java KKMultiServer <port number>");
			System.exit(1);
		}
		/*String pattern = "*.{KnockKnockClientBeta.java}";
		String theFile = "KnockKnockClientBeta.java";
		PathMatcher matcher = 
				FileSystems.getDefault().getPathMatcher("glob:" + pattern);
			Path filename = Paths.get(theFile);
			if (matcher.matches(filename)) {
			    System.out.println(filename);
			}
		*/

		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;
		String filePath = "/root/CCMultiServer/Client/src/client/ccBetaClient.java";
		Path startPath = Paths.get(filePath);
		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();) {
			Files.copy(startPath, clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			System.out.println("Welcome to ChatServer II2600");
			while (listening) {
				new CCMultiServerThread(serverSocket.accept()).start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
}
