import java.math.BigInteger;
import java.util.*;

public class Addition {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      BigInteger A = sc.nextBigInteger();
      BigInteger B = sc.nextBigInteger();

      if (A.equals(BigInteger.valueOf(-1)) && B.equals(BigInteger.valueOf(-1))) { // Converts int into BigInteger
        break;
      } else {
        System.out.println(A.add(B));
      }
    }
  }
}