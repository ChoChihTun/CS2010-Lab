// Copy paste this Java Template and save it as "GettingFromHereToThere.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here: Referred to the PrimDemo codes
// year 2018 hash code: c3HL7hbuMVasJh2TEnnf (do NOT delete this line) <-- change this

class GettingFromHereToThere {
	private int V; // number of vertices in the graph (number of rooms in the building)
	private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	// --------------------------------------------

	private PriorityQueue<IntegerTriple> pq;
	private int[] visited;

	private ArrayList<ArrayList<IntegerTriple>> MST;
	private ArrayList<ArrayList<IntegerTriple>> AdjList2;
	private int[][] queries;
	private int src_size;
	// --------------------------------------------

	public GettingFromHereToThere() {
		// Write necessary codes during construction;
		//
		// write your answer here
	}

	void PreProcess() {
		// write your answer here
		// you can leave this method blank if you do not need it
		src_size = Math.min(10, AdjList.size());
		queries = new int [src_size][AdjList.size()];

		Prim_MST();
		computeQueries(); // Compute every possible query
		// preQuery();
	}

	int Query(int source, int destination) {
		int ans = 0;

    // You have to report the weight of a corridor (an edge)
    // which has the highest effort rating in the easiest path from source to destination for the wheelchair bound
    //
		// write your answer here
		
		return (source != destination) ? queries[source][destination] : 0;
	}

	// You can add extra function if needed
	// --------------------------------------------

	private void process(int vtx) {
		visited[vtx] = 1;
		// Loop through neighbours of vtx // Storing as (source, dest, weight)
		for (int j = 0; j < AdjList2.get(vtx).size(); j++) {
			IntegerTriple v = AdjList2.get(vtx).get(j);

			if (visited[v.second()] == 0) {
				pq.offer(new IntegerTriple(v.first(), v.second(), v.third()));
				// for (IntegerTriple e : pq)
				// System.out.println("PQ: " + e.first() + ", " + e.second() + ") Weight: " +
				// e.third());
				// System.out.println(">> Inserting (" + v.first() + ", " + v.second() + ")
				// Weight: " + v.third() + "=> to priority queue");
				// } else
				// System.out.println(">> Ignoring (" + v.first() + ", " + v.second() + ")
				// Weight: " + v.third());
			}
		}
	}

	private void Prim_MST() {
		visited = new int[V];
		pq = new PriorityQueue<IntegerTriple>();

		process(0);

		while (!pq.isEmpty()) {
			IntegerTriple front = pq.poll();
			if (visited[front.second()] == 0) { // we have not connected this vertex yet
				// System.out.println(front.first() + " -> " + front.second() + " => weight: " +
				// front.third());
				MST.get(front.first()).add(front);
				MST.get(front.second()).add(new IntegerTriple(front.second(), front.first(), front.third()));
				// System.out.println(
				// ">> Inserting (" + front.first() + ", " + front.second() + ") Weight: " +
				// front.third() + "=> to MST");
				process(front.second());
			}
		}
	}

	void querying(int original_src, int curr_src, int curr_max) {
		visited[curr_src] = 1;
		// Loop through all the neighbours of curr_src
		for (int i = 0; i < MST.get(curr_src).size(); i++) {
			IntegerTriple e = MST.get(curr_src).get(i);
			int neighbour = e.second();
			if (visited[neighbour] == 0) {
				queries[original_src][neighbour] = Math.max(e.third(), curr_max);
				querying(original_src, neighbour, queries[original_src][neighbour]);
			}
		}          
	}

	private void computeQueries() {
		for (int i = 0; i < src_size; i++) {
			visited = new int[V];
			querying(i, i, 0);
		}
	}

	// --------------------------------------------

	void run() throws Exception {
		// do not alter this method
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new Vector < Vector < IntegerPair > >();
			AdjList2 = new ArrayList<ArrayList<IntegerTriple>>();
			MST = new ArrayList<ArrayList<IntegerTriple>>(V);
			
			for (int i = 0; i < V; i++) {
				AdjList.add(new Vector < IntegerPair >());
				AdjList2.add(new ArrayList<IntegerTriple>());
				MST.add(new ArrayList<IntegerTriple>());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					// AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
					//adjlist 2 is source,dest,weight
					AdjList2.get(i).add(new IntegerTriple(i,j, w));
				}
			}

			PreProcess(); // you may want to use this function or leave it empty if you do not need it

			int Q = sc.nextInt();
			while (Q-- > 0)
				pr.println(Query(sc.nextInt(), sc.nextInt()));
			pr.println(); // separate the answer between two different graphs
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		//startTime = System.nanoTime();
		GettingFromHereToThere ps4 = new GettingFromHereToThere();
		ps4.run();
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



class IntegerTriple implements Comparable < IntegerTriple > {
	Integer _first, _second, _third;

	public IntegerTriple(Integer f, Integer s, Integer t) {
		_first = f;
		_second = s;
		_third = t;
	}

	public int compareTo(IntegerTriple o) {
		if (!this.third().equals(o.third()))
			return this.third() - o.third();
		else if (!this.second().equals(o.second()))
			return this.second() - o.second();
		else
			return this.first() - o.first();
	}

	Integer first() { return _first; }
	Integer second() { return _second; }
	Integer third() { return _third; }
}