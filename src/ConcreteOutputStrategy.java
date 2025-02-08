import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public class ConcreteOutputStrategy extends AbstractOutputStrategy<String,String> {
    /**
     * Output the results to a file
     *
     * @param collection the collection of pairs to output
     */
    @Override
    public void output(Stream<Pair<String, List<String>>> collection) {
        File outFile = new File("count_anagrams.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            writer.write("Key,Count\n"); // CSV Header

            collection.forEach(pair -> {
                try {
                    writer.write(pair.getKey() + "," + pair.getValue().size());
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Error writing data to file: " + e.getMessage());
                }
            });

            System.out.println("Output successfully written to " + outFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error writing to file " + outFile.getAbsolutePath() + ": " + e.getMessage());
        }
    }
}