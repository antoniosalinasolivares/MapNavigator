import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread{
    public static void main(String[] args){
        Server.users = Server.LoadUsers();
        try {
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("En que puerto quieres inicializar el servidor");
            System.out.print(":");
            int port = Integer.parseInt(keyboard.readLine());
            
            System.out.println("Abriendo servidor en la direccion: " + Inet4Address.getLocalHost().getHostAddress() + "\npuerto: " + port);
            ServerSocket ss = new ServerSocket(port);

            while(true){
                System.out.println("Esperando conexion . . .");
                Server s = new Server(ss.accept());
                System.out.println("Conexion recibida desde: "+ s.connection.socket.getInetAddress());
                s.start();
            }
        } catch (Exception e) {
            System.out.println("Error en ejecucion");
        }

        
    }

    static List<User> users = new ArrayList<User>();

    //Fields
    Link connection;
    User user;
    Socket s;

    public Server(Socket s) throws Exception{
        this.connection = new Link(s);
        this.user = null;
        this.s = s;
        
    }

    public void run(){
        Message msg;
        User usr = null;
        try{
            while(this.user == null){
                //Sends a message to the client indicating to send a username and password
                this.connection.output.Send(new Message("login","Please login or create a new user"));
                
                //Waits for a message response to arrive
                msg = this.connection.input.readMessage();

                //creates a new user based on the receoved information
                usr = new User(msg.value, msg.value2);


                boolean exists = false;
                //Checks if the username exists in the users list
                for (User user : Server.users) {
                    exists = exists || user.username.equals(usr.username);
                }

                //if it exists, checks if the login is correct (username and passwords match)
                if(exists){
                    System.out.println("El username existe, checando si la contraseña es correcta");
                    for (User user : Server.users) {
                       if(user.equals(usr)){
                           
                            //if it finds a match, it sends a message indicating a good login, sets the objects user to the new one
                           this.user = user;
                           break;
                       }
                    }
                    //If it does not find a match, it sends a message indicating a bad login, try again
                    if(this.user == null){
                        System.out.println("la contraseña no es correcta");
                        this.connection.output.Send(new Message("badlogin","bad password"));
                    }
                }

                //if the user does not exist, it creates a new one with the data provided, sends back a good login message and sets the user to the current one
                else{
                    System.out.println("El usuario no existe, agregando al usuario");
                    Server.users.add(usr);
                    System.out.println(Server.users);
                    Server.SaveUsers(Server.users);
                    this.user = usr;
                }
            }

            //reports to the screen the new login
            System.out.println("Login successfull from: "+ this.s.getInetAddress() + " user: " + this.user.username);
            this.connection.output.Send(new Message("goodLogin",usr.username));
            boolean continueService = true;

            //it starts an infinite look until it receives a message that indicates that the service is over
            while(continueService){
                msg = this.connection.input.readMessage();
                switch (msg.tag) {
                    case "search":
                        switch (msg.value){
                            case "close":
                                this.connection.output.Send(new Message("","Sitios ordenados por cercanía:\n1)Escuela \n2)Departamentos\n3)Plaza\n4)Billar\n5)tacos\n6)banco\n7)zapatería"));
                                break;
                            case "concurrence":
                                this.connection.output.Send(new Message("","Sitios ordenados por concurrencia:\n1)Plaza \n2)banco \n3)tacos\n4)Zapateria \n5)Escuela\n6)billar\n7)Departamntos"));
                                break;
                            case "vigilance":
                                this.connection.output.Send(new Message("","Sitios ordenados por vigilancia:\n1)Banco \n2)Plaza\n3)Zapatería\n4)Escuela\n5)billar\n6)tacos\n7)Departamentos"));
                                break;
                        
                            default:
                                this.connection.output.Send(new Message("",""));
                                break;
                        }

                        if(msg.value2.equals("true")){
                            System.out.println("Guardando busqueda de: "+ this.user.username);
                            this.user.searches = this.user.searches + msg.value + "\t";
                            System.out.println(this.user.username + "ha buscado:" + this.user.searches);
                            SaveUsers(Server.users);
                        }

                        break;


                    case "previousSearch":
                        this.connection.output.Send(new Message("tag", this.user.searches));
                        
                        break;
                    case "exit":
                        this.connection.close();
                        System.out.println("cerrando conexion con: " + usr.username);
                        continueService = false;
                        break;
                    default:
                        break;
                }
            }


        } catch (Exception e){

        }
    }

    public static synchronized void SaveUsers(List<User> users){
        try {
            FileWriter csvWriter = new FileWriter("users.csv", false);
            for (User user : users) {
                System.out.println("Dumping "+ user.username + " into a file");
                csvWriter.append(user.username);
                csvWriter.append(",");
                csvWriter.append(user.password);
                csvWriter.append(",");
                csvWriter.append(user.searches);
                csvWriter.append("\n");
            }
            csvWriter.close();


        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public static synchronized List<User> LoadUsers(){
        List<User> users = new ArrayList<User>();
        String row;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("users.csv"));
            while ((row = csvReader.readLine()) != null) {
                System.out.println("Leyendo: " + row + "del archivo");
                String[] data = row.split(",");
                users.add(new User(data[0],data[1],data[2]));
            }
            csvReader.close();
            return users;

        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
    }

}