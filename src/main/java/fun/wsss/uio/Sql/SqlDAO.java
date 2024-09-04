package fun.wsss.uio.Sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Repository
public class SqlDAO {
    private static final Logger logger = LogManager.getLogger(SqlDAO.class);

    private final DataSource dataSource;

    public SqlDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> ResponseEntity<List<T>> Select(String sql, Function<ResultSet, T> value) {
        List<T> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(value.apply(rs));
            }
        } catch (Exception e) {
            logger.error("发生错误", e);
            return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.ok(results);
    }
}