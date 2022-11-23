//Name: Lishan Gao
//NetID: lgao14
//This is the major class that build up the map (or graph). 

import java.util.Hashtable;
import java.io.*;
import java.util.PriorityQueue;
import java.util.LinkedList;

public class Graph {
	Hashtable<String,Node> nodes = new Hashtable<String,Node>();
	LinkedList<Node> finalPath = null;
	
	//Created a graph object that can read the data file, separate the data, and
	//  connect locations together. Store each point(location) or edge into the hashtable nodes.
	public Graph(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    
		    while ((line = br.readLine()) != null) {
		    	//split by the :, no matter how long the space is or what format the space is, make them as array
		    	String[] l = line.split("\\s+"); 
		    	
		    	/*If the intersection start with "i", the first array is name,
		    	 * the second array is latitude, and the third is longitude.
		    	 */
		    	if(l[0].equals("i")) {
		    		String name = l[1];
		    		double lat = Double.valueOf(l[2]);
		    		double lo = Double.valueOf(l[3]);
		    		
		    		/* Maps the specified key to the specified value in this hashtable. 
		    		 * Neither the key nor the value can be null.
		    		 */
		    		nodes.put(name, new Node(name,lat,lo));
		    	}
		    	//If the data start with "r", it means roads.
		    	else {
		    		/*Store the name in the road as an array[1], then Intersection1ID as
		    		 * the second element of the array, Intersection2ID as the third element
		    		 * of the array.
		    		 */
		    		String name = l[1];
		    		Node n1 = nodes.get(l[2]);
		    		Node n2 = nodes.get(l[3]);
		    		double weight = Edge.calculateDist(n1, n2); //Calculated the distance between two nodes(locations).
		    		n1.addEdge(new Edge(name, n2, weight)); //Put n2 into n1's adjacent list
		    		n2.addEdge(new Edge(name, n1, weight)); //Put n1 into n2's adjacent list
		    	}
		    }
		    br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//Use Dijkstra's algorithm to find the shortest path:
	@SuppressWarnings("unchecked")
	public LinkedList<Node> findShortestPath(String start, String end) {
		Node startN = nodes.get(start);
		Node endN = nodes.get(end);
		
		PriorityQueue<Node> p = new PriorityQueue<Node>();
		
		//Labeled the start node's distance as 0 because the distance of 
		//  start node to itself is 0.
		startN.distance = 0;
		p.add(startN);
		//Make sure the priorityQueue has things to pop out, then go into the while loop.
		while(!p.isEmpty()) {
			//Each time pop the first element of the priorityQueue.
			Node first = p.poll();
			
			//If the first node is the end node, which means the shortest path hsa found out and get out of the while loop.
			if(first == endN) {
				endN.isVisited = true;
				break;
			}
			//Otherwise, it is not reach end node yet.
			//Go through each edge for each node:
			for(Edge i: first.e) {
				//i.connect is the neighbor or adjacent node of the first node, so I assigned it as node adj.
				Node adj = i.connect;
				
				/*Two situations: if the adjacent node distance is infinite, this means the node did not visit yet and 
				 * it is not in the priority queue yet. 
				 */
				if(Double.isInfinite(adj.distance)) {
					//The distance of the adjacent node is equal to the first node's distance plus the weight of each edge:
					adj.distance = first.distance + i.weight;
					adj.parent = first;
					//add the new adj node to the priority queue
					p.add(adj);
					
				}
				
				/*However, if the distance is not visited (means the shortest path node has not found yet) and not infinite, 
				 * the distance of the first node and the weight of the edge is greater than the adj's distance,
				 *  which means the path is not the shortest path, so it need to be updated.
				 */
				else if(!adj.isVisited && adj.distance > first.distance + i.weight) {
					//First, remove the adj that is not the shortest distance in the priority queue.
					p.remove(adj);
					
					//updated newest distance 
					adj.distance = first.distance + i.weight;
					adj.parent = first;
					p.add(adj);
				}
			}
			//After everything finish, the process of finding out the shortest path has finished, so set the isVisited is true,
			// meaning all of the things in first node has been visited.
			first.isVisited = true;
		}
		
		/*After finishing the while loop, check the isVisited for the end node. If the end node did not visited,
		 * this means there is no path can go between this location to another location, then return null.
		 */
		if(!endN.isVisited) {
			System.out.println("There is no path between these two locations! Please use another locations.");
			return null;
		}
		
		finalPath = new LinkedList<Node>();
		/*Set the current node as end node. Use the while loop to go through each node(location) that is in the path
		 * by tracking its parent all the way to the start node. Then, store those node (locations) into the linkedlist.
		 */
		Node current = endN;
		//O(n)
		while(current != null) {
			finalPath.addFirst(current);
			current = current.parent;
		}
		//Now, the finalPath linkedlist includes all of the nodes(locations) of the shortest path and return it.
		return (LinkedList<Node>) finalPath.clone();
	}
	
}
