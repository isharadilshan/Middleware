package middleware;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class RPCClient {

    private final PrintStream printStream;
    /*All characters printed by a PrintStream are converted
    into bytes using the platform’s default character encoding.
    The PrintWriter class should be used in situations that require
    writing characters rather than bytes. PrintStream never throws an IOException
    and flush automatically.*/
    
    public RPCClient(String ipAddress, int port) throws IOException {

        Socket rpcClient = new Socket(ipAddress, port);
        /* A socket is bound to a port number so that 
        the TCP layer can identify the application that data is destined to be sent to.*/

        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(rpcClient.getInputStream()));
                //get input stream from RPCServer 
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println("Server response : " + line);
                    System.out.print("\nCommands [add, sub, mul, div, mod, exit] : ");                    
                }
            } catch (IOException ex) {
                System.err.println("\nDisconnected!!");
                System.err.println(ex);
                System.exit(0);
            }
        }).start();

        printStream = new PrintStream(rpcClient.getOutputStream(), true);//send user input to RPCServer as output stream
        /* PrintStream(OutputStream out, boolean autoFlush)
        Creates a new print stream.*/
    }

    public void sendMessage(String operation) {
        
        Scanner scan = new Scanner(System.in);
        
        System.out.print("\nEnter 1st number : ");
        int f1 = scan.nextInt();

        System.out.print("Enter 2nd number : ");
        int s1 = scan.nextInt();

        printStream.println(operation + ":" + f1 + ":" + s1);

    }

    public static void main(String[] args) {

        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Enter server ip address : ");
            String ipAddress = scan.nextLine();

            System.out.print("Enter connection port : ");
            int port = scan.nextInt();

            RPCClient client = new RPCClient(ipAddress, port);
            System.out.println("\nConnected to server\n");

            System.out.print("Commands [add, sub, mul, div, mod, exit] : ");

            while (true) {
    
                scan = new Scanner(System.in);
    
                String command = scan.nextLine();

                if (command.equals("exit")) {
                    System.exit(0);
                }

                client.sendMessage(command);
    
                System.out.print("\n");

            }
            
        } catch (IOException ex) {
            
            System.err.println("\nUnable to connected!");
            System.err.println(ex);
        }

    }

}