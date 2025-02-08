import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JobScheduler<K, V> {

    private AbstractEmitStrategy<K, V> emitStrategy;
    private AbstractOutputStrategy<K, V> outputStrategy;
    /**
     * Constructor.
     * @param emitStrategy The emit strategy.
     * @param outputStrategy The output strategy.
     */
    public JobScheduler(AbstractEmitStrategy<K, V> emitStrategy, AbstractOutputStrategy<K, V> outputStrategy) {
        this.emitStrategy = emitStrategy;
        this.outputStrategy = outputStrategy;
    }

    /**
     * Setter for the emit strategy.
     * @param emitStrategy The new emit strategy.
     */
    public void setEmitStrategy(AbstractEmitStrategy<K, V> emitStrategy) {
        this.emitStrategy = emitStrategy;
    }

    /**
     * Setter for the output strategy.
     * @param outputStrategy The new output strategy.
     */
    public void setOutputStrategy(AbstractOutputStrategy<K, V> outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    /**
     * Executes all phases of the job scheduling framework in a fixed sequence.
     * The execution follows these four sequential phases:
     * 1. Emit: Generates a stream of jobs using the configured emission strategy.
     * 2. Compute: Executes the jobs and produces key-value pairs.
     * 3. Collect: Groups values by their corresponding keys.
     * 4. Output: Writes or prints the collected results using the configured output strategy.
     *
     * This method ensures that all phases are executed in the correct order and must not be overridden.
     */
    public final void executePhases() {
        output(collect(compute(emit())));
    }

    /**
     * Executes the jobs received from emit by invoking execute on them.
     *
     * @return A single stream of key/value pairs obtained by concatenating the
     *         output of the jobs.
     */
    public Stream<AJob<K, V>> emit(){
        return emitStrategy.emit();
    }

    /**
     * Executes the jobs received from the emit phase by invoking their `execute()` method.
     *
     * @param stream The stream of jobs to be executed.
     * @return A stream of key-value pairs produced by executing all jobs.
     */
    public final Stream<Pair<K, V>> compute(Stream<AJob<K, V>> stream) {
        return stream.flatMap(AJob::execute);
    }

    /**
     * Groups the output of compute by key, keeping a list of all the values.
     *
     * @param computed Output of emit.
     * @return A stream of pairs grouped as described.
     */
    public final Stream<Pair<K, List<V>>> collect(Stream<Pair<K, V>> computed) {
        return computed
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())))
                .entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue()));
    }

    /**
     * Outputs the result of collect.
     * @param out Output of collect.
     */
    public void output(Stream<Pair<K, List<V>>> out){
        outputStrategy.output(out);
    }

    /**
     * The main entry point of the framework.
     * This method initializes a JobScheduler instance with concrete implementations
     * of the emission and output strategies, and then executes the job processing phases.
     */
    public static void main(String[] args) {
        JobScheduler<String, String> scheduler = new JobScheduler<>(
                new ConcreteEmitStrategy(),
                new ConcreteOutputStrategy()
        );
        scheduler.executePhases();
    }
}