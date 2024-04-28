import java.util.Stack;

public class QueueWithTwoStacks<T> {
    private Stack<T> stack1; // Used for enqueue
    private Stack<T> stack2; // Used for dequeue

    public QueueWithTwoStacks() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    // Enqueue operation: Push an element onto stack1
    public void enqueue(T item) {
        stack1.push(item);
    }

    // Dequeue operation: Pop an element from stack2 if it's not empty,
    // otherwise, transfer elements from stack1 to stack2 and then pop.
    public T dequeue() {
        if (stack2.isEmpty()) {
            // Transfer elements from stack1 to stack2
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (!stack2.isEmpty()) {
            return stack2.pop();
        }
        throw new IllegalStateException("Queue is empty.");
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return stack1.size() + stack2.size();
    }

    public static void main(String[] args) {
        QueueWithTwoStacks<Integer> queue = new QueueWithTwoStacks<>();

        // Enqueue elements
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        // Dequeue elements
        System.out.println(queue.dequeue()); // Output: 1
        System.out.println(queue.dequeue()); // Output: 2

        // Enqueue more elements
        queue.enqueue(4);
        queue.enqueue(5);

        // Dequeue remaining elements
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
        // Output: 3, 4, 5
    }
}
