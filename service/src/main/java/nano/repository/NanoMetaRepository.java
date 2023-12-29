package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.NanoMeta;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NanoMetaRepository {

    private final JdbcClient jdbcClient;

    public void assertNanoMetaTableExists() {
        var sql = """
                SELECT COUNT(*)
                FROM sqlite_master
                WHERE name = 'nano_meta';
                """;
        var count = this.jdbcClient.sql(sql)
                .query(Integer.class)
                .single();
        Assert.state(Objects.equals(count, 1), "table [nano_meta] is not exists");
    }

    public @NotNull List<NanoMeta> getAll() {
        var sql = """
                SELECT id, name, value, description
                FROM nano_meta;
                """;
        return this.jdbcClient.sql(sql)
                .query(NanoMeta.class)
                .list();
    }

    public Map<String, String> getAllMap() {
        return this.getAll().stream()
                .collect(Collectors.toMap(NanoMeta::getName, NanoMeta::getValue));
    }

    public String getValue(@NotNull String name) {
        var all = this.getAll();
        for (var it : all) {
            if (Objects.equals(it.getName(), name)) {
                return it.getValue();
            }
        }
        return null;
    }
}
