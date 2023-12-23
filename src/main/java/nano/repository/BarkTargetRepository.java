package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.BarkTarget;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BarkTargetRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<BarkTarget> getAll() {
        var sql = "SELECT id, name, url, enabled FROM bark_target;";
        var rowMapper = new BeanPropertyRowMapper<>(BarkTarget.class);
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}
