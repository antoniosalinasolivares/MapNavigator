import java.util.*;
import java.net.*;
import java.io.*;

public class Client{
    public static void main(String[] args) throws Exception{
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Bienvenido a la guia turistica");
        System.out.println("Por favor introduce la direccion a la que te quieres conectar");
        System.out.print(":");
        String ip = keyboard.readLine();
        System.out.println("Por favor introduce el puerto");
        System.out.print(":"); 
        int port = Integer.parseInt(keyboard.readLine());
        Socket s = new Socket(ip, port);
        Link connection = new Link(s);
        Message msg;
        msg = connection.input.readMessage();
        User user = null;
        String username = "";
        String password = "";
        while (user == null){
            System.out.println("Por favor inicia sesion, si aun no tienes cuenta, ingresa tus datos de registro");
            System.out.println("Introduce tu nombre de usuario");
            System.out.print(":");
            username = keyboard.readLine();
            System.out.println("Introduce tu contraseña");
            System.out.print(":");
            password = keyboard.readLine();
            connection.output.Send(new Message("login",username, password));
            Thread.sleep(200);
            msg = connection.input.readMessage();
            if(msg.tag.equals("goodLogin")){
                user = new User(username, password);
                break;
            }else{
                System.out.println("Usuario o contraseña equívocos, inténtalo de nuevo");
            }
        }
        System.out.println("Welcome " + user.username);
        boolean keep = true;
        String election;
        while (keep) {
            printMap();
            System.out.println("Qué deseas hacer?");
            System.out.println("a) buscar");
            System.out.println("b) busquedas previas");
            System.out.println("c) salir");
            System.out.print(":");
            election = keyboard.readLine();
            switch (election) {
                case "a":
                    String parameter;
                    String save;
                    System.out.println("Con qué parámetro deseas buscar?");
                    System.out.println("a) Cercanía");
                    System.out.println("b) Concurrencia");
                    System.out.println("c) vigilancia");
                    System.out.print(":"); 
                    parameter = keyboard.readLine();
                    System.out.println("Deseas guardar la busqueda?");
                    System.out.println("a) Si");
                    System.out.println("b) No");
                    save = keyboard.readLine();
                    boolean keepSafe = save.equals("a");
                    
                    switch (parameter) {
                        case "a":
                            connection.output.Send(new Message("search","close", (keepSafe)?"true":"false"));
                            break;
                        case "b":
                            connection.output.Send(new Message("search","concurrence", (keepSafe)?"true":"false"));
                            break;
                        case "c":
                            connection.output.Send(new Message("search","vigilance", (keepSafe)?"true":"false"));
                            break;
                        default:
                            System.out.println("Eleccion no elegida");
                            break;
                    }

                    msg = connection.input.readMessage();
                    System.out.println("Resultados de la busqueda:");
                    System.out.println(msg.value);
                    break;

                case "b":
                    System.out.println("Busquedas previas:");
                    connection.output.Send(new Message("previousSearch",""));
                    msg = connection.input.readMessage();
                    System.out.println(msg.value);
                    break;

                case "c":
                    System.out.println("Saliendo");
                    connection.output.Send(new Message("exit", ""));
                    connection.close();
                    keep = false;            
                    break;
            
                default:
                    break;
            }
            keyboard.readLine();
        }
    }

    static public void printMap(){
        cls();
        System.out.println("+-------+   +---------+   +--------------+");
        System.out.println("|       |   |         |   |              |");
        System.out.println("| Banco |   |  Tacos  |   |   Zapateria  |");
        System.out.println("|       |   |         |   |              |");
        System.out.println("+-------+   +---------+   +--------------+");
        System.out.println("");
        System.out.println("");
        System.out.println("+---------------------+   +--------------+");
        System.out.println("|                     |   |              |");
        System.out.println("|  Plaza Comercial    |   |  Billar      |");
        System.out.println("|                     |   |              |");
        System.out.println("+---------------------+   +--------------+");
        System.out.println("");
        System.out.println("");
        System.out.println("+---------+   +-----+   +----------------+");
        System.out.println("|         |   |     |   |                |");
        System.out.println("| Escuela |   |Casa |   |  Departamentos |");
        System.out.println("|         |   |     |   |                |");
        System.out.println("+---------+   +-----+   +----------------+");
        System.out.println("");
    }

    static public void cls(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}