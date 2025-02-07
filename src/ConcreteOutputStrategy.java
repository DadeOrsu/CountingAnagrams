import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public class ConcreteOutputStrategy extends AbstractOutputStrategy<String,String> {
    /**
     * Output the results to a file
     * @param collection the collection of pairs to output
     */
    @Override
    public void output(Stream<Pair<String, List<String>>> collection) {
        File outFile = new File("count_anagrams.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            writer.write("Key,Count\n");
            collection.forEach(pair -> {
                try {
                    writer.write(pair.getKey() + "," + pair.getValue().size());
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Output written to " + outFile.getAbsolutePath());
    }
}
