import java.net.*;
import java.util.*;
import java.io.*;

import opencsv.*;

public class Server extends Thread{
    CSVReader reader = new CSVReader(new FileReader("file.csv"),',', '\'',2);
    public static void main(String[] args){
        try {
            ServerSocket ss = new ServerSocket(9999);
            
            Socket s = null;
            while(true){
                s = ss.accept();
                System.out.println("Connexion aceptada desde: " + s.getInetAddress());
                new Server(s);
                s = null;
            }
        } catch (Exception e) {
            System.out.println("Some bad shit happened");
            System.out.println("Closing the server");
            return;
        }
    }

    //fields
    Link connection;
    Socket s;
    List<Message> queue;
    Boolean open;
    Boolean logged;

    public Server(Socket s) throws Exception{
        this.s = s;
        this.connection = new Link(s);
        this.queue = connection.queue;
        this.open = true;
        this.logged = false;
        this.start();
    }

    public void run() {
        try {
            Message tmp = null;
            this.connection.output.Send(new Message("Login","request"));
                while(open){
                    if(!queue.isEmpty()){
                        tmp = queue.get(0);
                        queue.remove(0);
                        this.Execute(tmp);
                    }
                }
        } catch (Exception e) {
            System.out.println("Hubo un error inesperdado");
        }
    }

    void Execute(Message msg){
        switch (msg.tag) {
            case "login":

                break;
            case "search":
                
                break;
            case "save":
                
                break;
            case "prevous":
                
                break;
            case "logout":
                
                break;
        
            default:
                break;
        }

    }
}

class User{

    String username;
    String password;
    
    public User(String username, String password){
       this.username = username;
       this.password = password;
       User.Users.add(this);
    }

    public boolean Equals(User user){
        return this.username == user.username && this.password == user.password;
    }

    public boolean Exists(User user){
        boolean exists = false;
        for (User u : User.Users) {
            exists = exists || user.username == u.username;
        }
        return exists;
    }

    static List<User> Users = new ArrayList<User>();

}

class Search{

}