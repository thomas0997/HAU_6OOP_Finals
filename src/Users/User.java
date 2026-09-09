package Users;

public class User {


    protected String userID;
    protected String userType;

    public User(String userID, String userType) {
        this.userID = userID;
        this.userType = userType;
    }
    
    public String getUserId() {
        return userID;
    }

    public void setUserId(String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public abstract void navigate();


    public abstract boolean aunthenticate(); 
>>>>>>> 21af9d9 (Hi)
}
