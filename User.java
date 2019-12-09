
public class User{
    String username;
    String password;
    String searches;
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.searches = " ";
    }

    public User(String username, String password, String searches){
        this.username = username;
        this.password = password;
        this.searches = searches; 
    }

    public boolean equals(User user){
        return this.username.equals(user.username) && this.password.equals(user.password);
    }

    public boolean equals(String username){
        return this.username == username;
    }
}