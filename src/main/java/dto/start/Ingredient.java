package dto.start;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ingredient {

    private String size;
    private String type;
    private String name;
    private String note;
    private Boolean optional;

}
