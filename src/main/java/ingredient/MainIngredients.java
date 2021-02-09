package ingredient;

import com.google.gson.GsonBuilder;
import utils.SheetService;
import lk.utils.files.FileManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainIngredients {

    public static void main(String[] args) throws IOException {
        Sheet sheetIngredients = new SheetService().get("./src/main/resources/cocktails.xlsx", 4);
        List<String> allIngredients = new ArrayList<>();
        for (Row row : sheetIngredients) {
            for (Cell cell : row) {
                allIngredients.add(cell.getStringCellValue());
            }
        }

        List<IngredientFile> ingredientFiles = allIngredients.stream().map(it->new IngredientFile(it,it)).collect(Collectors.toList());
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(ingredientFiles);
        System.out.println(json);

        new FileManager().writeString("./src/main/resources/ingredients.json", json);
    }

}
