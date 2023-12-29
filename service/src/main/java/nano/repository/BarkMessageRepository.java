package nano.repository;

import lombok.RequiredArgsConstructor;
import nano.model.BarkMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BarkMessageRepository {

    private final JdbcClient jdbcClient;

    private Integer selectLastInsertRowId() {
        return this.jdbcClient.sql("SELECT LAST_INSERT_ROWID();")
                .query(Integer.class)
                .single();
    }

    public synchronized Integer create(@NotNull BarkMessage message) {
        var sql = """
                INSERT INTO bark_message (payload, ack_time, create_time, domain, comment)
                VALUES (:payload, :ackTime, :createTime, :domain, :comment);
                """;
        int affectedRowCount = this.jdbcClient.sql(sql)
                .paramSource(message)
                .update();
        return affectedRowCount == 1 ? this.selectLastInsertRowId() : null;
    }

    public @NotNull List<BarkMessage> getNotAckedList() {
        var sql = """
                SELECT id, payload, ack_time, create_time, domain, comment
                FROM bark_message
                WHERE ack_time IS NULL;
                """;
        return this.jdbcClient.sql(sql)
                .query(BarkMessage.class)
                .list();
    }

    public @Nullable BarkMessage getById(Integer id) {
        var sql = """
                SELECT id, payload, ack_time, create_time, domain, comment
                FROM bark_message
                WHERE id = :id;
                """;
        return this.jdbcClient
                .sql(sql)
                .param("id", id)
                .query(BarkMessage.class)
                .optional()
                .orElse(null);
    }

    public void updateAckTime(@NotNull Integer id, @NotNull String time, @NotNull String comment) {
        var sql = """
                UPDATE bark_message
                SET ack_time = :time,
                    comment  = :comment
                WHERE id = :id;
                """;
        this.jdbcClient.sql(sql)
                .param("id", id)
                .param("time", time)
                .param("comment", comment)
                .update();
    }
}
