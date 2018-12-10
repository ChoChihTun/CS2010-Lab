// Copy paste this Java Template and save it as "Supermarket.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here: Referred to Lecture note 11 on travelling salesman and to Lecture 12 note on Floyd Warshall
// year 2018 hash code: 8A2sCvBuVXdWFrYXe63U (do NOT delete this line)

class Supermarket {
  private int N; // number of items in the supermarket. V = N+1 
  private int K; // the number of items that Prof. Chong Ket Fah has to buy
  private int[] shoppingList; // indices of items that Prof. Chong Ket Fah has to buy
  private int[][] T; // the complete weighted graph that measures the direct wheeling time to go from one point to another point in seconds

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  private int[] visited;
  private int min_Cost;
  private int MAX = 100000000;

  private int[][] dist;
  // --------------------------------------------



  public Supermarket() {
    // Write necessary code during construction
    //
    // write your answer here

  }

  int Query() {
    int ans = 0;

    // You have to report the quickest shopping time that is measured
    // since Prof. Chong Ket Fah enters the supermarket (vertex 0),
    // completes the task of buying K items in that supermarket,
    // then reaches the cashier of the supermarket (back to vertex 0).
    //
    // write your answer here

    // Only can pick this item in the supermarket
    if (N == 1 && K == 1) {
      ans = T[0][1] * 2;
    } else {
      FloydWarshall(); // Pre-compute shortest path between all the possible pairs of vertices
      initDFS();
      DFS(0, 0, K);

      ans = min_Cost;
    }

    return ans;
  }

  // You can add extra function if needed
  private void FloydWarshall() {
    dist = new int[N+1][N+1];

    // Initialise the distance between vertice pair to INF
    for (int i = 0; i < N+1; i++)
      for (int j = 0; j < N+1; j++)
        dist[i][j] = T[i][j];

    for (int k = 0; k < N+1; k++) {
      for (int i = 0; i < N+1; i++)
        for (int j = 0; j < N+1; j++)
          dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
    }
  }

  private void initDFS() {
    visited = new int[N+1]; // 0 = false, 1 = true
    min_Cost = MAX;
  }

  // private void DFS(int u, int curr_Cost, int item_left) {
  //   if (item_left == 0) {
  //     min_Cost = Math.min(min_Cost, curr_Cost + dist[u][0]);
  //     return;
  //   }

  //    visited[u] = 1;

  //    for (int j = 0; j < N+1; j++) {
  //      // if u and j are different vertices
  //      // and if j is one of the items in shopping list
  //      if (u != j) {
  //        if (visited[j] == 0)
  //          DFS(j, curr_Cost + dist[u][j], item_left-1); 
  //      }
  //    }
  //    visited[u] = 0;
  // }

  private void DFS(int u, int curr_Cost, int item_left) {
    if (item_left == 0) {
      min_Cost = Math.min(min_Cost, curr_Cost + dist[u][0]);
      return;
    }

    visited[u] = 1;

    for (int j = 0; j < K; j++) {
      int item = shoppingList[j];

      // if u and j are different vertices
      // and if j is one of the items in shopping list
      if (u != item) {
        if (visited[item] == 0)
          DFS(item, curr_Cost + dist[u][item], item_left - 1);
      }
    }
    visited[u] = 0;
  }

  // --------------------------------------------



  void run() throws Exception {
    // do not alter this method to standardize the I/O speed (this is already very fast)
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      // read the information of the complete graph with N+1 vertices
      N = sc.nextInt(); K = sc.nextInt(); // K is the number of items to be bought

      shoppingList = new int[K];
      for (int i = 0; i < K; i++)
        shoppingList[i] = sc.nextInt();

      T = new int[N+1][N+1];
      for (int i = 0; i <= N; i++)
        for (int j = 0; j <= N; j++)
          T[i][j] = sc.nextInt();

      pw.println(Query());
    }

    pw.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Supermarket ps6 = new Supermarket();
    ps6.run();
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
    }
    catch (IOException ioe) {
      return -1;
    }
  }
}