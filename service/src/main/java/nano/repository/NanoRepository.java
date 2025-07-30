package nano.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nano.model.NanoMeta;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NanoRepository {

  private final JdbcClient jdbcClient;

  public @NotNull Map<String, String> getNanoMeta() {
    var sql = "SELECT `name`, `value` FROM nano_meta;";
    return this.jdbcClient.sql(sql)
      .query(NanoMeta.class)
      .list()
      .stream()
      .collect(Collectors.toMap(NanoMeta::getName, NanoMeta::getValue));
  }

  public void updateNanoMeta(String name, String value) {
    var sql = "update nano_meta SET value = ? WHERE name = ?";
    this.jdbcClient.sql(sql).param(value).param(name).update();
  }
}
