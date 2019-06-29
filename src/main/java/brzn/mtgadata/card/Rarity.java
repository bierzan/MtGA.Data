package brzn.mtgadata.card;

public enum Rarity {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    MYTHIC("mythic");

    private String rarityName;

    Rarity(String rarity) {
        this.rarityName = rarity;
    }

    public String getRarityName() {
        return rarityName;
    }
}
