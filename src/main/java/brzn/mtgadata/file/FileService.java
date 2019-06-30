package brzn.mtgadata.file;

import brzn.mtgadata.card.Card;
import brzn.mtgadata.card.CardCount;
import brzn.mtgadata.card.CardService;
import brzn.mtgadata.collection.SetProgress;
import brzn.mtgadata.collection.SetProgressService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class FileService {

    private CardService cardService;
    private SetProgressService progressService;

    @Autowired
    public FileService(CardService cardService, SetProgressService progressService) {
        this.cardService = cardService;
        this.progressService = progressService;
    }

    public List<SetProgress> getCollectionProgress(MultipartFile file) throws IOException {
        String data = IOUtils.toString(file.getInputStream(), Charset.forName("UTF-8"));
        Map<Long, Integer> collectionRaw = getContentFromFile(data);

        List<CardCount> collectionByCards = new ArrayList<>();
        Set<String> cardSetNames = new HashSet<>();
        List<SetProgress> progressesBySet = new ArrayList<>();

        for (Long cardId : collectionRaw.keySet()) {
            Card card = cardService.findOneByArenaId(cardId);
            CardCount cc = new CardCount(card, collectionRaw.get(cardId));
            collectionByCards.add(cc);
            cardSetNames.add(card.getSetName());
        }

        for (String set : cardSetNames) {
            SetProgress sp = progressService.getSetProgress(collectionByCards, set);
            progressesBySet.add(sp);
        }
        return progressesBySet;
    }

    Map<Long, Integer> getContentFromFile(String data) {
        boolean stop = false;
        Map<Long, Integer> cards = new HashMap<>();

        Scanner sc = new Scanner(data);

        while (!stop || sc.hasNextLine()) {
            if (sc.nextLine().equals("<== PlayerInventory.GetPlayerCardsV3(12)")) {
                sc.nextLine();
                String line = sc.nextLine();
                do {
                    String[] temp = line.split("(\": )|( *\")|,");
                    cards.put(Long.valueOf(temp[1]), Integer.valueOf(temp[2]));
                    line = sc.nextLine();
                } while (!line.equals("}"));
                stop = true;
            }
        }
        return cards;
    }
}
