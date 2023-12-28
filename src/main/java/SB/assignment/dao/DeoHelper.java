package SB.assignment.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DeoHelper {


    public static List<Account> removedRedundant(List<Account> accounts) {


        HashMap<String, Account> stringAccountHashMap = new HashMap<>();
        for (Account account : accounts) {
            if (!stringAccountHashMap.containsKey(account.getId().toString())) {
                stringAccountHashMap.put(account.getId().toString(), account);
            } else {
                // Already exist
                Account account1 = stringAccountHashMap.get(account.getId().toString());
                ArrayList<Statement> allStatements = new ArrayList<>();
                allStatements.addAll(account1.getStatementsEntity());
                allStatements.add(account.getStatementsEntity().get(0));
                account1.setStatementsEntity(allStatements);

                stringAccountHashMap.remove(account.getId().toString());
                stringAccountHashMap.put(account.getId().toString(), account1);
            }
        }

        ArrayList<Account> returnEmp = stringAccountHashMap.values().stream().collect(
                Collectors.toCollection(ArrayList::new));

        return returnEmp;
    }
}
