public class Client{
    public static void main(String[] args) throws Exception{
        System.out.println("Conectando a localhost en el puerto 9999");
        Link connection = new Link("localhost", 9999);
        System.out.println("Sending messages"); 
        connection.output.Write("Message numer one");
        connection.output.Write("Message numer two");
        connection.output.Write("Message numer three");
        Thread.sleep(5000);
        System.out.println("Printing received messages"); 
        for (String string : connection.messageQueue) {
            System.out.print("Message received:");
            System.out.print(string);
        }
        connection.Close();
        System.out.println("Connection is being ended!");
    }
}