import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TC: search O(1), insert: O(len(word)), SC: O(N)
//Approach: The idea is to quickly retrieve the sentences that match a given prefix
//We can use Trie data structure to store the sentences, along with the top3 matches at each node to return the results in O(1)
//why O(1) because the top3 list will not contain more than 3 elements, the list will be lexicographically sorted
//in the order of highest frequency, followed by lexicographical ordering
public class AutocompleteSystem {

    private static class TrieNode {
        private final List<String> top3;
        private final Map<Character, TrieNode> children;

        private TrieNode() {
            this.top3 = new ArrayList<>();
            this.children = new HashMap<>();
        }
    }

    private final TrieNode root;
    private final Map<String, Integer> freq;
    private StringBuilder builder;

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.root = new TrieNode();
        this.freq = new HashMap<>();
        this.builder = new StringBuilder();

        for (int i = 0; i < times.length; i++) {
            freq.put(sentences[i], freq.getOrDefault(sentences[i], 0) + times[i]);

            insert(sentences[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            final String word = builder.toString();
            this.freq.put(word, freq.getOrDefault(word, 0) + 1);

            insert(word);
            builder = new StringBuilder();

            return new ArrayList<>();
        }

        builder.append(c);
        return search(builder.toString());
    }

    private void insert(final String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!current.children.containsKey(ch)) {
                current.children.put(ch, new TrieNode());
            }

            current = current.children.get(ch);

            final List<String> top3 = current.top3;
            if (!top3.contains(word)) top3.add(word);

            top3.sort((a, b) -> {
                int countA = freq.get(a);
                int countB = freq.get(b);

                if (countA == countB) return a.compareTo(b);

                return countB - countA;
            });

            if (top3.size() > 3) top3.remove(3);
        }
    }

    private List<String> search(final String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!current.children.containsKey(ch)) return new ArrayList<>();

            current = current.children.get(ch);
        }

        return current.top3;
    }
}
