import java.util.Iterator;
import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Permutation <k>");
            return;
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        // Read input from standard input
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            String line;
            while ((line = br.readLine()) != null) {
                String item = line.trim();
                if (!item.isEmpty()) {
                    rq.enqueue(item);
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }

        // Print k randomly selected items
        Iterator<String> iterator = rq.iterator();
        for (int i = 0; i < k; i++) {
            try {
                if (iterator.hasNext()) {
                    System.out.println(iterator.next());
                } else {
                    break; // If fewer than k items in the queue, stop.
                }
            } catch (NoSuchElementException e) {
                break; // If no more items to return, stop.
            }
        }
    }
}
