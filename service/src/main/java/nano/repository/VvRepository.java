package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.Vv;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VvRepository {

    private final JdbcClient jdbcClient;

    public @Nullable Vv getVvByOffset(@NotNull Integer offset) {
        var sql = """
                SELECT id, name, url, comment
                FROM vv
                LIMIT 1 OFFSET :offset;
                """;
        return this.jdbcClient.sql(sql)
                .param("offset", offset)
                .query(Vv.class)
                .optional()
                .orElse(null);
    }

    public @Nullable Vv getVvById(@NotNull Integer id) {
        var sql = """
                SELECT id, name, url, comment
                FROM vv
                WHERE id = :id
                """;
        return this.jdbcClient.sql(sql)
                .param("id", id)
                .query(Vv.class)
                .optional()
                .orElse(null);
    }

    public @Nullable Vv getVvByName(@NotNull String name) {
        var sql = """
                SELECT id, name, url, comment
                FROM vv
                WHERE name = :name
                """;
        return this.jdbcClient.sql(sql)
                .param("name", name)
                .query(Vv.class)
                .optional()
                .orElse(null);
    }

    public void deleteVv(@NotNull Integer id) {
        var sql = """
                DELETE
                FROM vv
                WHERE id = :id
                """;
        this.jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }

    public @NotNull List<Vv> findVvList(@NotNull final String keyword, Integer limit, Integer offset) {
        var sql = """
                SELECT id, name, url, comment
                FROM vv
                WHERE name LIKE :like
                ORDER BY id DESC
                LIMIT :limit OFFSET :offset;
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
