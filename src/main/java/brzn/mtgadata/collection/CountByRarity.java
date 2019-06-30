package brzn.mtgadata.collection;


import lombok.Getter;

public class CountByRarity {

    @Getter
    private int commons;

    @Getter
    private int uncommons;

    @Getter
    private int rares;

    @Getter
    private int mythics;

    public void addCommons(int num) {
        this.commons += num;
    }

    public void addUcommons(int num) {
        this.uncommons += num;
    }

    public void addRares(int num) {
        this.rares += num;
    }

    public void addMythics(int num) {
        this.mythics += num;
    }

    int getTotal() {
        return this.commons + this.uncommons + this.rares + this.mythics;
    }
}
