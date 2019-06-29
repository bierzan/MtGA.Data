package brzn.mtgadata.card;

import lombok.Getter;

public final class CardCount {
    private Card card;

    @Getter
    private int count;

    public CardCount(Card card, int count) {
        this.card = card;
        this.count = count;
    }

    public boolean isFromSet(String setName) {
        return card.getSetName().equals(setName);
    }

    public Rarity getCardRarity(){
        return this.card.getRarity();
    }
}
