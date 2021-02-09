package cocktail.dto.middle;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CocktailMiddle {

    private String name;
    private String association;
    private String type;
    private List<IngredientMiddle> ingredientMiddles = new ArrayList<>();
    private String method;
    private String note;
    private String garnish;

}
