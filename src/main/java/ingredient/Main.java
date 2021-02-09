package ingredient;

import cocktail.dto.finish.IngredientFinish;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        Sheet sheetIngredients = new SheetService().get("./src/main/resources/cocktails.xlsx", 4);
        List<String> allIngredients = new ArrayList<>();
        for (Row row : sheetIngredients) {
            for (Cell cell : row) {
                allIngredients.add(cell.getStringCellValue());
            }
        }

        List<IngredientFinish> ingredientToJsons = allIngredients.stream().map(it->new IngredientFinish(it,it)).collect(Collectors.toList());
        String json = gson.toJson(ingredientToJsons);
        System.out.println(json);

        new FileManager().writeString("./src/main/resources/ingredients.json", json);
    }

}
