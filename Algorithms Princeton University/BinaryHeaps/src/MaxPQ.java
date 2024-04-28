import java.util.Random;

public class MaxPQ<Key extends Comparable<Key>>
{
    private Key[] pq;
    private int N;

    private Random random = new Random();

    public static void main(String[] args) {}

    public MaxPQ(int capacity)
    {pq = (Key[]) new Comparable[capacity+1];}

    public boolean isEmpty()
    {return N==0;}

    public void insert(Key x)
    {
        pq[++N] = x;
        swim(N);
    }

    public Key delMax()
    {
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N+1] = null;
        return max;
    }

    private void swim(int k)
    {
        while (k > 1 && less (k/2, k))
        {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k)
    {
        while (2*k <= N)
        {
            int j = 2*k;
            if(j<N && less(j,j+1)) j++;
            if(!less(k,j)) break;
            exch(k,j);
            k=j;
        }
    }

    private boolean less(int i, int j)
    {   return pq[1].compareTo(pq[j]) < 0;    }

    private void exch(int i, int j)
    {   Key t = pq[i]; pq[i] = pq[j]; pq[j] = t;    }


    public Key sample()
    {
        if (isEmpty())
        {
            throw new IllegalStateException("Heap is empty");
        }
        int randomIndex = random.nextInt(N) + 1;
        return pq[randomIndex];
    }

    public Key delRandom()
    {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        int randomIndex = random.nextInt(N) + 1;
        Key randomKey = pq[randomIndex];

        exch(randomIndex, N);

        N--;
        pq[N + 1] = null;

        sink(randomIndex);

        return randomKey;
    }
}
