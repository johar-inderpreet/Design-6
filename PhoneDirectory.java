import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

//TC: O(1) for get, check, release, SC: O(n)
//Approach: We will use a hash set to check if a number is available or not, queue for unassigned numbers
//we need to maintain 2 DS in order to perform these operations in constant time
public class PhoneDirectory {

    private final Queue<Integer> available;
    private final Set<Integer> availableSet;

    public PhoneDirectory(int maxNumbers) {
        this.available = new LinkedList<>();
        this.availableSet = new HashSet<>();

        for (int i = 0; i < maxNumbers; i++) {
            availableSet.add(i);
            available.add(i);
        }
    }

    public int get() {
        if (available.isEmpty()) return -1;

        int result = available.poll();
        availableSet.remove(result);

        return result;
    }

    public boolean check(int number) {
        return availableSet.contains(number);
    }

    public void release(int number) {
        if (availableSet.contains(number)) return;

        availableSet.add(number);
        available.add(number);
    }
}
