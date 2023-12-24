package SB.assignment.helpers;

import SB.assignment.controllers.Statement;
import SB.assignment.dto.SearchForm;

import java.util.List;
import java.util.stream.Collectors;

public class SearchHelper {


    public static List<Statement> StatementsFiltering(List<Statement> statements, SearchForm searchForm) {

        List<Statement> statementsResult = statements.stream()
                .filter(statement -> statement.getAmount() > searchForm.getFromAmount() && statement.getAmount() < searchForm.getToAmount())
//                .filter(statement -> statement.getAmount() > searchForm.getFromAmount() && statement.getAmount() < searchForm.getToAmount()) //TODO ( data ) Filtering
                .collect(Collectors.toList());
        ;

        return statementsResult;
    }
}
