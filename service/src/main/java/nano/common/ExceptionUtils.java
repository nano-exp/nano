package nano.common;

import java.util.function.Supplier;

public abstract class ExceptionUtils {

  @SuppressWarnings("unchecked")
  public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
    throw (E) e;
  }

  public static <T> T toValue(Supplier<T> supplier) {
    return toValue(supplier, "Can't get value");
  }

  public static <T> T toValue(Supplier<T> supplier, String message) {
    try {
      return supplier.get();
    } catch (Exception ex) {
      throw new IllegalStateException(message, ex);
    }
  }
}
