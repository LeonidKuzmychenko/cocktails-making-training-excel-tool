package dto.finish;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientFinish {

    private String size;
    private String typeRU;
    private String typeEN;
    private String nameRU;
    private String nameEN;
    private Boolean optional;

}
