package cocktail.dto.finish;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientFinish {

    @Expose
    private Boolean optional;

    @Expose
    private String size;

    @Expose
    private String typeRU;

    @Expose
    private String typeEN;

    private String nameRU;
    private String nameEN;

    public IngredientFinish(String nameRU, String nameEN) {
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }
}
