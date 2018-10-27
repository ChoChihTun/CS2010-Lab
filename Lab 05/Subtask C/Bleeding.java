// Copy paste this Java Template and save it as "Bleeding.java"
import java.util.*;
import java.io.*;
import javafx.util.Pair;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here: Referred to lecture 8 and 9
// year 2018 hash code: psJ6yCZMN7uwQv79EtpQ (do NOT delete this line)

class Bleeding {
  private int V; // number of vertices in the graph (number of junctions in Singapore map)
  private int Q; // number of queries

  private ArrayList<ArrayList<IntegerPair>> AdjList; // the weighted graph (the Singapore map), the length of each edge(road) is stored here too, as the weight of edge

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------

  private int limit;
  private int[][] path_matrix;
  private final int MAX = 1000000000;
  private Queue<IntegerPair> q;


  // --------------------------------------------

  public Bleeding() {
    // Write necessary code during construction
    //
    // write your answer here

  }

  void PreProcess() {

  }

  int Query(int s, int t, int k) {
    int ans = -1;
    // You have to report the shortest path from Ket Fah's current position s
    // to reach the chosen hospital t, output -1 if t is not reachable from s
    // with one catch: this path cannot use more than k vertices

    if (s == t)
      return 0;
      
    initMatrix(s, t, k);
    // ans = DPShortestPaths(s, k - 1);

    shortestPath(s);

    ans = path_matrix[t][k];
    if (ans == MAX)
      ans = -1;
    return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------
  void initMatrix(int s, int t, int k) {
    limit = k;
    path_matrix = new int[V][k+1];
    q = new LinkedList<IntegerPair>();
    q.offer(new IntegerPair(s, 1));

    for (int i = 0; i < V; i++)
      for (int j = 0; j < k+1; j++)
          path_matrix[i][j] = MAX; 
          
    path_matrix[s][1] = 0;
  }

  private void shortestPath(int s) {
    while (!q.isEmpty()) {
      IntegerPair front = q.poll();
      int level = front.second(); // current level of the vertex
      int curr = front.first(); // vertex we are currently at

      if (level + 1 <= limit) { // within the limit
        for (IntegerPair e : AdjList.get(curr)) {
          int weight = e.second();
          int neighbour = e.first();
          if (neighbour != s) {
            int newDist = path_matrix[curr][level] + weight;
            // System.out.println("level: " + level + " Source: " + front + " neighbour: " + neighbour);
            // System.out.println("newDist: " + newDist);
            int oldDist = path_matrix[neighbour][level];
            // System.out.println("oldDist: " + oldDist);
            // System.out.println("===============================================");

            path_matrix[neighbour][level + 1] = Math.min(newDist, oldDist);
            q.offer(new IntegerPair(neighbour, level + 1));
          }
        }
      }
    }
  }

  // int DPShortestPaths(int current, int remaining_hops) {
  //   int ans = MAX;
  //   if (remaining_hops == 0)
  //     return path_matrix[current][remaining_hops];

  //   if (path_matrix[current][remaining_hops] != -1)
  //     return path_matrix[current][remaining_hops];

  //   for (IntegerPair edge : AdjList.get(current)) {
  //     int neighbour = edge.first();
  //     int weight = edge.second();
  //     int path_weight = DPShortestPaths(neighbour, remaining_hops - 1) + weight;
  //     ans = Math.min(ans, path_weight);
  //   }

  //   path_matrix[current][remaining_hops] = ans;
  //   return ans;
  // }
  // --------------------------------------------

  void run() throws Exception {
    // you can alter this method if you need to do so
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new ArrayList<ArrayList<IntegerPair>>();
      // initialise queries array to adjacency matrix
      for (int i = 0; i < V; i++) {
        AdjList.add(new ArrayList<IntegerPair>());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
        }
      }

      PreProcess(); // optional

      Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

      if (TC > 0)
        pr.println();
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Bleeding ps5 = new Bleeding();
    ps5.run();
  }
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
  BufferedInputStream bis;

  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }

  public int nextInt() {
    int result = 0;
    try {
      int cur = bis.read();
      if (cur == -1)
        return -1;

      while ((cur < 48 || cur > 57) && cur != 45) {
        cur = bis.read();
      }

      boolean negate = false;
      if (cur == 45) {
        negate = true;
        cur = bis.read();
      }

      while (cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }

      if (negate) {
        return -result;
      }
      return result;
    } catch (IOException ioe) {
      return -1;
    }
  }
}

class IntegerPair implements Comparable<IntegerPair> {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (this.second() > o.second())
      return 1;
    else if (this.second() < o.second())
      return -1;
    else
      return 0;
  }

  Integer first() {
    return _first;
  }

  Integer second() {
    return _second;
  }
}