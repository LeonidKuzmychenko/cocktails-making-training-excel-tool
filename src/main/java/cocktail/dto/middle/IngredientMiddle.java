package cocktail.dto.middle;

import cocktail.dto.finish.IngredientFinish;
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

    public IngredientFinish toFinish(){
        IngredientFinish ingredientFinish = new IngredientFinish();
        ingredientFinish.setSize(this.size);
        ingredientFinish.setOptional(this.optional);
        ingredientFinish.setTypeRU(this.type);
        ingredientFinish.setTypeEN(this.type);
        ingredientFinish.setNameRU(this.name);
        ingredientFinish.setNameEN(this.name);
        return ingredientFinish;
    }
}
