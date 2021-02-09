package cocktail.dto.midle;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CocktailMidle {

    private String name;
    private String association;
    private String type;
    private List<IngredientMidle> ingredientMidles = new ArrayList<>();
    private String method;
    private String note;
    private String garnish;

}
