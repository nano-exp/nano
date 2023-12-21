package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.NanoMeta;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NanoMetaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public @NotNull List<NanoMeta> getAll() {
        var rowMapper = new BeanPropertyRowMapper<>(NanoMeta.class);
        var sql = "SELECT id, name, value, description FROM nano_meta;";
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}
