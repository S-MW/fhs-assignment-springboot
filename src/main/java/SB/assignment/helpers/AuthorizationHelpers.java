package SB.assignment.helpers;

public class AuthorizationHelpers {

    public static Boolean hasFilteringAuthorization(String sessionUserRuleName) {
        return sessionUserRuleName.equals("admin") ? Boolean.TRUE : Boolean.FALSE;

    }
}
