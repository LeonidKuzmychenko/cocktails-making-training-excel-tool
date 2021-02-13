package cocktail;

import cocktail.dto.finish.CocktailFinish;
import cocktail.service.CocktailService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.utils.files.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void main(String[] args) throws IOException {
        CocktailService cs = new CocktailService();
        List<CocktailFinish> cocktailsFinishEN = cs.get("./src/main/resources/excel/en/cocktailsEN.xlsx");
        List<CocktailFinish> cocktailsFinishRU = cs.get("./src/main/resources/excel/ru/cocktailsRU.xlsx");

        List<CocktailFinish> cocktailsFinish = new ArrayList<>(cocktailsFinishEN);

        for (int i = 0; i < cocktailsFinish.size(); i++) {
            CocktailFinish finish = cocktailsFinish.get(i);
            CocktailFinish ru = cocktailsFinishRU.get(i);
            finish.setNameRU(ru.getNameRU());
            finish.setAssociationRU(ru.getAssociationRU());
            finish.setMethodRU(ru.getMethodRU());
            finish.setNoteRU(ru.getNoteRU());
            finish.setGarnishRU(ru.getGarnishRU());
            finish.setTypeRU(ru.getTypeRU());
        }

        String json = gson.toJson(cocktailsFinish);
        new FileManager().writeString("./src/main/resources/db/cocktails.json", json);
    }

}
