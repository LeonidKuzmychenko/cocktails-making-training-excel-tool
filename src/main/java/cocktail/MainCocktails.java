package cocktail;

import com.google.gson.GsonBuilder;
import cocktail.dto.finish.CocktailFinish;
import cocktail.dto.finish.IngredientFinish;
import cocktail.dto.start.CocktailMidle;
import cocktail.dto.start.CocktailStart;
import cocktail.dto.start.Ingredient;
import utils.SheetService;
import lk.utils.files.FileManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import cocktail.types.State;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainCocktails {

    public static void main(String[] args) throws IOException {
        Sheet sheet = new SheetService().get("./src/main/resources/cocktails.xlsx", 0);
        List<CocktailStart> cocktailsDto = getCocktailsDto(sheet);
        List<CocktailMidle> cocktailMidles = transform(cocktailsDto);
        List<CocktailFinish> cocktailsFinish = cocktailMidles.stream().map(it->{
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

            List<IngredientFinish> ingredientFinishes = it.getIngredients().stream().map(itI->{
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

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(cocktailsFinish);
        new FileManager().writeString("./src/main/resources/cocktails.json", json);
    }

    public static List<CocktailStart> getCocktailsDto(Sheet sheet) {
        List<CocktailStart> cocktails = new ArrayList<>();
        State state = State.NONE;
        CocktailStart cocktail = null;
        for (Row row : sheet) {
            for (Cell cell : row) {
                String text = cell.getStringCellValue();
                if (text.length() != 0) {
                    switch (text) {
                        case "Название":
                            state = State.Название;
                            break;
                        case "Ассоциация":
                            state = State.Ассоциация;
                            break;
                        case "Тип":
                            state = State.Тип;
                            break;
                        case "Ингредиенты":
                            state = State.Ингредиенты;
                            break;
                        case "Способ приготовления":
                            state = State.СпособПриготовления;
                            break;
                        case "Комментарий":
                            state = State.Комментарий;
                            break;
                        case "Украшение":
                            state = State.Украшение;
                            break;
                        default: {
                            cocktail = setData(cocktail, text, state);
                            if (state == State.Украшение) {
                                cocktails.add(cocktail);
                            }
                        }
                    }
                }
            }
        }
        return cocktails;
    }

    public static CocktailStart setData(CocktailStart cocktail, String text, State state) {
        switch (state) {
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
            List<Ingredient> newIngredients = new ArrayList<>();
            Ingredient ingredient = null;
            for (int i = 1; i <= oldIngredients.size(); i++) {
                if (i % 3 == 1) {
                    ingredient = new Ingredient();
                    ingredient.setSize(oldIngredients.get(i - 1));
                } else if (i % 3 == 2) {
                    ingredient.setType(oldIngredients.get(i - 1));
                } else if (i % 3 == 0) {
                    ingredient.setName(oldIngredients.get(i - 1));
                    newIngredients.add(ingredient);
                }
            }

            CocktailMidle cocktailMidle1 = new CocktailMidle();
            cocktailMidle1.setName(it.getName());
            cocktailMidle1.setAssociation(it.getAssociation());
            cocktailMidle1.setType(it.getType());
            cocktailMidle1.setIngredients(newIngredients);
            cocktailMidle1.setMethod(it.getMethod());
            cocktailMidle1.setNote(it.getNote());
            cocktailMidle1.setGarnish(it.getGarnish());
            return cocktailMidle1;
        }).collect(Collectors.toList());
    }
}
