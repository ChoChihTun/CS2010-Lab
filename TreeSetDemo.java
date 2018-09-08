import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<Integer>();
        
        ts.add(10);
        ts.add(5);
        ts.add(30);
        ts.addAll(Arrays.asList(1, 4, 23, 14, 11));
        
        System.out.println(ts.contains(14));
        
        ts.remove(14);
        System.out.println(ts.contains(14));
                
        for (int a : ts) {
            System.out.print(a + " ");
        }
        System.out.println();
        
        SortedSet<Integer> sub = ts.subSet(1, 11);
        for (int a : sub) {
            System.out.print(a + " ");
        }
        System.out.println();
        
        int successor = ts.tailSet(11, false).first();
        System.out.println(successor);
        
        int predecessor = ts.headSet(11, false).last();
        System.out.println(predecessor);
    }
}