package Users;

public abstract class User {
    private String userid, usertype, name;
    public static int nextID = 1;

    public User(String usertype, String name) {
        this.userid = "U" + String.format("%03d", nextID++);
        this.usertype = usertype;
        this.name = name;
    }
    public String getName(){ return name;}
    public String getUserID() { return userid; }
    public String getUserType() { return usertype; }

    public abstract void navigate();
    public abstract boolean authenticate();
}
