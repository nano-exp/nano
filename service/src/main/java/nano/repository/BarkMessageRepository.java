package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.BarkMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BarkMessageRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public synchronized Integer create(@NotNull BarkMessage message) {
        var insertSql = """
                INSERT INTO bark_message (payload, ack_time, create_time, domain, comment)
                VALUES (:payload, :ackTime, :createTime, :domain, :comment);
                """;
        var source = new BeanPropertySqlParameterSource(message);
        this.jdbcTemplate.update(insertSql, source);
        var selectIdSql = "SELECT LAST_INSERT_ROWID();";
        return this.jdbcTemplate.queryForObject(selectIdSql, Map.of(), new SingleColumnRowMapper<>());
    }

    public @NotNull List<BarkMessage> getNotAckedList() {
        var sql = """
                SELECT id, payload, ack_time, create_time, domain, comment
                FROM bark_message
                WHERE ack_time IS NULL;
                """;
        var rowMapper = new BeanPropertyRowMapper<>(BarkMessage.class);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public @Nullable BarkMessage getById(Integer id) {
        var sql = """
                SELECT id, payload, ack_time, create_time, domain, comment
                FROM bark_message
                WHERE id = :id;
                """;
        var rowMapper = new BeanPropertyRowMapper<>(BarkMessage.class);
        var messageList = this.jdbcTemplate.query(sql, Map.of("id", id), rowMapper);
        if (CollectionUtils.isEmpty(messageList)) {
            return null;
        }
        return messageList.get(0);
    }

    public void updateAckTime(@NotNull Integer id, @NotNull String time, @NotNull String comment) {
        var sql = """
                UPDATE bark_message
                SET ack_time = :time,
                    comment  = :comment
                WHERE id = :id;
                """;
        var paramMap = Map.of("id", id, "time", time, "comment", comment);
        this.jdbcTemplate.update(sql, paramMap);
    }
}
