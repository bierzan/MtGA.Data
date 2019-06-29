package brzn.mtgadata.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;

@Service
@Transactional
public class CardService {
    private CardRepo cardRepo;

    @Autowired
    public CardService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    public void updateCardDatabase() throws IOException {
        String api = "https://api.scryfall.com/cards/search?q=game%3Aarena";
        CardList cl;

        do {
            cl = mapToCardListClassFromAPI(api);
            cl.getCards().forEach(card -> cardRepo.save(card));
            api = cl.getNextPage();
        } while (cl.hasMore());
    }

    private CardList mapToCardListClassFromAPI(String apiUrl) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(apiUrl), CardList.class);
    }

}
