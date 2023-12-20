import java.util.stream.Stream;

public interface EmitStrategy<K, V> {
    Stream<AJob<K, V>> emit();
}
