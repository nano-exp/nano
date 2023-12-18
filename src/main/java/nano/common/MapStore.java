package nano.common;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class MapStore {

    private final Map<String, Object> store = new HashMap<>();
    private final Path storePath;

    public MapStore(@NotNull String pathUrl) {
        this.storePath = Paths.get(pathUrl);
    }

    public void restore() {
        try (var os = new ByteArrayOutputStream()) {
            if (Files.exists(this.storePath)) {
                Files.copy(this.storePath, os);
                var s = os.toString(StandardCharsets.UTF_8);
                this.store.putAll(Json.parseMap(s));
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public <T> T get(@NotNull String key, T defaultValue) {
        T value = this.get(key);
        return value == null ? defaultValue : value;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@NotNull String key) {
        return (T) this.store.get(key);
    }

    public void set(@NotNull String key, @NotNull Object value) {
        this.store.put(key, value);
    }

    public void persist() {
        var s = Json.stringify(this.store);
        try (var is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))) {
            Files.copy(is, this.storePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
