package SB.assignment.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementRowMapper implements RowMapper<Statement> {
    @Override
    public Statement mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Statement(rs.getInt("ID"),rs.getInt("account_id"),rs.getString("datefield"),rs.getInt("amount"));
    }
}
