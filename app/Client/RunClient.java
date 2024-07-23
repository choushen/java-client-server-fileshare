/*
 *              Client Class
 *          Author: Jacob McKenzie
 */

import java.net.Socket;

public class RunClient {

  public static void main(String[] args) throws Exception {

  Socket clientSocket = new Socket("localhost", 8084); // Creates a client socket

  ClientProtocols protocols = new ClientProtocols(clientSocket); // adds client socket

  protocols.userOptions(); // displays user menu

  } // main end

} // RunClient end
