import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class ConcreteEmitStrategy extends AbstractEmitStrategy<String, String>{
    /**
     * Emit jobs for each file in a directory
     * @return a stream of jobs
     */
    public Stream<AJob<String, String>> emit() {
        // Get directory path
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the path of the directory where the documents are stored:");
        File directory = new File(input.nextLine());

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Error: The specified path is not a valid directory.");
            return Stream.empty();
        }


        // Spawn jobs for each file in the directory
        List<AJob<String, String>> jobs = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().toLowerCase().endsWith(".txt")) {
                jobs.add(new Job(file.getAbsolutePath()));
            }
        }

        if (jobs.isEmpty()) {
            System.out.println("Warning: No .txt files found in the directory.");
        }

        return jobs.stream();
    }
}
