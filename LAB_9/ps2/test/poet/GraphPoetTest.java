package poet;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GraphPoetTest {
    
    // Sample corpus files for testing
    private static final File SEVEN_WORDS_FILE = new File("test/poet/seven-words.txt");
    private static final File EMPTY_FILE = new File("test/poet/empty.txt");
    private static final File MALFORMED_FILE = new File("test/poet/malformed.txt");
    
    // Constructor Tests

    @Test
    public void testGraphPoetWithValidFile() {
        try {
            GraphPoet poet = new GraphPoet(SEVEN_WORDS_FILE);
            assertNotNull(poet);  // Ensure the GraphPoet is initialized
        } catch (IOException e) {
            fail("IOException should not occur with valid file.");
        }
    }

    @Test(expected = IOException.class)
    public void testGraphPoetWithInvalidFile() throws IOException {
        new GraphPoet(new File("nonexistent-file.txt"));
    }

    // Test for empty file (edge case)
    @Test
    public void testGraphPoetWithEmptyFile() {
        try {
            GraphPoet poet = new GraphPoet(EMPTY_FILE);
            assertNotNull(poet);  // Ensure GraphPoet is still created
            // Ensure it doesn't generate poems (empty graph)
            List<String> poem = poet.generatePoem(5);  // Assuming 5 words
            assertTrue("Poem should be empty for empty file", poem.isEmpty());
        } catch (IOException e) {
            fail("IOException should not occur with empty file.");
        }
    }

    // Malformed file (e.g., file with invalid structure)
    @Test
    public void testGraphPoetWithMalformedFile() {
        try {
            GraphPoet poet = new GraphPoet(MALFORMED_FILE);
            assertNotNull(poet);
            List<String> poem = poet.generatePoem(5);
            assertNotNull("Poem should not be null", poem);
        } catch (IOException e) {
            fail("IOException should not occur with malformed file.");
        }
    }

    // Poem Generation Tests

    @Test
    public void testGeneratePoemWithValidFile() {
        try {
            GraphPoet poet = new GraphPoet(SEVEN_WORDS_FILE);
            List<String> poem = poet.generatePoem(3);
            assertEquals("Poem should contain 3 words", 3, poem.size());
            // Add more assertions here depending on expected behavior of the poem
            assertTrue("Poem should not be empty", poem.size() > 0);
        } catch (IOException e) {
            fail("IOException should not occur with valid file.");
        }
    }

    // Edge Case: Large File
    @Test
    public void testGeneratePoemWithLargeFile() {
        try {
            GraphPoet poet = new GraphPoet(new File("test/poet/large-file.txt"));
            List<String> poem = poet.generatePoem(100);
            assertTrue("Poem should be generated even for large files", poem.size() > 0);
        } catch (IOException e) {
            fail("IOException should not occur with large file.");
        }
    }

    // Edge Case: File with repetitive content
    @Test
    public void testGeneratePoemWithRepetitiveFile() {
        try {
            GraphPoet poet = new GraphPoet(new File("test/poet/repetitive.txt"));
            List<String> poem = poet.generatePoem(3);
            // Assert that the generated poem has meaningful content, not just repeated words
            assertNotEquals("Poem should not be just a repetition of the same word", poem.get(0), poem.get(1));
        } catch (IOException e) {
            fail("IOException should not occur with repetitive file.");
        }
    }

    // Test for assertion error with invalid corpus (if applicable)
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Ensure assertions are enabled
    }
}
