package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.NanoMeta;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class NanoMetaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void assertNanoMetaTableExists() {
        var sql = """
                SELECT COUNT(*)
                FROM sqlite_master
                WHERE name = 'nano_meta';
                """;
        var rowMapper = new SingleColumnRowMapper<Integer>();
        var count = this.jdbcTemplate.queryForObject(sql, Map.of(), rowMapper);
        Assert.state(Objects.equals(count, 1), "table [nano_meta] is not exists");
    }

    public @NotNull List<NanoMeta> getAll() {
        var sql = """
                SELECT id, name, value, description
                FROM nano_meta;
                """;
        var rowMapper = new BeanPropertyRowMapper<>(NanoMeta.class);
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}
