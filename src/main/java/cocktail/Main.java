package cocktail;

import cocktail.dto.finish.CocktailFinish;
import cocktail.dto.finish.IngredientFinish;
import cocktail.dto.midle.CocktailMidle;
import cocktail.dto.midle.IngredientMidle;
import cocktail.dto.start.CocktailStart;
import cocktail.types.LineType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.utils.files.FileManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import utils.SheetService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        Sheet sheet = new SheetService().get("./src/main/resources/cocktails.xlsx", 0);
        List<CocktailStart> cocktailsDto = getCocktailsDto(sheet);
        List<CocktailMidle> cocktailMidles = transform(cocktailsDto);
        List<CocktailFinish> cocktailsFinish = cocktailMidles.stream().map(it -> {
            CocktailFinish cocktailFinish = new CocktailFinish();
            cocktailFinish.setNameRU(it.getName());
            cocktailFinish.setNameEN(it.getName());
            cocktailFinish.setAssociationRU(it.getAssociation());
            cocktailFinish.setAssociationEN(it.getAssociation());
            cocktailFinish.setTypeRU(it.getType());
            cocktailFinish.setTypeEN(it.getType());
            cocktailFinish.setMethodRU(it.getMethod());
            cocktailFinish.setMethodEN(it.getMethod());
            cocktailFinish.setNoteRU(it.getNote());
            cocktailFinish.setNoteEN(it.getNote());
            cocktailFinish.setGarnishRU(it.getGarnish());
            cocktailFinish.setGarnishEN(it.getGarnish());

            List<IngredientFinish> ingredientFinishes = it.getIngredientMidles().stream().map(itI -> {
                IngredientFinish ingredientFinish = new IngredientFinish();
                ingredientFinish.setSize(itI.getSize());
                ingredientFinish.setOptional(itI.getOptional());
                ingredientFinish.setTypeRU(itI.getType());
                ingredientFinish.setTypeEN(itI.getType());
                ingredientFinish.setNameRU(itI.getName());
                ingredientFinish.setNameEN(itI.getName());
                return ingredientFinish;
            }).collect(Collectors.toList());

            cocktailFinish.setIngredientFinishes(ingredientFinishes);
            return cocktailFinish;
        }).collect(Collectors.toList());

        String json = gson.toJson(cocktailsFinish);
        new FileManager().writeString("./src/main/resources/cocktails.json", json);
    }

    public static List<CocktailStart> getCocktailsDto(Sheet sheet) {
        List<CocktailStart> cocktails = new ArrayList<>();
        LineType lineType = LineType.NONE;
        CocktailStart cocktail = null;
        for (Row row : sheet) {
            for (Cell cell : row) {
                String text = cell.getStringCellValue();
                if (text.length() != 0) {
                    switch (text) {
                        case "Название":
                            lineType = LineType.Название;
                            break;
                        case "Ассоциация":
                            lineType = LineType.Ассоциация;
                            break;
                        case "Тип":
                            lineType = LineType.Тип;
                            break;
                        case "Ингредиенты":
                            lineType = LineType.Ингредиенты;
                            break;
                        case "Способ приготовления":
                            lineType = LineType.СпособПриготовления;
                            break;
                        case "Комментарий":
                            lineType = LineType.Комментарий;
                            break;
                        case "Украшение":
                            lineType = LineType.Украшение;
                            break;
                        default: {
                            cocktail = setData(cocktail, text, lineType);
                            if (lineType == LineType.Украшение) {
                                cocktails.add(cocktail);
                            }
                        }
                    }
                }
            }
        }
        return cocktails;
    }

    public static CocktailStart setData(CocktailStart cocktail, String text, LineType lineType) {
        switch (lineType) {
            case Название: {
                cocktail = new CocktailStart();
                cocktail.setName(text);
                break;
            }
            case Ассоциация: {
                cocktail.setAssociation(text);
                break;
            }
            case Тип: {
                cocktail.setType(text);
                break;
            }
            case Ингредиенты: {
                cocktail.addIngredient(text);
                break;
            }
            case СпособПриготовления: {
                cocktail.setMethod(text);
                break;
            }
            case Комментарий: {
                cocktail.setNote(text);
                break;
            }
            case Украшение: {
                cocktail.setGarnish(text);
                break;
            }
            default: {
                System.out.println("ERRRROOOOORRRR");
            }
        }
        return cocktail;
    }

    public static List<CocktailMidle> transform(List<CocktailStart> cocktails) {
        return cocktails.stream().map(it -> {
            List<String> oldIngredients = it.getIngredients();
            List<IngredientMidle> newIngredientMidles = new ArrayList<>();
            IngredientMidle ingredientMidle = null;
            for (int i = 1; i <= oldIngredients.size(); i++) {
                if (i % 3 == 1) {
                    ingredientMidle = new IngredientMidle();
                    ingredientMidle.setSize(oldIngredients.get(i - 1));
                } else if (i % 3 == 2) {
                    ingredientMidle.setType(oldIngredients.get(i - 1));
                } else if (i % 3 == 0) {
                    ingredientMidle.setName(oldIngredients.get(i - 1));
                    newIngredientMidles.add(ingredientMidle);
                }
            }

            CocktailMidle cocktailMidle1 = new CocktailMidle();
            cocktailMidle1.setName(it.getName());
            cocktailMidle1.setAssociation(it.getAssociation());
            cocktailMidle1.setType(it.getType());
            cocktailMidle1.setIngredientMidles(newIngredientMidles);
            cocktailMidle1.setMethod(it.getMethod());
            cocktailMidle1.setNote(it.getNote());
            cocktailMidle1.setGarnish(it.getGarnish());
            return cocktailMidle1;
        }).collect(Collectors.toList());
    }
}
