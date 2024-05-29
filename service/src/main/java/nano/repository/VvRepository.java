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
                LIMIT :limit OFFSET :offset
                ORDER BY id DESC;
                """;
        return this.jdbcClient.sql(sql)
                .param("like", String.join("", "%", keyword, "%"))
                .param("limit", limit)
                .param("offset", offset)
                .query(Vv.class)
                .list();
    }

    public @NotNull Integer countVvList(@NotNull final String keyword) {
        var sql = """
                SELECT COUNT(*)
                FROM vv
                WHERE name LIKE :like;
                """;
        return this.jdbcClient.sql(sql)
                .param("like", String.join("", "%", keyword, "%"))
                .query(Integer.class)
                .single();
    }

    public void createVv(@NotNull Vv vv) {
        var sql = """
                INSERT INTO vv (name, url, comment)
                VALUES (:name, :url, :comment);
                """;
        this.jdbcClient.sql(sql).paramSource(vv)
                .update();
    }
}
