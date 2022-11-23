//Name: Lishan Gao
//NetID: lgao14
//Website I used for the distance formula: https://en.wikipedia.org/wiki/Haversine_formula
/*This class is about the edge between two nodes (or locations). It includes the method that calculates the 
 * distance between two locations.
 */

public class Edge {
	String name;
	Node connect;
	double weight;
	public final static double R = 3958.8; //Radius of the earth
	
	public Edge(String n, Node c,double w) {
		name = n;
		connect = c;
		weight = w;
	}
	
	//I simplified the formula to calculate the distance: (detail in the README)
	public static double calculateDist(Node n1, Node n2) {
		double lat1 = n1.latitude/180*Math.PI;
		double lat2 = n2.latitude/180*Math.PI;
		double long1 = n1.longitude/180*Math.PI;
		double long2 = n2.longitude/180*Math.PI;
		double sqroot = Math.pow(Math.sin((lat2-lat1)/2),2)
				+(Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin((long2-long1)/2),2));
		return 2*R*Math.asin(Math.sqrt(sqroot));
	}
	
}
