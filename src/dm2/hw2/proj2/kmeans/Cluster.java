package dm2.hw2.proj2.kmeans;

import java.util.ArrayList;

public class Cluster {
	private int center; 
    private ArrayList<Location> ofCluster = new ArrayList<Location>(); 
  
    public int getCenter() {  
        return center;  
    }  
  
    public void setCenter(int center) {  
        this.center = center;  
    }  
  
    public ArrayList<Location> getOfCluster() {  
        return ofCluster;  
    }  
  
    public void setOfCluster(ArrayList<Location> ofCluster) {  
        this.ofCluster = ofCluster;  
    }  
  
    public void addGeneral(Location location) {  
        if (!(this.ofCluster.contains(location)))  
            this.ofCluster.add(location);  
    }  
}
