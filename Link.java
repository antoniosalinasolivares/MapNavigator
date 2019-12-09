import java.io.*;
import java.net.*;
import java.util.*;

public class Link{
    Writer output;
    Reader input;
    Socket socket;

    public Link(String ip, int port) throws Exception{
        Socket s = new Socket(ip, port);
        this.output = new Writer(s);
        this.input = new Reader(s);
        this.socket = s;
    } 

    public Link(Socket s) throws Exception{
        this.output = new Writer(s);
        this.input = new Reader(s);
        this.socket = s;
    } 

    public void close() throws Exception{
        this.input.input.close();
        this.output.output.close();
        this.input.connected = false;
    }
}

class Writer{
    public final ObjectOutputStream output;
    public Writer(Socket s)throws Exception{
        this.output = new ObjectOutputStream(s.getOutputStream());
    }

    public void Send(Message msg) throws Exception{
        this.output.writeObject(msg);
    }
}



class Reader{

    public List<Message> queue;
    public final ObjectInputStream input;
    public Boolean connected; 
    public Reader(Socket s) throws Exception{
        this.queue = new ArrayList<Message>();
        this.input = new ObjectInputStream(s.getInputStream());
        this.connected = true;
    }

    public Message readMessage(){
        Message tmp = null;
        try {
            tmp = (Message) input.readObject();
            return tmp;
        } catch (Exception e) {
            System.out.println("Error trying to read from the connection");
        }
        return tmp;
    }
}

class Message implements Serializable{
    private static final long serialVersionUID = -1963340039952728946L;
    final String tag;
    final String value;
    final String value2;

    public Message(String tag, String value){
        this.tag = tag;
        this.value = value;
        this.value2 = "";
    }
    public Message(String tag, String value, String value2){
        this.tag = tag;
        this.value = value;
        this.value2 = value2;
    }

    public String getText(){
        return this.tag + this.value + this.value2;
    }

    public String toString(){
        String txt = this.tag + ": " + this.value;
        if(this.value2 == ""){
            txt += ", " + this.value2;
        } 
        return txt;
    }
}