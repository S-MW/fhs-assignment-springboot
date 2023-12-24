package SB.assignment.controllers;


import SB.assignment.dao.DeoHelper;
import SB.assignment.dto.LoginForm;
import SB.assignment.dto.SearchForm;
import SB.assignment.exceptions.ApiRequestException;
import SB.assignment.helpers.SearchHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/v1")
public class TestController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;


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
    public Account getStatements(@PathVariable Integer accountId, @RequestBody SearchForm searchForm) {
        System.out.println("getStatements START with accountId: " + accountId);

        try {

//            var sql = """
//                    SELECT *
//                    FROM statement WHERE account_id = ?;
//                    """;

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

            // must be only one account because the (  WHERE account.id = ? )
            Account returnAccount =  accountWithAllStatements.get(0);
            returnAccount.setStatementsEntity(SearchHelper.StatementsFiltering(returnAccount.getStatementsEntity(),searchForm)); // TODO ,add this feature for admin only


            return returnAccount;

//            return jdbcTemplate.query(sql, new AccountRowMapper(),accountId);

        } catch (Exception e) {
            System.out.println("Exception - Exception - Exception");
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            return null;
        }

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
