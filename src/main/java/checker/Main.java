package checker;

import cocktail.dto.finish.IngredientFinish;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ingredient.service.IngredientService;
import lk.utils.files.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        IngredientService is = new IngredientService();
        List<IngredientFinish> ingredientsFinishEN = is.get("./src/main/resources/excel/en/cocktailsEN.xlsx");
        List<IngredientFinish> ingredientsFinishRU = is.get("./src/main/resources/excel/ru/cocktailsEN.xlsx");

        List<IngredientFinish> ingredientsFinish = new ArrayList<>(ingredientsFinishEN);

        for (int i = 0; i < ingredientsFinish.size(); i++) {
            IngredientFinish finish = ingredientsFinish.get(i);
            IngredientFinish ru = ingredientsFinishRU.get(i);
            finish.setNameRU(ru.getNameRU());
        }

        String json = gson.toJson(ingredientsFinish);
        new FileManager().writeString("./src/main/resources/db/ingredients.json", json);
    }

}
