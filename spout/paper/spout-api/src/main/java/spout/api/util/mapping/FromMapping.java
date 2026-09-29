package spout.api.util.mapping;

import java.util.List;
import org.jspecify.annotations.Nullable;
import spout.api.util.mapping.builder.FromRegistryEntryBuilder;

/**
 * A mapping that targets zero or more particular target elements.
 */
public interface FromMapping<T> {

    /**
     * The targets for this mapping.
     */
    List<? extends T> getFrom();

}
