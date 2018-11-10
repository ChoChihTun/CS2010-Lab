// this code runs in about 0.522s in UVa online judge

import java.util.*;
import java.io.*;

public class UVa10702 { // the default Java class name in UVa online judge is "Main", change it before you submit this code
  private static int INF = Integer.MAX_VALUE;
  private static int C;
  private static int[][] profit = new int[110][110];
  private static boolean[] canEnd = new boolean[110];
  private static int[][] memo = new int[110][1100];

  private static int get_profit(int u, int t) {
    if (t == 0) // last inter-city travel?
      return canEnd[u] ? 0 : -INF;
    if (memo[u][t] != -1) // computed before?
      return memo[u][t];

    memo[u][t] = -INF;
    for (int v = 0; v < C; v++) {
      if (v == u) continue;
      memo[u][t] = Math.max(memo[u][t],
         profit[u][v] + get_profit(v, t-1));
    }
    return memo[u][t];
  }

  public static void main(String[] args) throws Exception {
    IntegerScanner sc = new IntegerScanner(System.in);

/*
3 1 2 2
0 3 5
5 0 1
9 2 0
2 3

0 0 0 0

// the answer is 7, see
// http://uva.onlinejudge.org/external/107/10702.html
*/

    while (true) {
      C = sc.nextInt(); int S = sc.nextInt(), E = sc.nextInt(), T = sc.nextInt();
      if (C == 0 && S == 0 && E == 0 && T == 0)
        break;
    
      for (int i = 0; i < C; i++) // read the C*C profit table
        for (int j = 0; j < C; j++)
          profit[i][j] = sc.nextInt();

      Arrays.fill(canEnd, false); // read the list of cities the tour can end
      for (int i = 0; i < E; i++)
        canEnd[sc.nextInt()-1] = true; // use 0-based indexing

      for (int i = 0; i < 110; i++)
        Arrays.fill(memo[i], -1);
      System.out.printf("%d\n", get_profit(S-1, T)); // DP, use 0-based indexing for S
    }
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
