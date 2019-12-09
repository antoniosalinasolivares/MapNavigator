import java.net.*;

public class Client{
    public static void main(String[] args) throws Exception{
        System.out.println("Conectando a localhost en el puerto 9999");
        Socket s = new Socket("localhost", 9999);
        Link connection = new Link(s);
        System.out.println("Sending messages"); 
        connection.output.Send(new Message("First message","Acm1pt"));
        connection.output.Send(new Message("Second message","Acm2pt"));
        connection.output.Send(new Message("Third message","Acm3pt"));
        connection.output.Send(new Message("Fourth message","Acm4pt"));
        System.out.println("Waiting for incomming messages"); 
        Thread.sleep(12000);
        System.out.println("Printing received messages"); 
        for (Message msg : connection.input.queue) {
            System.out.print("Message received:");
            System.out.print(msg.toString());
            System.out.println();
        }
        
        connection.close();
        System.out.println("Connection is being ended!");
    }
}