package checker;

import cocktail.dto.finish.CocktailFinish;
import cocktail.dto.finish.IngredientFinish;
import cocktail.service.CocktailService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ingredient.service.IngredientService;
import lk.utils.files.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        List<IngredientFinish> ingredients = new IngredientService()
                .get("./src/main/resources/excel/en/cocktailsEN.xlsx");

        Set<String> ingredientsSet =  ingredients.stream()
                .map(IngredientFinish::getNameEN)
                .collect(Collectors.toSet());

        List<CocktailFinish> cocktails = new CocktailService()
                .get("./src/main/resources/excel/en/cocktailsEN.xlsx");

        Set<String> cocktailsIngredientsSet = cocktails.stream()
                .map(CocktailFinish::getIngredientFinishes)
                .flatMap(Collection::stream)
                .map(IngredientFinish::getNameEN)
                .collect(Collectors.toSet());

        System.out.println(ingredientsSet.size());
        System.out.println(cocktailsIngredientsSet.size());


        List<String> ingredientsList = new ArrayList<>(ingredientsSet).stream()
                .sorted()
                .collect(Collectors.toList());

        List<String> cocktailsIngredientsList = new ArrayList<>(cocktailsIngredientsSet).stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.println();
        for (int i = 0; i < ingredientsList.size(); i++) {
            String ingr1 = ingredientsList.get(i);
            String ingr2 = cocktailsIngredientsList.get(i);

            boolean isOk = ingr1.equals(ingr2);

            System.out.print(ingr1 + " - " + ingr2);
            System.out.println(" (" + isOk + ")");

            if(!isOk){
                for (int j = 0; j < 30; j++) {
                    System.out.println();
                }
            }
        }
    }

}
