package ingredient.service;

import cocktail.dto.finish.IngredientFinish;
import utils.SheetService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientService {

    public List<IngredientFinish> get(String path) throws IOException {
        List<String> excelIngredients = new SheetService().getList(path, 4);
//        List<String> excelIngredients = new SheetService().getList("./src/main/resources/excel/cocktailsEN.xlsx", 4);
        return excelIngredients.stream().map(it -> new IngredientFinish(it, it)).collect(Collectors.toList());
    }

    public List<IngredientFinish> join(List<IngredientFinish> to, List<IngredientFinish> from) {
        List<IngredientFinish> ingredientsJoin = new ArrayList<>(to);
        for (int i = 0; i < ingredientsJoin.size(); i++) {
            IngredientFinish ru = from.get(i);
            ingredientsJoin.get(i).setNameRU(ru.getNameRU());
        }
        return ingredientsJoin;
    }
}
