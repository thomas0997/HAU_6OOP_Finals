package Users;


public class Visitor extends User {
    private String purposeOfVisit, typeOfID;

    public Visitor (String userid, String usertype, String purposeOfVisit, String typeOfID) {
        super(userid, usertype);
        this.purposeOfVisit = purposeOfVisit;
        this.typeOfID = typeOfID;
    }

    @Override 
    public void navigate() {
        System.out.println("Visitor access: public locations onl");
    }

    public boolean authenticate(){
        return true;
    }


    
}
