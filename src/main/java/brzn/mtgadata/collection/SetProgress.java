package brzn.mtgadata.collection;


import lombok.Getter;

public final class SetProgress {

    @Getter
    private String setName;

    @Getter
    private CountByRarity counted;

    @Getter
    private CountByRarity total;

    @Getter
    private PercentageByRarity progress;

    @Getter
    private int overallCount;

    @Getter
    private int overallTotal;

    public static SetProgress generateProgress(String setName, CountByRarity collectedCount, CountByRarity totalCount) {
        return new SetProgress(setName, collectedCount, totalCount);
    }

    private SetProgress(String setName, CountByRarity counted, CountByRarity total) {
        this.setName = setName;
        this.counted = counted;
        this.total = total;
        this.progress = PercentageByRarity.countPercentage(counted, total);
        this.overallCount = counted.getTotal();
        this.overallTotal = total.getTotal();
    }

}
