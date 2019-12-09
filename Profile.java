import java.util.*;

public class Profile{

    User user;
    List<String> savedSearches;

    public Profile(User user){
        this.user  = user;
        this.savedSearches = new LinkedList<String>();
    }

    public Profile(User user, List<String> savedSearches){
        this.user = user;
        this.savedSearches = savedSearches;
    }

    public boolean addSearch(String search){
        return this.savedSearches.add(search);
    }
}