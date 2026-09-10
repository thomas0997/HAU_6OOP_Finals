package Users;


public class Visitor extends User {
    private String purposeOfVisit, typeOfID;

    public Visitor (String userid, String usertype, String purposeOfVisit, String typeOfID) {
        super(userid, usertype);
        this.purposeOfVisit = purposeOfVisit;
        this.typeOfID = typeOfID;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public String getTypeOfID() {
        return typeOfID;
    }

    @Override 
    public void navigate() {
        System.out.println("Visitor access: public locations onl");
    }

    @Override 
    public boolean authenticate(){
        return true;
    }


    
}
