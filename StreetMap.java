//Name: Lishan Gao
//NetID: lgao14
/* This class includes the main method that process the command line argument and show up the map by using
 * JFrame.
 */

import javax.swing.JFrame;
import java.awt.Color;
import java.util.LinkedList;

public class StreetMap {
	//Main method: 1. take command line argument 2. Dijkstra's algorithm 3. draw the graph(map)
	
	public static void main(String[] args) {
		//Command line argument:
		//java StreetMap ur.txt --show --directions HOYT MOREY // Showing both map and the directions
		
		LinkedList<Node> path = null;
		boolean show = false;
		Graph graph = new Graph(args[0]);
		for(int i=1;i<args.length;i++) {
			if(args[i].equals("--show")) {
				show = true;
			}
			else if(args[i].equals("--directions")) {
				path = graph.findShortestPath(args[i+1], args[i+2]);
			}
		}
		
		
		if(path != null) {
			Node current = path.pollFirst();
			System.out.print(current.name);
			while(!path.isEmpty()) {
				current = path.pollFirst();
				System.out.print(" -> " + current.name);
			}
			System.out.println("\nThe total distance is " + String.valueOf(current.distance) + " miles");
			
		}
		
		if(show) {
			JFrame frame = new JFrame("MapDraw");
			frame.getContentPane().setBackground(Color.WHITE);
			
			MapDraw component = new MapDraw(graph);
			frame.add(component);
			
			frame.setSize(800,600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		
	}
}
