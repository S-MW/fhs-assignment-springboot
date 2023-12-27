package SB.assignment.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {

    private String fromDate;
    private String toDate;

    @Min(value = 1,message = "can't be less than 1")
    private Integer fromAmount;
    @Min(value = 1,message = "can't be less than 1")
    private Integer toAmount;

}
