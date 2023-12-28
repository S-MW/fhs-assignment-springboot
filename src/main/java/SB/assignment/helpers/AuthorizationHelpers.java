package SB.assignment.helpers;

import static SB.assignment.constants.AssignmentConstants.RulesConstants.*;

public class AuthorizationHelpers {

    public static Boolean hasFilteringAuthorization(String sessionUserRuleName) {
        return sessionUserRuleName.equals(ADMIN.getRuleName()) ? Boolean.TRUE : Boolean.FALSE;

    }
}
