package dm2.hw2.proj2.kmeans;

public class Location {
	public double x;
    public double y;
    public String location;
    public int category;//��¼�����ĸ����

    public Location(double x, double y, int category) {
        this.x = x;
        this.y = y;
        this.category = category;
    }
    
    public Location(double x, double y, String location, int category) {
        this.x = x;
        this.y = y;
        this.location = location;
        this.category = category;
    }

    public Location() {
    }

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }
}  