package Users;

public abstract class User {
    private String userid;
    private String usertype;

    public User(String userid, String usertype) {
        this.userid = userid;
        this.usertype = usertype;
    }

    public abstract void navigate();

    public abstract boolean authenticate();
}
