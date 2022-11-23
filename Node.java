//Name: Lishan Gao
//NetID: lgao14
//This class is about the nodes (or location in the map).

import java.util.LinkedList;

public class Node implements Comparable<Node> {
	String name;
	double latitude;
	double longitude;
	LinkedList<Edge> e;
	double distance; //distance for each node
	Node parent;
	boolean isVisited;
	
	/* Initialized each variable. Then, labeled every node's distance as infinity.
	 * 
	 */
	public Node(String n, double la, double lo){
		name = n;
		latitude = la;
		longitude = lo;
		e = new LinkedList<Edge>();
		distance = Double.POSITIVE_INFINITY;
		parent = null;
		isVisited = false;
	}
	
	public void addEdge(Edge d) {
		e.add(d);
	}
	
	//Make the priorityQueue is comparable. If the distance is smaller, return -1. If the distance is bigger, 
	// return 1. Otherwise, return 0.
	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		if(distance < o.distance) {
			return -1;
		}
		else if(distance > o.distance) {
			return 1;
		}
		return 0;
	}
	
	
}
