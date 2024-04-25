package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.NanoMeta;
import org.jetbrains.annotations.NotNull;
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
}
