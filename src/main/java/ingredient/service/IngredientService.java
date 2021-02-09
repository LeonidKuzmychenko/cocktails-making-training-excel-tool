package ingredient.service;

import cocktail.dto.finish.IngredientFinish;
import utils.SheetService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientService {

    public List<IngredientFinish> get() throws IOException {
        List<String> excelIngredients = new SheetService().getList("./src/main/resources/cocktails.xlsx", 4);
        return excelIngredients.stream().map(it->new IngredientFinish(it,it)).collect(Collectors.toList());
    }
}