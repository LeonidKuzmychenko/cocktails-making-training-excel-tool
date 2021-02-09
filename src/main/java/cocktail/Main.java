package cocktail;

import cocktail.dto.finish.CocktailFinish;
import cocktail.dto.finish.IngredientFinish;
import cocktail.dto.middle.CocktailMiddle;
import cocktail.dto.middle.IngredientMiddle;
import cocktail.dto.start.CocktailStart;
import cocktail.types.LineType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.utils.files.FileManager;
import utils.SheetService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        List<String> addSheetStrings = new SheetService().getList("./src/main/resources/cocktails.xlsx", 0);
        List<CocktailStart> cocktailsDto = getCocktailsDto(addSheetStrings);
        List<CocktailMiddle> cocktailMiddles = transform(cocktailsDto);
        List<CocktailFinish> cocktailsFinish = cocktailMiddles.stream().map(it -> {
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

            List<IngredientFinish> ingredientFinishes = it.getIngredientMiddles().stream().map(itI -> {
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

    public static List<CocktailStart> getCocktailsDto(List<String> allStrings) {
        allStrings = allStrings.stream().filter(s -> s.length() != 0).collect(Collectors.toList());

        List<CocktailStart> cocktails = new ArrayList<>();
        LineType lineType = LineType.NONE;
        CocktailStart cocktail = null;

        for (String text : allStrings) {
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

    public static List<CocktailMiddle> transform(List<CocktailStart> cocktails) {
        return cocktails.stream().map(CocktailStart::toMiddle).collect(Collectors.toList());
    }
}
