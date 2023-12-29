package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.BarkTarget;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BarkTargetRepository {

    private final JdbcClient jdbcClient;

    public List<BarkTarget> getAll() {
        var sql = """
                SELECT id, name, url, enabled
                FROM bark_target
                WHERE name IS NOT NULL AND url IS NOT NULL;
                """;
        return this.jdbcClient.sql(sql)
                .query(BarkTarget.class)
                .list();
    }
}
