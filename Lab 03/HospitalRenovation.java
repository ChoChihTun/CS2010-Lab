// Copy paste this Java Template and save it as "HospitalRenovation.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here: Refer to lecture note 6 on DFS and https://stackoverflow.com/questions/15873153/explanation-of-algorithm-for-finding-articulation-points-or-cut-vertices-of-a-gr on articulation point
// year 2018 hash code: xrVYbth32e6GM6jXHLKb (do NOT delete this line) <- generate a new hash code

class HospitalRenovation {
  private int V; // number of vertices in the graph (number of rooms in the hospital)
  private ArrayList<ArrayList<Integer>> AdjList; // the graph (the hospital)
  private int[] RatingScore; // the weight of each vertex (rating score of each room)

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  private final int MAX = 100000;
  private int time;
  private int[] disc;
  private int[] parent;
  private int[] low; // the discovery time of ancestor node T whereby descendent node v and T are connected by at most 1 back edge
  private boolean[] visited;
  private ArrayList<Integer> cr; // list of critical rooms

  public HospitalRenovation() {
    // Write necessary code during construction
    //
    // write your answer here
    
  }

  int Query() {
    int ans = 0;

    // You have to report the rating score of the critical room (vertex)
    // with the lowest rating score in this hospital
    //
    // or report -1 if that hospital has no critical room
    //
    // write your answer here
    DFS(0);

    if (cr.isEmpty()) 
      return -1;
    else {
      ans = RatingScore[cr.get(0)]; // Initialise minimum
      for (int i = 1; i < cr.size(); i++) {
        if (RatingScore[cr.get(i)] < ans)
          ans = RatingScore[cr.get(i)];
      }
    }

    return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------

  // A DFS Tree
  private void DFS(int curr) {
    update(curr);
    
    boolean isCritical = false;
    // Neighbours of curr(ent) node are its children
    int childrenSize = 0;

    // Traverse through list of curr(ent) node's neighbours
    for (int i = 0; i < AdjList.get(curr).size(); i++) {
      int child = AdjList.get(curr).get(i);
      childrenSize++;

      // if we did not visit node at index i before
      if (visited[child] == false) {
        parent[child] = curr; // update the path taken
        DFS(child); // recursive
        // update. if child connects to ancestor node, curr can connect too
        low[curr] = low[child] < low[curr] ? low[child] : low[curr];
        
        // checks if curr is critical
        if (isCritical == false) {
          // if curr is at root and it has more than 1 children
          if (parent[curr] == -1 && childrenSize > 1) {
            isCritical = true; // set to true to prevent adding curr again
            cr.add(curr);
            
            // if curr is NOT root and has a child who has no vertex in subtree that can connect to curr
          } else if (parent[curr] != -1 && low[child] >= disc[curr]) {
            isCritical = true; // set to true to prevent adding curr again
            cr.add(curr);
          }
        }
      }
      // if node at index i has been visited
      else
        low[curr] = low[curr] < disc[child] ? low[curr] : disc[child];
    }
  }

  // Update relevant information
  private void update(int node) {
    visited[node] = true;
    time++;
    disc[node] = time;
    low[node] = time;
  }

  // Intialise the Adjacent list and relevant information
  public void initialise() {
    time = 0;
    disc = new int[V];
    parent = new int[V];
    visited = new boolean[V];
    low = new int[V];
    cr = new ArrayList<Integer>();

    for (int i = 0; i < V; i++) {
      AdjList.add(new ArrayList<Integer>());
      visited[i] = false;
      low[i] = MAX;
      parent[i] = -1;
      disc[i] = -1;
    }
  }
  // --------------------------------------------

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {
      br.readLine(); // ignore dummy blank line
      V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new int[V];
      for (int i = 0; i < V; i++)
        RatingScore[i] = Integer.parseInt(st.nextToken());

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new ArrayList<ArrayList<Integer>>(V);
      initialise();

      for (int i = 0; i < V; i++) {
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
          int j = Integer.parseInt(st.nextToken());
          AdjList.get(i).add(j); // edge weight is always 1 (the weight is on vertices now)
        }
      }

      pr.println(Query());
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalRenovation ps3 = new HospitalRenovation();
    ps3.run();
  }
}



class IntegerPair implements Comparable < IntegerPair > {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}