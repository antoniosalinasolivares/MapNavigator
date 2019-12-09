import java.net.*;
import java.io.*;
import java.util.*;

public class Link{
    Socket connection = null;
    Boolean isConnected = false;
    Writer output;
    Reader input;
    List<String> messageQueue = null;

    public Link(Socket socket){
        try {
            this.messageQueue = new ArrayList<String>();
            this.connection = socket;
            this.output = new Writer(this);
            this.input = new Reader(this);
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host");
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to");
        }catch (Exception e) {
            System.out.println("");
        }
    }
    
    public Link(String ip, int port){
        try {
            this.messageQueue = new ArrayList<String>();
            this.connection = new Socket(ip, port);
            this.output = new Writer(this);
            this.input = new Reader(this);
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host");
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to");
        }catch (Exception e) {
            System.out.println("");
        }
    }

    public void Close() throws Exception{
        this.output.output.close();
        this.input.close();
        this.connection.close();
    }
}

class Writer{
    DataOutputStream  output = null;
    Link client = null;
    public Writer(Link client) throws Exception{
        this.client = client;
        this.output = new DataOutputStream(this.client.connection.getOutputStream());
    }

    void Write(String message)throws Exception{
        output.writeUTF("Hello from the other side!");
        output.flush();
    }
}

class Reader extends Thread{
    DataInputStream input = null;
    Link client = null;


    public Reader(Link client) throws Exception{
        this.input = new DataInputStream(this.client.connection.getInputStream());
        this.client = client;
        this.run();
    }

    public void run(){
        String temp = "";
        while(client.isConnected){
            temp = readMessage();
            if(!temp.equals("")){
                client.messageQueue.add(temp);
            }
            else{
                temp = "";
            }
        }
        close();
    }

    public void close(){
        try {
            input.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    private String readMessage(){
        try {
            return this.input.readUTF();
        } catch (Exception e) {
            return "";
        }
    }
}