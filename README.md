## Street-Mapping

How I structured my project: 
There is total five classes in this project. 
First, the Edge class can include information about the edge’s name, edge’s weight, the nodes it connects to, and the method that calculate the distance between two locations. For the distance calculate, I simplified the formula to become easier to read (the simplify process shows below). 

Second, the Node class can store variables such as the node’s name (location’s name), the location’s latitude and longitude, the edge that the node connect to, the parent that the nodes connect to (this is what I came out later, so I added this later into the class), and check if the nodes have been visited (this is useful for the Dijkstra’s algorithm). For the constructor, I initialized everything and set the distance as positive infinity based on the Dijkstra’s algorithm. Also, set parent as null and isVisited is false, so after all nodes are visited, the variable isVisited can be set into true. There are also some methods that are included in this class, such as addEdge, which is adding edge into the LinkedList node because each node connects to many edges, and compareTo method, which is help to rank the distance of the priority queue. 

After that, I created a Graph class. The Graph class read in the data in the txt file by using the BufferReader. Then, I split lines that begins with “i” as array. The first element of the array is the name of the intersection, then the latitude, and the longitude. For every line that begins with “r”, the first element of the array is the road’s name, then the locations (I stored them as node n1 and node n2). Also, calculated the distance (or weight) between these two nodes, and assigned edges to them. For example, location n1 has an edge that connect with n2. I also used hashtable to store information about the intersection. There is one method in this class, findShortestPath. This method returns LinkedList type. First, put the start node into the PriorityQueue and set the distance as 0 (based on Dijkstra’s algorithm, set the start node itself to 0 at the beginning). Then, using a while loop to pop the elements in the PriorityQueue. For each node, goes through the edge to find its adjacent nodes and updates the distance if it is a shorter distance than the previous one. This process continues working for all the nodes until the end node reach. Also, add and updated those nodes into the PriorityQueue. After the while loop, I checked if there is such path exist between those two locations. If yes, return the LinkedList that has all nodes (all locations that in this path). Remember I used parent to store each node’s parent, so I refer to their parents to get all the nodes from two nodes.

Fourth, the MapDraw set up the JPanel that draws the map and the shortest path out. I set the minimum latitude and longitude as positive infinity because it will only go bigger if it is the minimum. And set the maximum latitude and longitude as negative infinity because it will only go lower if it is the maximum. For the paintComponent in this class, I set up the line of the map by using the center. The center point is ((maximum longitude + minimum longitude)/2, (minimum latitude + maximum latitude)/2). All other points can be found based on the center point. Then, the second part is to change the lines by using the color and set a stroke. I used 2D Graphics to make the line red and make it thicker. And then the similar process as above (located the point (location) based on the center point). After finishing this class, the basic map and highlight the shortest path have been done.

Finally, the StreetMap class took the command line argument. If user want to show the map, draw the map out. If user want to show the direction between two locations, print out the shortest path of two locations that user enters. 

Java library that I implemented:
For graphic, I implemented: 
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
For building the graph, I implemented PriorityQueue, LinkedList, and Hashtable.

Challenges I overcame:
When I was building the StreetMap class, I was not understand how to make the program accept the command line arguments in the pdf. At the beginning, I thought that is what the user type into the console. However, after I did some research and asked my tutor, this is not the input that user types, instead, this allows user to specify configuration information when the application is launched. When the user wants to show the map, I wrote a coding to draw the map out. If the user wants to see the whole shortest path that across two locations, just print them out.

Runtime Analysis:
n represents the number of nodes
m represents the number of edges 
Graph class:
constructor: read file -- file has n+m lines, so the time of creating one node and one edge is constant time, which is O(1). So for the constructor, the runtime is O(n+m).
findShortestPath:
Get element in the hash table is constant time O(1).
The first while loop: Each time when you poll something out of the priority queue, it takes time (logn). And then for the for loop, it goes through every node, so the runtime for the for loop is degree(V) * (logn) (logn: put into queue and take out). Therefore, the worst case runtime for this method is (logn) + degree(V) * (logn). 
 
So the total runtime for this class is still O(n+m).
MapDraw:
Constructor: O(n) because there is only one for loop goes though all the nodes.
paintComponent: outer loop goes through all V, each vertices takes deg(V) *O(1) to run. 
 
So the total runtime for this MapDraw class is O(n+m).
If we assume n<m, then the runtime will be o(n).
StreetMap:
There is one for loop run O(n) and one while loop also runs O(n). So this class will take O(n).


Test (some sample output): (I installed the java package and run it in the terminal)
Install java package jdk:
https://www.oracle.com/java/technologies/downloads/#jdk17-mac

Project3 % javac *.java 
Project3 % java StreetMap ur.txt --show --directions HOYT MOREY
HOYT -> i45 -> MOREY
The total distance is 0.04837840812215988 miles
 
Project3 % java StreetMap nys.txt --show --directions i2 i200
 

Project3 % java StreetMap ur.txt --show --directions HOYT MOREY
HOYT -> i45 -> MOREY
The total distance is 0.04837840812215988 miles 

MBP Project3 % java StreetMap ur.txt --show
MBP Project3 % java StreetMap ur.txt --directions HOYT MOREY
HOYT -> i45 -> MOREY
The total distance is 0.04837840812215988 miles





