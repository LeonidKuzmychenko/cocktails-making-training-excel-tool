package cocktail.help;

import cocktail.types.LineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineTypeValue {
    private LineType lineType;
    private String value;

    public boolean filter(){
        return lineType != LineType.NONE && lineType != null && value.trim().length() != 0;
    }
}
