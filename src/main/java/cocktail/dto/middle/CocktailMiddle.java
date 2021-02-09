package cocktail.dto.middle;

import cocktail.dto.finish.CocktailFinish;
import cocktail.dto.finish.IngredientFinish;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public CocktailFinish toFinish(){
        CocktailFinish cocktailFinish = new CocktailFinish();
        cocktailFinish.setNameRU(this.name);
        cocktailFinish.setNameEN(this.name);
        cocktailFinish.setAssociationRU(this.association);
        cocktailFinish.setAssociationEN(this.association);
        cocktailFinish.setTypeRU(this.type);
        cocktailFinish.setTypeEN(this.type);
        cocktailFinish.setMethodRU(this.method);
        cocktailFinish.setMethodEN(this.method);
        cocktailFinish.setNoteRU(this.note);
        cocktailFinish.setNoteEN(this.note);
        cocktailFinish.setGarnishRU(this.garnish);
        cocktailFinish.setGarnishEN(this.garnish);

        List<IngredientFinish> ingredientFinishes = ingredientMiddles.stream()
                .map(IngredientMiddle::toFinish)
                .collect(Collectors.toList());

        cocktailFinish.setIngredientFinishes(ingredientFinishes);
        return cocktailFinish;
    }
}
