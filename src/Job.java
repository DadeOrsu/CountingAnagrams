import java.util.Arrays;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class Job extends AJob<String, String> {
    private final String path;

    public Job(String path) {
        this.path = path;
    }

    /**
     * Given a word, return its CIAO key.
     *
     * @param word Word to be processed.
     * @return CIAO key of the word.
     */
    private String ciao(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Read the file at path, and return all pairs of the form (ciao(word), word)
     * where word has >= 4 characters and contains alphabetical characters only.
     * 1 - Split to lines
     * 2 - Split to words (by whitespace)
     * 3 - Filter words that contain alphabetical characters only and have >= 4 characters
     * 4 - Map to pairs
     * @return Stream of pairs (ciao(word), word).
     */
    @Override
    public Stream<Pair<String, String>> execute() {
        try {
            return Files
                    .lines(Paths.get(this.path))
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(word -> word.length() >= 4 && word.matches("^[A-Za-z]+"))
                    .map(word -> new Pair<>(ciao(word), word.toLowerCase()));
        } catch (IOException e) {
            System.err.println("Error during opening of the file: " + path);
            e.printStackTrace();
            return Stream.empty();
        }
    }
}