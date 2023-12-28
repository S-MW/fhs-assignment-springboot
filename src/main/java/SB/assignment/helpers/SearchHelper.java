package SB.assignment.helpers;

import SB.assignment.dao.Statement;
import SB.assignment.dto.SearchForm;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SearchHelper {


    public static List<Statement> statementsFiltering(List<Statement> statements, SearchForm searchForm) {

        log.info("STARTED - statementsFiltering with data : " + searchForm.toString());

        List<Statement> statementsResult = statements.stream()
                .filter(statement -> {
                    if (searchForm.getFromAmount() == null || searchForm.getToAmount() == null) {
                        return true;
                    } else {
                        return statement.getAmount() >= searchForm.getFromAmount() && statement.getAmount() <= searchForm.getToAmount();
                    }
                })
                .filter(statement -> {
                    if (searchForm.getFromDate() == null || searchForm.getToDate() == null) {
                        return true;
                    } else {
                        LocalDate fromLocalDate = getLocalDateFormatByString(searchForm.getFromDate());
                        LocalDate toLocalDate = getLocalDateFormatByString(searchForm.getToDate());

                        return getLocalDateFormatByString(statement.getDatefield()).isAfter(fromLocalDate) || getLocalDateFormatByString(statement.getDatefield()).isEqual(fromLocalDate)
                                && getLocalDateFormatByString(statement.getDatefield()).isBefore(toLocalDate) || getLocalDateFormatByString(statement.getDatefield()).isEqual(toLocalDate);
                    }
                })
                .filter(statement -> {

                    if ((searchForm.getFromAmount() == null || searchForm.getToAmount() == null) && (searchForm.getFromDate() == null || searchForm.getToDate() == null)) {
                        LocalDate data = LocalDate.now().minusMonths(3);
                        return getLocalDateFormatByString(statement.getDatefield()).isAfter(data);
                    } else {
                        return true;
                    }
                })
                .collect(Collectors.toList());
        ;
        return statementsResult;
    }

    public static List<Statement> getStatementsByLastSelectedMonths(List<Statement> statements, int months) {

        List<Statement> statementsResult = statements.stream()
                .filter(statement -> {
                    LocalDate data = LocalDate.now().minusMonths(months);
                    return getLocalDateFormatByString(statement.getDatefield()).isAfter(data);
                })
                .collect(Collectors.toList());
        ;
        return statementsResult;
    }


    private static LocalDate getLocalDateFormatByString(String date) {
        String[] dateParts = date.split("\\."); // TODO: ADD VALIDATION with 3 parts
        return LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
    }
}
