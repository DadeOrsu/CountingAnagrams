import java.util.List;
import java.util.stream.Stream;
public interface OutputStrategy<K,V> {
    void output(Stream<Pair<K, List<V>>> out);
}
