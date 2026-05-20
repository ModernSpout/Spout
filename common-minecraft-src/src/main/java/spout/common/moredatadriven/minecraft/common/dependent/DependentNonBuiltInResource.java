package spout.common.moredatadriven.minecraft.common.dependent;

import net.minecraft.resources.Identifier;
import java.util.List;

/**
 * A non-built-in resource that may be dependent on other resources
 * of the same type, thereby imposing a required loading order.
 */
public interface DependentNonBuiltInResource {

    List<Identifier> getRequiredResources();

}
