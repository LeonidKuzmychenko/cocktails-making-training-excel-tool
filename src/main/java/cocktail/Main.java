package cocktail;

import cocktail.dto.finish.CocktailFinish;
import cocktail.dto.middle.CocktailMiddle;
import cocktail.dto.start.CocktailStart;
import cocktail.service.CocktailService;
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
        CocktailService cocktailService = new CocktailService();
        List<CocktailFinish> cocktailsFinish = cocktailService.get();
        String json = gson.toJson(cocktailsFinish);
        new FileManager().writeString("./src/main/resources/cocktails.json", json);
    }

}
