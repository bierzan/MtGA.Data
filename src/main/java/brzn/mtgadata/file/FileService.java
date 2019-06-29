package brzn.mtgadata.file;

import brzn.mtgadata.card.Card;
import brzn.mtgadata.card.CardCount;
import brzn.mtgadata.card.CardRepo;
import brzn.mtgadata.card.Rarity;
import brzn.mtgadata.collection.CountByRarity;
import brzn.mtgadata.collection.SetProgress;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class FileService {

    private CardRepo cardRepo;

    @Autowired
    public FileService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    public List<SetProgress> getCollectionProgress(MultipartFile file) throws IOException {
        String data = IOUtils.toString(file.getInputStream(), Charset.forName("UTF-8"));
        Map<Long, Integer> collectionRaw = getContentFromFile(data);
        List<CardCount> collectionByCards = new ArrayList<>();
        Set<String> cardSetNames = new HashSet<>();
        List<SetProgress> progressesBySet = new ArrayList<>();

        for (Long cardId : collectionRaw.keySet()) {
            Card card = cardRepo.findOneByArenaId(cardId);
            CardCount cc = new CardCount(card, collectionRaw.get(cardId));
            collectionByCards.add(cc);
            cardSetNames.add(card.getSetName());
        }

        for (String set : cardSetNames) {
            CountByRarity cbr = new CountByRarity();
            CountByRarity totalCbr = new CountByRarity();
            totalCbr.addCommons(cardRepo.countCardsBySetNameAndRarity(set, Rarity.COMMON) * 4);
            totalCbr.addCommons(cardRepo.countCardsBySetNameAndRarity(set, Rarity.UNCOMMON) * 4);
            totalCbr.addCommons(cardRepo.countCardsBySetNameAndRarity(set, Rarity.RARE) * 4);
            totalCbr.addCommons(cardRepo.countCardsBySetNameAndRarity(set, Rarity.MYTHIC) * 4);

            collectionByCards.stream()
                    .filter(collectedCard -> collectedCard.isFromSet(set))
                    .forEach(collectedCard -> {
                        switch (collectedCard.getCardRarity()) {
                            case COMMON:
                                cbr.addCommons(collectedCard.getCount());
                            case UNCOMMON:
                                cbr.addUcommons(collectedCard.getCount());
                            case RARE:
                                cbr.addRares(collectedCard.getCount());
                            case MYTHIC:
                                cbr.addMythics(collectedCard.getCount());
                        }
                    });
            progressesBySet.add(SetProgress.generateProgress(set, cbr, totalCbr));
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
