# PathFinding-Following

This project aims to create a game environment using tile graphs. It was built in Java using the processing Library.
 
The folder structure is as follows:

* The first package is called **Behavior** and it contains 4 classes. Align and Smotion are to do with the character movement.
**ShortestPath** contains the algorithms needed for implementing both Dijkstra and A* to get the shortest path from start to goal vertex.
**Path Following** is the main class that runs everything. It sets up the environment and draws everything.

* The second package called **DataStructures** contains classes for different structures used in the behavior package.
Agent and GameObject are the character and targets. **Vertex** and **Edge** are used to generate the graph in the **Graph** class.
**ClassRoom** is basically the game environment I chose to implement, which is the class room where I'm taking this class.
**Obstacle** describes anything that the character is not allowed to go through (walls, tables, ..etc).

* The third package **Other** contains a **Helper** class to utility functions used everywhere in the project.


* There are some defaults in the working of the assignments that could be changed by changing the values of some parameters.
For example: the project by default uses A* with manhattan distance to get the shortest path.