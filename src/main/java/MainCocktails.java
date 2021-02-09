import com.google.gson.GsonBuilder;
import dto.finish.CocktailFinish;
import dto.finish.IngredientFinish;
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
import java.util.stream.Collectors;

public class MainCocktails {

    public static void main(String[] args) throws IOException {
        Sheet sheet = new SheetService().get("./src/main/resources/cocktails.xlsx", 0);
        List<CocktailDto> cocktailsDto = getCocktailsDto(sheet);
        List<Cocktail> cocktails = transform(cocktailsDto);
        List<CocktailFinish> cocktailsFinish = cocktails.stream().map(it->{
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
        File file = new File("./src/main/resources/cocktails.json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();
    }

    public static List<CocktailDto> getCocktailsDto(Sheet sheet) {
        List<CocktailDto> cocktails = new ArrayList<>();
        State state = State.NONE;
        CocktailDto cocktail = null;
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

    public static CocktailDto setData(CocktailDto cocktail, String text, State state) {
        switch (state) {
            case Название: {
                cocktail = new CocktailDto();
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

    public static List<Cocktail> transform(List<CocktailDto> cocktails) {
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

            Cocktail cocktail1 = new Cocktail();
            cocktail1.setName(it.getName());
            cocktail1.setAssociation(it.getAssociation());
            cocktail1.setType(it.getType());
            cocktail1.setIngredients(newIngredients);
            cocktail1.setMethod(it.getMethod());
            cocktail1.setNote(it.getNote());
            cocktail1.setGarnish(it.getGarnish());
            return cocktail1;
        }).collect(Collectors.toList());
    }
}
