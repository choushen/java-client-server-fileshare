/*
 *           ServerProtocols Class
 *          Author: Jacob McKenzie
 */

import java.net.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import java.lang.*;

 class ServerProtocols implements Runnable {

   DataInputStream dataInput;
   DataOutputStream dataOutput;
   File serverDirectory = new File(".");  // sets directory to current
   Socket cSocket = null;

   ServerProtocols(Socket socket) {
       this.cSocket = socket;
   } // constructor end

   private static List<File> generateList(File directory) {
   	File[] filesList = directory.listFiles();
   	List<File> currentDirectory = new ArrayList<File>();
   	for (File object : filesList) {
   		if (object.isDirectory()) {
   			currentDirectory.add(object);
   			generateList(object);
   		} else {
   			currentDirectory.add( object );
   		} // else
   	} // for
     return (currentDirectory);
   } // generateList

   void uploadToClient() throws Exception {

     List<File> elementList = generateList(serverDirectory); // Creates the file list

     dataOutput.writeUTF("Object Sending");

     ObjectOutputStream outputStream = new ObjectOutputStream(dataOutput); // Input stream for file

     outputStream.writeObject(elementList);

     ObjectInputStream inputStream = new ObjectInputStream(dataInput); // Input stream for file

     File tempFile = (File) inputStream.readObject(); // Stores input

     if(tempFile.isDirectory()) {
       serverDirectory = tempFile; // Updates directory
       uploadToClient(); // Passes list to client recursively
       return;
     } // if end

     if(!tempFile.exists()) {
       dataOutput.writeUTF("File Not Found");
       return;
     } else {
       dataOutput.writeUTF("READY");
       FileInputStream clientFile = new FileInputStream(tempFile);
       int holder;
       do {
         holder = clientFile.read();
         dataOutput.writeUTF(String.valueOf(holder));
       } while(holder != -1);
       clientFile.close();
       dataOutput.writeUTF("File successfully saved to downloads folder.");
     }

   } // uploadToClient


   public void run() {
     try {
       dataInput = new DataInputStream(cSocket.getInputStream()); // Reads data
       dataOutput = new DataOutputStream(cSocket.getOutputStream()); // Writes data
       System.out.println("Connection with client has been established...");
     } catch(Exception ex) {
       System.out.println("Error...");
     } // try & catch end

     while(true) {
       try {
         System.out.println("Awaiting instruction...");
         String instructions = dataInput.readUTF(); // Taking user instruction
         if(instructions.compareTo("GET")==0) {
           System.out.println("Instruction received...\nFile Sending...");
           uploadToClient(); // Sends the file to the client
           continue;
         } // if end
         else if(instructions.compareTo("DISCONNECT")==0) {
           System.out.println("Instruction received...\nTerminating...");
           System.exit(1);
         } // else if
       } catch(Exception ex) {
         System.out.println("Error...");
       } // try & catch end
     } // while end
   } // run end

 } // ServerProtocols end
