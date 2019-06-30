package brzn.mtgadata.collection;

import brzn.mtgadata.card.CardCount;
import brzn.mtgadata.card.CardService;
import brzn.mtgadata.card.Rarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetProgressService {

    private CardService cardService;

    @Autowired
    public SetProgressService(CardService cardService) {
        this.cardService = cardService;
    }

    public SetProgress getSetProgress(List<CardCount> collectionByCards, String set) {
        CountByRarity cbr = new CountByRarity();
        CountByRarity totalCbr = new CountByRarity();
        totalCbr.addCommons(cardService.countCardsBySetNameAndRarity(set, Rarity.COMMON) * 4);
        totalCbr.addUcommons(cardService.countCardsBySetNameAndRarity(set, Rarity.UNCOMMON) * 4);
        totalCbr.addRares(cardService.countCardsBySetNameAndRarity(set, Rarity.RARE) * 4);
        totalCbr.addMythics(cardService.countCardsBySetNameAndRarity(set, Rarity.MYTHIC) * 4);

        collectionByCards.stream()
                .filter(collectedCard -> collectedCard.isFromSet(set))
                .forEach(collectedCard -> {
                    switch (collectedCard.getCardRarity()) {
                        case COMMON:
                            cbr.addCommons(collectedCard.getCount());
                            break;
                        case UNCOMMON:
                            cbr.addUcommons(collectedCard.getCount());
                            break;
                        case RARE:
                            cbr.addRares(collectedCard.getCount());
                            break;
                        case MYTHIC:
                            cbr.addMythics(collectedCard.getCount());
                            break;
                    }
                });
        return SetProgress.generateProgress(set, cbr, totalCbr);
    }
}
