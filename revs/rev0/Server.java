import java.net.*;

public class Server{
    public static void main(String[] args) throws Exception{
        System.out.println("Opening server socket in port 9999");
        ServerSocket ss = new ServerSocket(9999);
        Socket s = ss.accept();
        System.out.println("Received connection from: "+ s.getInetAddress().toString());
        Link connection = new Link(s);
        Thread.sleep(5000);
        System.out.println("Received messages:");
        for (String string : connection.messageQueue) {
            System.out.print("Message received:");
            System.out.print(string);
            System.out.println();
        }
        connection.output.Write("Message numer one");
        connection.output.Write("Message numer two");
        connection.output.Write("Message numer three");
        connection.Close();
        ss.close();
    }
}