package cocktail.dto.finish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CocktailFinish {

    @Expose
    private String nameRU;

    @Expose
    private String nameEN;

    @Expose
    private String associationRU;

    @Expose
    private String associationEN;

    @Expose
    private String typeRU;

    @Expose
    private String typeEN;

    @Expose
    @SerializedName("ingredients")
    private List<IngredientFinish> ingredientFinishes = new ArrayList<>();

    @Expose
    private String methodRU;

    @Expose
    private String methodEN;

    @Expose
    private String noteRU;

    @Expose
    private String noteEN;

    @Expose
    private String garnishRU;

    @Expose
    private String garnishEN;

}
