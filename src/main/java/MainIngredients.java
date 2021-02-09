import com.google.gson.GsonBuilder;
import dto.finish.CocktailFinish;
import dto.finish.IngredientFinish;
import dto.finish.file.IngredientFile;
import dto.start.Cocktail;
import dto.start.CocktailDto;
import dto.start.Ingredient;
import excel.SheetService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import types.State;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
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

        File file = new File("./src/main/resources/ingredients.json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();
    }

}
