/*
 *           ClientProtocols Class
 *          Author: Jacob McKenzie
 */

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;

class ClientProtocols {

  DataInputStream dataInput;
  DataOutputStream dataOutput;

  BufferedReader bufferReader;

  Socket cSocket = null;
  String fileNames;

  ClientProtocols(Socket socket) throws Exception {
    this.cSocket = socket;
    dataInput = new DataInputStream(cSocket.getInputStream()); // Reads data
    dataOutput = new DataOutputStream(cSocket.getOutputStream()); // Writes data
    bufferReader = new BufferedReader(new InputStreamReader(System.in)); // Reads from input stream
  } // constructor end

    void userOptions() throws Exception {
      while(true) {
        System.out.println("***USER MENU***");
        System.out.println("1. Download File");
        System.out.println("2. Exit program");

        System.out.println("\nInput one of the numbers \nto make your selection: ");
        int option;
        option = Integer.parseInt(bufferReader.readLine());
        if(option==1) {
          dataOutput.writeUTF("GET");
          DownloadFile();
        }
        else {
          dataOutput.writeUTF("DISCONNECT");
          System.exit(1);
        } // else end
      } // while end
    } // userOptions

  void DownloadFile() throws Exception {

    int i = 0;
		int j = 0;

    String serverMessage = dataInput.readUTF(); // Reads String

    if(serverMessage.compareTo("File Not Found")==0) {
      System.out.println("File does not exist.");
			return;
    } else if(serverMessage.compareTo("Object Sending")==0) {

      // Input & output stream for file
      ObjectInputStream inputStream = new ObjectInputStream(dataInput);
      ObjectOutputStream outputStream = new ObjectOutputStream(dataOutput);

      List<File> elementList = (List<File>) inputStream.readObject();

      for (File element : elementList) {
        System.out.println(i + ". " + elementList.get(i));
        i++;
      } // for end

      j = i;

      // Prompts user and takes input
      String Selection;
      System.out.println("\nMake a selection");
      Selection = bufferReader.readLine();

      int userInput = Integer.parseInt(Selection);

      if(userInput >= 0 && userInput <= (j - 1)) {

        if (elementList.get(userInput).isDirectory()) {
          outputStream.writeObject(elementList.get(userInput));
          DownloadFile();
          return;
        } else {
          outputStream.writeObject(elementList.get(userInput));
          fileNames = elementList.get(userInput).toString(); // Converts input to string
          DownloadFile();
          return;
        } // else
      } else {
          System.out.println("Index out of range...");
        } // else
        return;
      } // if end

      else if(serverMessage.compareTo("READY")==0) {
        System.out.println("Name your file and the download will commence...");
        fileNames = "downloads/" + bufferReader.readLine();
        System.out.println("File is being downloaded...");

        File currentDownload = new File(fileNames);

        if(currentDownload.exists()) { // tests the existence of the file
          String action;
          System.out.println("File already exists.  Would you like to overwrite? (Y/N)");
          action = bufferReader.readLine();

          if(action == "N") {
            dataOutput.flush();
            return;
          } // if end
        } // if end
        FileOutputStream fileSent = new FileOutputStream(currentDownload);
        int holder;
        String temporary;
        do {  // Converting
          temporary = dataInput.readUTF();
          holder = Integer.parseInt(temporary);
          if (holder != -1){
            fileSent.write(holder);
          } // if end
        } while(holder != -1);
        fileSent.close();
        System.out.print(dataInput.readUTF());
      } // else if end
  } // DownloadFile end

} // ClientTransfer end
