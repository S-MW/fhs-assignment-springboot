package SB.assignment.controllers;

import SB.assignment.dao.Account;
import SB.assignment.dto.LoginForm;
import SB.assignment.dto.SearchForm;
import SB.assignment.services.AssignmentService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1")
@Validated
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;


    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginForm loginForm) {
        return assignmentService.login(loginForm);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout() {
        return assignmentService.logout();
    }


    @GetMapping("/statements/{accountId}")
    public Account getStatements
            (
                    @PathVariable
                    @Min(value = 1, message = "can't be less than 1")
                    @Max(value = 5, message = "can't be more than 5") Integer accountId,

                    @Valid
                    @Nullable
                    @RequestBody SearchForm searchForm
            ) {
        return assignmentService.getStatements(accountId, searchForm);
    }


}
