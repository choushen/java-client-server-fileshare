/*
 *              RunServer Class
 *          Author: Jacob McKenzie
 */

public class RunServer {

  public static void main(String[] args) {

    System.out.println("Server is up!");

    Server run = new Server(8084); // Creates server object and assigns port

    new Thread(run).start(); // Initiates server object and starts threading
    try {
      Thread.sleep(20 * 1000);
    } catch(InterruptedException e) {
      e.printStackTrace();
    } // Exception handling end

    run.finish();

  } // main end

} // RunServer end
