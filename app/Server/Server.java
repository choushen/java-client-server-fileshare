/*
 *              Server Class
 *          Author: Jacob McKenzie
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server implements Runnable {

  // Server variables
  protected int serverPort = 8080; // Assigning port number
  protected ServerSocket serverSocket = null; // Setting socket default value to null
  protected boolean isStopped = false;
  protected Thread activeThread= null; // Settings thread default to null
  protected ExecutorService threadPool = Executors.newFixedThreadPool(10); // The number of threads being run

  Server(int port) {
    this.serverPort = port;
  } // constructor end

  public void run() {
    synchronized(this) {
      this.activeThread = Thread.currentThread();
    } // prevent thread interference

    try {
      File outFile = new File("ConsoleLog.txt"); //Your file
      FileOutputStream outStream = new FileOutputStream(outFile);
      PrintStream consoleLog = new PrintStream(outStream);
      System.setOut(consoleLog);
      } catch (FileNotFoundException e) {
			e.printStackTrace(System.err);
		} // try and catch end


    openServerSocket();

    while(isStopped() == false) {
      Socket clientSocket = null;
      try {
        clientSocket = this.serverSocket.accept(); // Waits, then accepts the client socket connection
      } catch(IOException e) {
        if(isStopped()) {
          System.out.println("Server has terminated.");
          break;
        } // if
        throw new RuntimeException("Client is unable to connect", e);
      } // try & catch end
      this.threadPool.execute(new ServerProtocols(clientSocket));
    } // while end
    this.threadPool.shutdown(); // executes submitted tasks before terminating
    System.out.println("Server Stopped.");
  } // run end

  boolean isStopped() {
    return this.isStopped;
  } // isStopped() end

  void finish () {
    this.isStopped = true;
    try {
      this.serverSocket.close(); // Closes the server socket
    } catch(IOException e) {
      throw new RuntimeException("Server socket could not be closed.", e);
    } // try and catch end
  } // stop end

  void openServerSocket() {
    try {
      this.serverSocket = new ServerSocket(this.serverPort);
    } catch (IOException e) {
      throw new RuntimeException("Cannot access port!", e); // If cannot open port
    } // try & catch end
  } // openServerSocket end
} // Server class end
