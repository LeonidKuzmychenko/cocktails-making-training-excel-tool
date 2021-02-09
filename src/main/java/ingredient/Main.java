package ingredient;

import cocktail.dto.finish.IngredientFinish;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ingredient.service.IngredientService;
import utils.SheetService;
import lk.utils.files.FileManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        IngredientService ingredientService = new IngredientService();
        List<IngredientFinish> ingredients = ingredientService.get();
        String json = gson.toJson(ingredients);
        new FileManager().writeString("./src/main/resources/ingredients.json", json);
    }

}
