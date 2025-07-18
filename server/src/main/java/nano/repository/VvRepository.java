package nano.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nano.model.Vv;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VvRepository {

  private final JdbcClient jdbcClient;

  public @NotNull Vv getRandomVv() {
    var sql = """
      SELECT id, name, url, comment
      FROM vv
      ORDER BY RANDOM()
      LIMIT 1;
      """;
    return this.jdbcClient.sql(sql).query(Vv.class).single();
  }

  public @Nullable Vv getVvById(@NotNull Integer id) {
    var sql = """
      SELECT id, name, url, comment
      FROM vv
      WHERE id = ?
      """;
    return this.jdbcClient.sql(sql).param(id).query(Vv.class).optional().orElse(null);
  }

  public @Nullable Vv getVvByName(@NotNull String name) {
    var sql = """
      SELECT id, name, url, comment
      FROM vv
      WHERE name = ?
      """;
    return this.jdbcClient.sql(sql).param(name).query(Vv.class).optional().orElse(null);
  }

  public void deleteVv(@NotNull Integer id) {
    var sql = """
      DELETE
      FROM vv
      WHERE id = ?
      """;
    this.jdbcClient.sql(sql).param(id).update();
  }

  public @NotNull List<Vv> findVvList(@NotNull final String keyword, Integer limit, Integer offset) {
    var sql = """
      SELECT id, name, url, comment
      FROM vv
      WHERE name LIKE ?
      ORDER BY id DESC
      LIMIT ? OFFSET ?;
      """;
    return this.jdbcClient.sql(sql)
      .param(String.join("", "%", keyword, "%"))
      .param(limit)
      .param(offset)
      .query(Vv.class)
      .list();
  }

  public @NotNull Integer countVvList(@NotNull final String keyword) {
    var sql = """
      SELECT COUNT(*)
      FROM vv
      WHERE name LIKE ?;
      """;
    return this.jdbcClient.sql(sql).param(String.join("", "%", keyword, "%")).query(Integer.class).single();
  }

  public void createVv(@NotNull Vv vv) {
    var sql = """
      INSERT INTO vv (name, url, comment)
      VALUES (:name, :url, :comment);
      """;
    this.jdbcClient.sql(sql).paramSource(vv).update();
  }
}
