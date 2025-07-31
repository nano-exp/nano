package nano.common;

import java.util.function.Supplier;

public abstract class Ext {

  @SuppressWarnings("unchecked")
  public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
    throw (E) e;
  }

  public static <T> T get(Supplier<T> supplier) {
    return get(supplier, "Can't get value");
  }

  public static <T> T get(Supplier<T> supplier, String message) {
    try {
      return supplier.get();
    } catch (Exception ex) {
      throw new IllegalStateException(message, ex);
    }
  }
}
