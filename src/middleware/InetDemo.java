
package middleware;
  
import java.net.*; 

public class InetDemo{  
    public static void main(String[] args){  
        try{  
            InetAddress ip=InetAddress.getByName("www.javatpoint.com");  

            System.out.println("Host Name: "+ip.getHostName());  
            System.out.println("IP Address: "+ip.getHostAddress());  
        }catch(UnknownHostException e){
            System.out.println(e);
        }  
    }  
}  
