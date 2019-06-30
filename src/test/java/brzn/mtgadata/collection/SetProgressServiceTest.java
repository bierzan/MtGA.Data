package brzn.mtgadata.collection;

import brzn.mtgadata.card.Card;
import brzn.mtgadata.card.CardCount;
import brzn.mtgadata.card.CardService;
import brzn.mtgadata.card.Rarity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SetProgressServiceTest {

    private String set = "cardSet";
    private String set2 = "cardSet2";
    private String set3 = "cardSet3";

    @Mock
    private CardService cardService;

    @InjectMocks
    private SetProgressService progressService;

    @Test
    public void shouldGetSetProgress() {

        List<CardCount> cards = new ArrayList<>();
        fillCardCountList(cards);
        when(cardService.countCardsBySetNameAndRarity(any(String.class), any(Rarity.class))).thenReturn(5);
        SetProgress sp = progressService.getSetProgress(cards, set);

        assertThat(sp.getSetName(), equalTo(set));
        assertThat(sp.getCounted().getCommons(), equalTo(10));
        assertThat(sp.getCounted().getUncommons(), equalTo(10));
        assertThat(sp.getCounted().getRares(), equalTo(6));
        assertThat(sp.getCounted().getMythics(), equalTo(10));
        assertThat(sp.getOverallCount(), equalTo(36));
        assertThat(sp.getOverallTotal(), equalTo(80));
    }

    private void fillCardCountList(List<CardCount> cards) {
        cards.add(makeCollectedCard(set, Rarity.COMMON, 1));
        cards.add(makeCollectedCard(set, Rarity.COMMON, 2));
        cards.add(makeCollectedCard(set, Rarity.COMMON, 3));
        cards.add(makeCollectedCard(set, Rarity.COMMON, 4));
        cards.add(makeCollectedCard(set2, Rarity.COMMON, 1));
        cards.add(makeCollectedCard(set2, Rarity.COMMON, 4));
        cards.add(makeCollectedCard(set3, Rarity.COMMON, 2));

        cards.add(makeCollectedCard(set, Rarity.UNCOMMON, 1));
        cards.add(makeCollectedCard(set, Rarity.UNCOMMON, 2));
        cards.add(makeCollectedCard(set, Rarity.UNCOMMON, 3));
        cards.add(makeCollectedCard(set2, Rarity.UNCOMMON, 4));
        cards.add(makeCollectedCard(set, Rarity.UNCOMMON, 4));
        cards.add(makeCollectedCard(set2, Rarity.UNCOMMON, 3));
        cards.add(makeCollectedCard(set3, Rarity.UNCOMMON, 2));
        cards.add(makeCollectedCard(set3, Rarity.UNCOMMON, 2));

        cards.add(makeCollectedCard(set, Rarity.RARE, 1));
        cards.add(makeCollectedCard(set, Rarity.RARE, 2));
        cards.add(makeCollectedCard(set, Rarity.RARE, 3));
        cards.add(makeCollectedCard(set2, Rarity.RARE, 1));
        cards.add(makeCollectedCard(set2, Rarity.RARE, 4));
        cards.add(makeCollectedCard(set3, Rarity.RARE, 3));
        cards.add(makeCollectedCard(set3, Rarity.RARE, 2));

        cards.add(makeCollectedCard(set, Rarity.MYTHIC, 1));
        cards.add(makeCollectedCard(set, Rarity.MYTHIC, 2));
        cards.add(makeCollectedCard(set, Rarity.MYTHIC, 3));
        cards.add(makeCollectedCard(set, Rarity.MYTHIC, 4));
        cards.add(makeCollectedCard(set2, Rarity.MYTHIC, 4));
        cards.add(makeCollectedCard(set3, Rarity.MYTHIC, 2));
        cards.add(makeCollectedCard(set3, Rarity.MYTHIC, 1));
    }

    private CardCount makeCollectedCard(String set, Rarity rarity, int quantity) {
        Card card = new Card();
        card.setSetName(set);
        card.setRarity(rarity);
        return new CardCount(card, quantity);
    }
}