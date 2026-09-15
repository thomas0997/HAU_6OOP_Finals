package Users;

public abstract class User {
    private String userid, usertype;
    public static int nextID = 1;

    public User(String usertype) {
        this.userid = "U" + String.format("%03d", nextID++);
        this.usertype = usertype;
    }

    public String getUserID() { return userid; }
    public String getUserType() { return usertype; }

    public abstract void navigate();
    public abstract boolean authenticate();
}
