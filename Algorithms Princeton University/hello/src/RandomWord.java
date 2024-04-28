import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = ""; // Initialize the champion word

        int i = 0; // Initialize the word count
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString(); // Read the next word from standard input
            i++; // Increment the word count

            // Use Knuth's method to determine if the current word becomes the new champion
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = word;
            }
        }

        // Print the surviving champion
        StdOut.println(champion);
    }
}
