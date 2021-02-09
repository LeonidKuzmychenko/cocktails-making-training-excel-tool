package cocktail.service;

import cocktail.dto.finish.CocktailFinish;
import cocktail.dto.middle.CocktailMiddle;
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

public class CocktailService {


    private final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public List<CocktailFinish> get() throws IOException {
        List<String> addSheetStrings = new SheetService().getList("./src/main/resources/cocktails.xlsx", 0);
        List<CocktailStart> cocktailsStart = getCocktailsStart(addSheetStrings);
        List<CocktailMiddle> cocktailsMiddle = transform(cocktailsStart);
        return cocktailsMiddle.stream()
                .map(CocktailMiddle::toFinish)
                .collect(Collectors.toList());
    }

    private List<CocktailStart> getCocktailsStart(List<String> allStrings) {
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

    private CocktailStart setData(CocktailStart cocktail, String text, LineType lineType) {
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

    private List<CocktailMiddle> transform(List<CocktailStart> cocktails) {
        return cocktails.stream().map(CocktailStart::toMiddle).collect(Collectors.toList());
    }
}
