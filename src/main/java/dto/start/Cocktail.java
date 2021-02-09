package dto.start;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Cocktail {

    private String name;
    private String association;
    private String type;
    private List<Ingredient> ingredients = new ArrayList<>();
    private String method;
    private String note;
    private String garnish;

}
