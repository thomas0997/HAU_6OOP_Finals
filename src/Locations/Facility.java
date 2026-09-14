// Source code is decompiled from a .class file using FernFlower decompiler (from IntelliJ IDEA).

package Locations;

public class Facility extends CampusEntity {
 
    private String facilityType;
 
    public Facility(String id, String name, String description, double x, double y,
                     String facilityType) {
        super(id, name, description, x, y);
        this.facilityType = facilityType;
    }
 
    public String getFacilityType() {
        return facilityType;
    }
    
    @Override
    public String getInfo() {
        return "[Facility] " + getName() + " │ Type: " + facilityType;
    }
}