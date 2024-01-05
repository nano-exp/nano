package nano.common;

public abstract class Env {

    public static final String NANO_HOST = System.getenv().getOrDefault("NANO_HOST", "http://localhost:9000/");
}
