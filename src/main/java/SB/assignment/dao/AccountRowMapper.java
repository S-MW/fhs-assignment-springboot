package SB.assignment.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

        ArrayList<Statement> StatementList = new ArrayList<>();


        if (rs.getString("datefield") != null) {

            Statement statement = new Statement
                    (
                            rs.getInt("ID"),
                            rs.getInt("account_id"),
                            rs.getString("datefield"),
                            rs.getInt("amount")
                    );

            StatementList.add(statement);
        }


        return new Account
                (
                        rs.getInt("ID"),
                        rs.getString("account_type"),
                        rs.getString("account_number"),
                        StatementList
                );


    }
}
