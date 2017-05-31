Shortest Paths in Networks 04/23/2017
Programming language: Java 
Compiler version: javac 1.8.0_121	

Description:
------------
This project involves the implementation of Dijkstra's algorithm. Minimum Binary Heap data structure is used to implement the queue used in Dijkstra's algorithm to find shortest path to route network packets from one router to another, situated in different buildings in UNCC campus.

Design:
Minimum Binary Heap class is used to generate and maintain a heap structure. It supports the operations:
->isEmpty()
->isfull()
->parent()
->nthChild()
->insert()
->findmin()
->deletemin()
->delete()
->heapifyUp()
->heapifyDown()
->minChild()
->printHeap()
->returnIndex()
->update()

Vertex, Edge and Graph are implemented in different class files. Import all the classes into the project package and then execute.

Graph class implements methods to addedge(), printPath(), getVertex(), clearAll(), unweighted(), alterEdge(), alterVertex(), onInputError().

The unweighted() method is used to implement BFS algorithm to check the reachable vertices in the graph from all the vertices to other vertices.


How to Run the Program:
-----------------------

Compile: javac Graph.java Vertex.java Edge.java MinimumHeap.java

After compiling run the following:

java Graph [PATH_TO_networks.txt_FILE] [PATH_TO_queries.txt_FILE] [PATH_TO_output.txt_FILE]

Example: java graph D:\Algos\Project2\src\network.txt D:\Algos\Project2\src\queries.txt D:\Algos\Project2\src\output.txt

Make sure the queries are in the following format for proper execution:
1. addedge tailvertex headvertex transmit-time
2. deleteedge tailvertex headvertex
3. edgedown tailvertex headvertex
4. edgeup tailvertex headvertex
5. vertexdown vertex
6. vertexup vertex
7. path from_vertex to_vertex
8. print
9. reachable
10. quit



Limitations:
------------
An additional step to sort the final output will bring down the running time of the entire program. However when the shortest path algorithm implementation is considered it has the expected running time.

Contact Information:
--------------------

Name: Sharath Chandra Bagur Suryanarayanaprasad
Student ID: 800974802
EmailID: sbagursu@uncc.edu