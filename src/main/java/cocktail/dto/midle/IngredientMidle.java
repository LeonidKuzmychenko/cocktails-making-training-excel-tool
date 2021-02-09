package cocktail.dto.midle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientMidle {

    private String size;
    private String type;
    private String name;
    private String note;
    private Boolean optional;

}
