package brzn.mtgadata.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = CardListDeserializer.class)
final class CardList {

    @Getter
    @Setter
    private String nextPage;

    private boolean hasMore = false;

    @Getter
    @Setter
    @JsonProperty("cards")
    private List<Card> cards = new ArrayList<>();

    boolean hasMore() {
        return hasMore;
    }

    void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
