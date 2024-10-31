package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.NanoMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NanoRepository {

    private final JdbcClient jdbcClient;

    public @NotNull List<NanoMeta> getNanoMeta() {
        var sql = """
                SELECT id, name, value, description
                FROM nano_meta;
                """;
        return this.jdbcClient.sql(sql)
                .query(NanoMeta.class)
                .list();
    }

    public @Nullable String getNanoMetaValue(@NotNull String name) {
        var sql = """
                SELECT value
                FROM nano_meta
                WHERE name = :name
                """;
        return this.jdbcClient.sql(sql)
                .param("name", name)
                .query(String.class)
                .optional()
                .orElse(null);
    }
}
