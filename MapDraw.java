//Name: Lishan Gao
//NetID: lgao14
/*This is the class that set up the JPanel of the map. It includes drawing the map and the shortest path between
 * two locations.
 */

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.util.LinkedList;

public class MapDraw extends JPanel{
	Graph graph;
	double min_lat;
	double max_lat;
	double min_lon;
	double max_lon;
	
	//Constructor:
	public MapDraw(Graph graph) {
		this.graph = graph;
		
		/*The minimum value of latitude and longitude will only go to the larger number, so I set them as positive
		 * infinity. Then, for the maximum latitude and longitude, I set them as negative infinite becasue it can only 
		 * go to the lower number.
		 */
		min_lat = Double.POSITIVE_INFINITY;
		min_lon = Double.POSITIVE_INFINITY;
		max_lat = Double.NEGATIVE_INFINITY;
		max_lon = Double.NEGATIVE_INFINITY;
		
		//Go through the nodes in the graph and find out the maximum latitude and longitude as well as the
		//  minimum latitude and longitude.
		//  (graph.nodes is hashtable)		
		for(Node node: graph.nodes.values()) {
			min_lat = Math.min(min_lat, node.latitude);
			min_lon = Math.min(min_lon, node.longitude);
			max_lat = Math.max(max_lat, node.latitude);
			max_lon = Math.max(max_lon, node.longitude);
		}
			
	}
	
	/* The latitude will become larger when going from the right to the left in the panel.
	 * The longitude will become smaller when going from the bottom to the top in the panel.
	 */
	public void paintComponent(Graphics g){
	//First, draw the map:
		super.paintComponent(g);
		//Create variables for the width, height, and scale of the canvas.
		int width = getWidth();
		int height = getHeight();
		
		//1 degree latitude or 1 degree longitude corresponding to 1 pixel.(a pixel is a square)
		//I subtracted 5 just to make the entire map can present and nothing missing.
		double scale = Math.min(width/(max_lon-min_lon), height/(max_lat-min_lat))-5;
		
		//Define the center point as the center of the canvas. 
		double lon_center = (max_lon + min_lon)/2;
		double lat_center = (min_lat + max_lat)/2;
		 
		//Go through nodes and neighbors 
		for(Node node: graph.nodes.values()) {
			for(Edge i: node.e) {
				Node adj = i.connect; //This is the adjacent (neighbor) node
				
				//Draw line from node to adj:
				// Change the longitude and latitude into points in the canvas. I need two points in order to draw a line.
				// Since I defined the center point above, if I want to access each point, I can use the formula below 
				// to access each point based on the center.
				int x1 = (int) (width/2-(lon_center-node.longitude)*scale);
				int x2 = (int) (width/2-(lon_center-adj.longitude)*scale);
				int y1 = (int) (height/2-(node.latitude-lat_center)*scale);
				int y2 = (int) (height/2-(adj.latitude-lat_center)*scale);
				g.drawLine(x1,y1,x2,y2);
			}
		}
		
		
	//draw the shortest path:
		//Use 2D graphic to draw the shortest path between two locations: I labeled them as red color
		// and set basicStroke as 3.0f.
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3.0f));
		
		/*If the finalPath linkedlist is not empty, finish the following stuffs: 
		 * 1. copy the finalPath linkedlist object as path.
		 * 2. Use the copied linkedlist to draw line based on two locations((x1,y1),(x2,y2)), and set the pre node as current.
		 */
		if(graph.finalPath != null) {
			@SuppressWarnings("unchecked")
			LinkedList<Node> path = (LinkedList<Node>) graph.finalPath.clone();
			Node pre = path.pollFirst();
			while(!path.isEmpty()) {
				Node current = path.pollFirst();
				int x1 = (int) (width/2-(lon_center-pre.longitude)*scale);
				int x2 = (int) (width/2-(lon_center-current.longitude)*scale);
				int y1 = (int) (height/2-(pre.latitude-lat_center)*scale);
				int y2 = (int) (height/2-(current.latitude-lat_center)*scale);
				
				g2.drawLine(x1,y1,x2,y2);
				
				pre = current;
			}
		}
		
	}
}
