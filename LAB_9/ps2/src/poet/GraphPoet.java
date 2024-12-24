package poet;

import graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class GraphPoet {

    private final Graph<String> graph = Graph.empty();  // Empty graph to store words and their adjacencies

    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        // Read the corpus from the file
        List<String> lines = Files.readAllLines(corpus.toPath());

        // Create a map to count adjacencies between words
        Map<String, Map<String, Integer>> wordGraph = new HashMap<>();

        // Process each line in the corpus
        for (String line : lines) {
            // Tokenize the line into words, removing punctuation and converting to lowercase
            String[] words = line.split("[\\s]+");  // Split by any whitespace
            for (int i = 0; i < words.length - 1; i++) {
                String word1 = normalize(words[i]);
                String word2 = normalize(words[i + 1]);

                // Update the adjacency map (word1 -> word2)
                wordGraph.putIfAbsent(word1, new HashMap<>());
                wordGraph.putIfAbsent(word2, new HashMap<>());
                wordGraph.get(word1).put(word2, wordGraph.get(word1).getOrDefault(word2, 0) + 1);
            }
        }

        // Now, populate the graph with the adjacencies and their weights
        for (Map.Entry<String, Map<String, Integer>> entry : wordGraph.entrySet()) {
            String word = entry.getKey();
            for (Map.Entry<String, Integer> adjacency : entry.getValue().entrySet()) {
                String adjacentWord = adjacency.getKey();
                int weight = adjacency.getValue();

                // Add the edge to the graph
                graph.add(word, adjacentWord, weight);
            }
        }
    }

    /**
     * Normalize the word: convert to lowercase and remove any non-alphanumeric characters
     */
    private String normalize(String word) {
        return word.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.split("\\s+");
        List<String> poem = new ArrayList<>();

        // For each consecutive pair of words, find the best bridge word
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = normalize(words[i]);
            String word2 = normalize(words[i + 1]);

            // Add the first word in the pair to the poem (preserving the case)
            poem.add(words[i]);

            // Find the best bridge word (if any)
            String bridgeWord = findBestBridgeWord(word1, word2);
            if (bridgeWord != null) {
                poem.add(bridgeWord);  // Add the bridge word in lowercase
            }
        }

        // Add the last word in the input
        poem.add(words[words.length - 1]);

        return String.join(" ", poem);
    }

    /**
     * Find the best bridge word between two words.
     * A bridge word is the word that connects two words via a two-edge path with the maximum weight.
     * 
     * @param word1 first word
     * @param word2 second word
     * @return the bridge word, or null if no bridge exists
     */
    private String findBestBridgeWord(String word1, String word2) {
        int maxWeight = 0;
        String bestBridgeWord = null;

        // Iterate through all neighbors of word1 and find the maximum weight path
        for (String neighbor : graph.neighbors(word1)) {
            int weight = graph.getEdgeWeight(word1, neighbor) + graph.getEdgeWeight(neighbor, word2);
            if (weight > maxWeight) {
                maxWeight = weight;
                bestBridgeWord = neighbor;
            }
        }

        return bestBridgeWord;
    }

    // Additional methods for testing and debugging

    /**
     * String representation of the graph for debugging
     */
    @Override
    public String toString() {
        return graph.toString();
    }

    // Optional checkRep method to ensure the graph's representation is valid
    private void checkRep() {
        for (String word : graph.vertices()) {
            for (String neighbor : graph.neighbors(word)) {
                assert word != null && neighbor != null : "Null words detected in graph";
                assert !word.isEmpty() && !neighbor.isEmpty() : "Empty words detected in graph";
            }
        }
    }
}
