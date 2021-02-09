package dto.finish;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CocktailFinish {

    private String nameRU;
    private String nameEN;
    private String associationRU;
    private String associationEN;
    private String typeRU;
    private String typeEN;
    @SerializedName("ingredients")
    private List<IngredientFinish> ingredientFinishes = new ArrayList<>();
    private String methodRU;
    private String methodEN;
    private String noteRU;
    private String noteEN;
    private String garnishRU;
    private String garnishEN;

}
