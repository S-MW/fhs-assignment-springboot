package SB.assignment.services;

import SB.assignment.constants.AssignmentConstants;
import SB.assignment.dao.Account;
import SB.assignment.dao.AccountRowMapper;
import SB.assignment.dao.DeoHelper;
import SB.assignment.dto.LoginForm;
import SB.assignment.dto.SearchForm;
import SB.assignment.exceptions.ApiRequestException;
import SB.assignment.exceptions.UnauthorizedException;
import SB.assignment.helpers.AuthorizationHelpers;
import SB.assignment.helpers.SearchHelper;
import SB.assignment.helpers.UsersHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

import static SB.assignment.constants.AssignmentConstants.SuccessConstants.LOGGED_IN_SUCCESSFULLY;
import static SB.assignment.constants.AssignmentConstants.SuccessConstants.LOGOUT_IN_SUCCESSFULLY;
import static SB.assignment.constants.AssignmentConstants.SystemConstants.USER_DETAILS;

@Service
@Slf4j
public class AssignmentService {

    @Autowired
    private HttpServletRequest httpServletRequest;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersHelper usersHelper;


    public ResponseEntity<Object> login(LoginForm loginForm) {

        if (usersHelper.isUserLogin()) {

            throw new ApiRequestException("user cannot login twice ( logout before login )"); // TODO : constant
        }
        usersHelper.login(loginForm.getUsername(), loginForm.getPassword());
        return ResponseEntity.ok().body(Map.of("message", LOGGED_IN_SUCCESSFULLY.getMessage()));
    }


    public ResponseEntity<Object> logout() {
        log.info("new logout with user : " + httpServletRequest.getSession().getAttribute(USER_DETAILS.getKeyName()));
        httpServletRequest.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("message", LOGOUT_IN_SUCCESSFULLY.getMessage()));

    }


    public Account getStatements(Integer accountId, SearchForm searchForm) {

        var sql = """
                 SELECT account.*, 
                        statement.ID,
                        statement.account_id,
                        statement.datefield,
                        statement.amount
                FROM account
                LEFT JOIN statement  on account.id = statement.account_id
                WHERE account.id = ?
                  """;

        List<Account> accountWithAllStatements = DeoHelper.removedRedundant(jdbcTemplate.query(sql, new AccountRowMapper(), accountId));

        // must be only one account because the ( WHERE account.id = ? )
        Account returnAccount = accountWithAllStatements.get(0);
        returnAccount.setAccount_number(passwordEncoder.encode(returnAccount.getAccount_number()));


        if (searchForm != null) {
            if (AuthorizationHelpers.hasFilteringAuthorization(usersHelper.getSessionUserRuleName())) {
                returnAccount.setStatementsEntity(SearchHelper.statementsFiltering(returnAccount.getStatementsEntity(), searchForm));
            } else {
                throw new UnauthorizedException("unauthorized");
            }
        } else {
            returnAccount.setStatementsEntity(SearchHelper.getStatementsByLastSelectedMonths(returnAccount.getStatementsEntity(), 3)); // TODO: CONSTANT
        }

        return returnAccount;
    }

}
