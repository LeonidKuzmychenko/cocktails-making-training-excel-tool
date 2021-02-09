package cocktail;

import cocktail.dto.finish.CocktailFinish;
import cocktail.service.CocktailService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.utils.files.FileManager;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        CocktailService cocktailService = new CocktailService();
        List<CocktailFinish> cocktailsFinish = cocktailService.get();
        String json = gson.toJson(cocktailsFinish);
        new FileManager().writeString("./src/main/resources/cocktails.json", json);
    }

}
