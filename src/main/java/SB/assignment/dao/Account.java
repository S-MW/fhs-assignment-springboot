package SB.assignment.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Integer id;
    private String account_type;
    private String account_number;

    private List<Statement> StatementsEntity = new ArrayList<>();





}
