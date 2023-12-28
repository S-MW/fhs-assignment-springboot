package SB.assignment.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface AssignmentConstants {


    @RequiredArgsConstructor
    @Getter
    public enum SystemConstants {

        RULE("rule"),
        USER_DETAILS("user_details");

        private final String keyName;

    }

    @RequiredArgsConstructor
    @Getter
    public enum RulesConstants {

        ADMIN("admin"),
        USER("user");

        private final String ruleName;

    }

    @RequiredArgsConstructor
    @Getter
    public enum ErrorsConstants {

        PLEASE_LOGIN_IN_FIRST("Please login in first");

        private final String message;

    }

    @RequiredArgsConstructor
    @Getter
    public enum SuccessConstants {

        LOGGED_IN_SUCCESSFULLY("Logged in successfully"),
        LOGOUT_IN_SUCCESSFULLY("Logout in successfully");

        private final String message;

    }
}
