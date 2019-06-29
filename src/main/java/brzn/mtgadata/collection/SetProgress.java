package brzn.mtgadata.collection;


public final class SetProgress {

    private String setName;
    private CountByRarity counted;
    private CountByRarity total;
    private PercentageByRarity progress;
    private int overallCount;
    private int overallTotal;

    public static SetProgress generateProgress(String setName, CountByRarity collectedCount, CountByRarity totalCount){
        return new SetProgress(setName,collectedCount,totalCount);
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
