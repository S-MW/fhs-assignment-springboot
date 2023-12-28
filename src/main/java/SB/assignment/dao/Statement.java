package SB.assignment.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statement {

    private Integer id;
    private Integer account_id;
    private String datefield;
    private Integer amount;

}
