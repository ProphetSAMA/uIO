package fun.wsss.uio.Service;

import fun.wsss.uio.Entity.Power;
import fun.wsss.uio.Sql.SqlDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

@Service
public class PowerService {

    private final SqlDAO sqlDAO;

    public PowerService(SqlDAO sqlDAO) {
        this.sqlDAO = sqlDAO;
    }

    public ResponseEntity<List<Power>> getPowerValue() {
        String sql = "SELECT power, querytime FROM powervaule";
        Function<ResultSet, Power> mapper = rs -> {
            try {
                return new Power(rs.getString("power"), rs.getString("querytime"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        return sqlDAO.Select(sql, mapper);
    }

}
