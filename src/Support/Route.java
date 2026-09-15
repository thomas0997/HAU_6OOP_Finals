package Support;

import Locations.CampusEntity;

public class Route  {
    private double totalDistance;
    private CampusEntity[] waypoints;

    public Route(CampusEntity[] waypoints){ this.waypoints = waypoints; }

    public void calculateRoute() throws NavigationException{
        if (waypoints == null || waypoints.length < 2){throw new LocationNotFoundException(Messages.msg[1]);}
        
        totalDistance = 0;
        for (int i = 0; i < waypoints.length - 1; i++) {
            double dx = waypoints[i+1].getX() - waypoints[i].getX();
            double dy = waypoints[i+1].getY() - waypoints[i].getY();
            totalDistance += Math.sqrt(dx*dx + dy*dy);
        }        
    }

    public double getTotalDistance(){ return totalDistance; }

}
