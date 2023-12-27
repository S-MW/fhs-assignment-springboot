package SB.assignment.controllers;


import SB.assignment.dao.DeoHelper;
import SB.assignment.dto.LoginForm;
import SB.assignment.dto.SearchForm;
import SB.assignment.exceptions.ApiRequestException;
import SB.assignment.exceptions.UnauthorizedException;
import SB.assignment.helpers.AuthorizationHelpers;
import SB.assignment.helpers.SearchHelper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/v1")
@Validated
public class TestController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    //TODO: respone missing filde if not sent it
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {

        // TODO : do the logic at service class

        // The user cannot login twice (the user should logout before login).
        if (httpServletRequest.getSession().getAttribute("rule") != null) {
            throw new ApiRequestException("user cannot login twice ( logout before login )"); // TODO : add massage as constent
        }


        // TODO : add enum/consetnt
        if (loginForm.getUsername().equals("admin") && loginForm.getPassword().equals("admin")) {
            httpServletRequest.getSession().setAttribute("rule", "admin");

        } else if (loginForm.getUsername().equals("user") && loginForm.getPassword().equals("user")) {
            httpServletRequest.getSession().setAttribute("rule", "user");

        } else {
            // throw : not found user
            throw new ApiRequestException("username or password is wrong!");
        }


        return ResponseEntity.ok().body("login success");
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout() {
        httpServletRequest.getSession().invalidate();
        return ResponseEntity.ok().body("logout done!");
    }


    @GetMapping("/statements/{accountId}")
    public Account getStatements(@PathVariable @Min(value = 1, message = "can't be less than 1") @Max(value = 5, message = "can't be more than 5") Integer accountId, @Valid @Nullable @RequestBody SearchForm searchForm) {
        System.out.println("getStatements START with accountId: " + accountId);

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


            if (searchForm != null ) {
                if (AuthorizationHelpers.hasFilteringAuthorization(httpServletRequest.getSession().getAttribute("rule").toString())) {
                    returnAccount.setStatementsEntity(SearchHelper.statementsFiltering(returnAccount.getStatementsEntity(), searchForm));
                } else {
                    throw new UnauthorizedException("unauthorized - unauthorized - unauthorized"); // TODO : 401
                }
            } else {
                returnAccount.setStatementsEntity(SearchHelper.getStatementsByLastSelectedMonths(returnAccount.getStatementsEntity(), 3)); // TODO: CONSTANT
            }

            return returnAccount;
    }


//    @GetMapping()
////    public List<Car> getHi() {
//    public List<Account> getHi() {
//
//        System.out.println("getHi START");
//        System.out.println("---");
//        System.out.println(httpServletRequest.getSession().getId());
//        System.out.println("---");
//        try {
////            var saveSql = """
////                INSERT INTO users(Field1,Field2) VALUES ("AAa","BBb");
////                """;
////            jdbcTemplate.update(saveSql);
//
//            var sql = """
//                    SELECT *
//                    FROM account;
//                    """;
//            return jdbcTemplate.query(sql, new CarRowMapper());
//
//        } catch (Exception e) {
//            System.out.println("Exception - Exception - Exception");
////            System.out.println(e.getCause());
//            System.out.println(e.getMessage());
//            return null;
//        }
//
//    }
//
//
//    @GetMapping("/statement")
////    public List<Car> getHi() {
//    public List<Statement> getHi2() {
//        System.out.println("getHi2 START");
//
//        try {
//
//            var sql = """
//                    SELECT *
//                    FROM statement;
//                    """;
//            return jdbcTemplate.query(sql, new StatementRowMapper());
//
//        } catch (Exception e) {
//            System.out.println("Exception - Exception - Exception");
////            System.out.println(e.getCause());
//            System.out.println(e.getMessage());
//            return null;
//        }
//
//    }


}
