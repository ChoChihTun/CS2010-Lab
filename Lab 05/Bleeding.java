// Copy paste this Java Template and save it as "Bleeding.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here: 
// year 2018 hash code: psJ6yCZMN7uwQv79EtpQ (do NOT delete this line)

class Bleeding {
  private int V; // number of vertices in the graph (number of junctions in Singapore map)
  private int Q; // number of queries
  private Vector<Vector<IntegerPair>> AdjList; // the weighted graph (the Singapore map), the length of each edge (road)
                                               // is stored here too, as the weight of edge

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------
  private int MAX;
  private int[] D;
  private int[] p;
  private PriorityQueue<IntegerPair> pq;

  // idx = source; hashmap key = dest, value = junction
  private ArrayList<HashMap<Integer, Integer>> junctions;
  private int[][] queries;
  
  // --------------------------------------------

  public Bleeding() {
    // Write necessary code during construction
    //
    // write your answer here
    MAX = Integer.MAX_VALUE;
  }

  void PreProcess() {
    // Write necessary code to preprocess the graph, if needed
    //
    // write your answer here
    // -------------------------------------------------------------------------
    queries = new int[V][V];
    junctions = new ArrayList<>(V);
    for (int i = 0; i < V; i++) {
      junctions.add(new HashMap<>(V));
    }
    initSSSP();
    // -------------------------------------------------------------------------
  }

  int Query(int s, int t, int k) {
    int ans = -1;

    // You have to report the shortest path from Ket Fah's current position s
    // to reach the chosen hospital t, output -1 if t is not reachable from s
    // with one catch: this path cannot use more than k vertices
    //
    // write your answer here

    // checks if we have permute queries starting with this source
    if (s == t)
      return 0;

    if (junctions.get(s).isEmpty()) {
      initSSSP();
      D[s] = 0;
      junctions.get(s).put(s, 1); // source node is a junction itself

      Dijkstra(s);
    }

    if (junctions.get(s).get(t) <= k) {
      ans = queries[s][t];
    }

    // -------------------------------------------------------------------------

    return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------
  
  // Initialise Dijkstra Algo
  private void initSSSP() {
    D = new int[V];
    for (int i = 0; i < V; i++) {
      D[i] = MAX;
      pq = new PriorityQueue<IntegerPair>();
    }
  }

  private void Dijkstra(int ori_Src) {
    pq.add(new IntegerPair(ori_Src, 0)); // Adding the source node to PQ

    while (!pq.isEmpty()) {
      IntegerPair front = pq.poll();
      int u_idx = front.first();

      // Checks if distance from src to u is currently the shortest
      if (front.second() == D[u_idx]) {
        // Iterate through neighbours of u
        for (IntegerPair v : AdjList.get(u_idx)) {
          int v_idx = v.first();
          if (D[v_idx] > D[u_idx] + v.second()) {
            D[v_idx] = D[u_idx] + v.second();

            // Update result of the shortest dist between src and v
            queries[ori_Src][v_idx] = D[v_idx];

            // Update the number of junctions from src to v
            int junction_u = junctions.get(ori_Src).get(u_idx);
            junctions.get(ori_Src).put(v_idx, junction_u + 1);

            pq.add(new IntegerPair(v_idx, D[v_idx]));
          }
        }
      }
    }
  }
  // --------------------------------------------

  void run() throws Exception {
    // you can alter this method if you need to do so
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new Vector<Vector<IntegerPair>>();
      for (int i = 0; i < V; i++) {
        AdjList.add(new Vector<IntegerPair>());

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
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() {
    return _first;
  }

  Integer second() {
    return _second;
  }
}