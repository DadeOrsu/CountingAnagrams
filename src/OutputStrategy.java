import java.util.List;
import java.util.stream.Stream;
public interface OutputStrategy<K,V> {
    public void output(Stream<Pair<K, List<V>>> out);
}
