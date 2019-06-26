package brzn.mtgadata.collection;

import java.math.BigDecimal;

public class CollectionProgress {

    private String setName;
    private BigDecimal percentCollected;

    public CollectionProgress(String setName, BigDecimal percentCollected) {
        this.setName = setName;
        this.percentCollected = percentCollected;
    }
}
