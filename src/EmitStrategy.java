import java.util.stream.Stream;

public interface EmitStrategy<K, V> {
    public Stream<AJob<K, V>> emit();
}
