package cocktail.dto.finish;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientFinish {

    private Boolean optional;
    private String size;
    private String typeRU;
    private String typeEN;

    @Expose
    private String nameRU;

    @Expose
    private String nameEN;

    public IngredientFinish(String nameRU, String nameEN) {
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }
}
