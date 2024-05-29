package nano.common;

public abstract class Env {

    private static String e(String key, String defaultValue) {
        return System.getenv().getOrDefault(key, defaultValue);
    }

    public static final String NANO_HOST = e("NANO_HOST", "http://localhost:9000/");
    public static final String CDN_R2_HOST = e("CDN_R2_HOST", "");

    public static final String R2_ACCESS_KEY = e("R2_ACCESS_KEY", "");
    public static final String R2_SECRET_KEY = e("R2_SECRET_KEY", "");
    public static final String R2_ENDPOINT = e("R2_ENDPOINT", "");
}
