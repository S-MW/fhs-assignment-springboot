package SB.assignment.helpers;

import SB.assignment.exceptions.ApiRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static SB.assignment.constants.AssignmentConstants.RulesConstants;
import static SB.assignment.constants.AssignmentConstants.SystemConstants.RULE;
import static SB.assignment.constants.AssignmentConstants.SystemConstants.USER_DETAILS;

@Service
@Slf4j
public class UsersHelper {

    @Autowired
    private HttpServletRequest httpServletRequest;


    private final List<User> users = Arrays.asList(
            new User("admin", "admin", RulesConstants.ADMIN),
            new User("user", "user", RulesConstants.USER)
    );


    public Boolean isUserLogin() {
        if (httpServletRequest.getSession().getAttribute(RULE.getKeyName()) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void login(String username, String password) {

        Optional<User> user = users
                .stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();


        if (user.isPresent()) {
            log.info("new login with user : " + user.get().toString());
            httpServletRequest.getSession().setAttribute(USER_DETAILS.getKeyName(), user.get());
            httpServletRequest.getSession().setAttribute(RULE.getKeyName(), user.get().getRule().getRuleName());

        } else {
            throw new ApiRequestException("username or password is wrong!");
        }

    }

    public String getSessionUserRuleName() {
        return httpServletRequest.getSession().getAttribute(RULE.getKeyName()).toString();
    }


}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private String username;
    private String password;
    private RulesConstants rule;
}