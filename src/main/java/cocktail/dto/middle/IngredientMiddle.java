package cocktail.dto.middle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientMiddle {

    private String size;
    private String type;
    private String name;
    private String note;
    private Boolean optional;

}
