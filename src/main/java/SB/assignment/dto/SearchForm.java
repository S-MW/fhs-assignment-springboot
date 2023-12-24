package SB.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {

    private String fromDate;
    private String toDate;

    private Integer fromAmount;
    private Integer toAmount;

}
