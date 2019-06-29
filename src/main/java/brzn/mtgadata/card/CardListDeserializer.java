package brzn.mtgadata.card;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CardListDeserializer extends StdDeserializer<CardList> {

    public CardListDeserializer() {
        super(CardList.class);
    }


    public CardListDeserializer(Class<?> vc) {
        super(vc);
    }

    public CardListDeserializer(JavaType valueType) {
        super(valueType);
    }

    public CardListDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public CardList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        CardList cl = new CardList();
        List<Card> cards = new ArrayList<>();
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        cl.setHasMore(jsonNode.get("has_more").asBoolean());

        if (cl.hasMore()) {
            cl.setNextPage(jsonNode.get("next_page").asText());
        }

        JsonNode jsonCards = jsonNode.get("data");

        for (JsonNode node : jsonCards) {

            Card card = new Card();
            if (node.has("arena_id")) {
                card.setArenaId(node.get("arena_id").asLong());

                card.setName(node.get("name").asText());
                card.setType(node.get("type_line").asText());
                card.setRarity(Rarity.valueOf((node.get("rarity").asText())));
                card.setSetName(node.get("set_name").asText());
                if (node.has("image_uris")) {

                    JsonNode images = node.get("image_uris");
                    if (images.has("large")) {
                        card.setImageUrl(new URL(images.get("large").asText()));
                    } else if (images.has("normal")) {
                        card.setImageUrl(new URL(images.get("normal").asText()));
                    } else if (images.has("small")) {
                        card.setImageUrl(new URL(images.get("small").asText()));
                    }
                }
                card.setUri(new URL(node.get("uri").asText()));

                cards.add(card);
            }
        }

        cl.setCards(cards);
        return cl;

    }
}
