package ingredient;

import cocktail.dto.finish.IngredientFinish;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ingredient.service.IngredientService;
import lk.utils.files.FileManager;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        IngredientService is = new IngredientService();
        List<IngredientFinish> ingredientsFinishEN = is.get("./src/main/resources/excel/en/cocktailsEN.xlsx");
        List<IngredientFinish> ingredientsFinishRU = is.get("./src/main/resources/excel/ru/cocktailsRU.xlsx");

        List<IngredientFinish> ingredientsJoin = is.join(ingredientsFinishEN, ingredientsFinishRU);

        String json = gson.toJson(ingredientsJoin);
        new FileManager().writeString("./src/main/resources/db/ingredients.json", json);
    }

}
