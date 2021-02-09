package cocktail.dto.start;

import cocktail.dto.middle.CocktailMiddle;
import cocktail.dto.middle.IngredientMiddle;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CocktailStart {

    private String name;
    private String association;
    private String type;
    private List<String> ingredients = new ArrayList<>();
    private String method;
    private String note;
    private String garnish;

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public CocktailMiddle toMiddle() {
        List<IngredientMiddle> ingredientMiddles = new ArrayList<>();
        IngredientMiddle ingredientMiddle = null;
        for (int i = 1; i <= this.ingredients.size(); i++) {
            if (i % 3 == 1) {
                ingredientMiddle = new IngredientMiddle();
                ingredientMiddle.setSize(this.ingredients.get(i - 1));
            } else if (i % 3 == 2) {
                ingredientMiddle.setType(this.ingredients.get(i - 1));
            } else if (i % 3 == 0) {
                ingredientMiddle.setName(this.ingredients.get(i - 1));
                ingredientMiddles.add(ingredientMiddle);
            }
        }
        CocktailMiddle cocktailMiddle = new CocktailMiddle();
        cocktailMiddle.setName(this.name);
        cocktailMiddle.setAssociation(this.association);
        cocktailMiddle.setType(this.type);
        cocktailMiddle.setIngredientMiddles(ingredientMiddles);
        cocktailMiddle.setMethod(this.method);
        cocktailMiddle.setNote(this.note);
        cocktailMiddle.setGarnish(this.garnish);
        return cocktailMiddle;
    }
}
