package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.NanoMeta;
import nano.model.Vv;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.StringJoiner;

@Repository
@RequiredArgsConstructor
public class VvRepository {

    private final JdbcClient jdbcClient;

    public @NotNull List<Vv> findVvList(@NotNull final String keyword, Integer limit, Integer offset) {
        var sql = """
                SELECT id, name, url, comment
                FROM vv
                WHERE name LIKE :like
                LIMIT :limit OFFSET :offset;
                """;
        return this.jdbcClient.sql(sql)
                .param("like", String.join("", "%", keyword, "%"))
                .param("limit", limit)
                .param("offset", offset)
                .query(Vv.class)
                .list();
    }
}