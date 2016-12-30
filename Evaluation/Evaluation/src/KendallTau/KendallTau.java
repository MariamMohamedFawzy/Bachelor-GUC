package KendallTau;

// http://algs4.cs.princeton.edu/25applications/KendallTau.java.html

/******************************************************************************
 *  Compilation:  javac KendallTau.java
 *  Execution:    java KendallTau n
 *  Dependencies: StdOut.java Inversions.java
 *  
 *  Generate two random permutations of size N and compute their
 *  Kendall tau distance (number of inversions).
 *
 ******************************************************************************/

public class KendallTau {

    // return Kendall tau distance between two permutations
    public static long distance(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;

        int[] ainv = new int[n];
        for (int i = 0; i < n; i++)
            ainv[a[i]] = i;

        Integer[] bnew = new Integer[n];
        for (int i = 0; i < n; i++)
            bnew[i] = ainv[b[i]];

        return Inversions.count(bnew);
    }


//    // return a random permutation of size n
//    public static int[] permutation(int n) {
//        int[] a = new int[n];
//        for (int i = 0; i < n; i++)
//            a[i] = i;
//        StdRandom.shuffle(a);
//        return a;
//    }
//
//
//    public static void main(String[] args) {
//
//        int n = 5;
//        int[] a = {0, 1, 2, 3, 4};
//        int[] b = KendallTau.permutation(n);
//        int[] c = KendallTau.permutation(n);
//
//
//        // print initial permutation
//        for (int i = 0; i < n; i++)
//            StdOut.println(a[i] + " " + b[i] + " " + c[i]);
//        StdOut.println();
//
//        StdOut.println("inversions = " + KendallTau.distance(a, b));
//        StdOut.println("inversions = " + KendallTau.distance(a, c));
//    }
}
