package brzn.mtgadata.collection;

import lombok.Getter;

final class PercentageByRarity {

    @Getter
    private double commons;

    @Getter
    private double uncommons;

    @Getter
    private double rares;

    @Getter
    private double mythics;

    static PercentageByRarity countPercentage(CountByRarity collected, CountByRarity total) {
        return new PercentageByRarity(
                getPercentsFromInt(collected.getCommons(), total.getCommons()),
                getPercentsFromInt(collected.getUncommons(), total.getUncommons()),
                getPercentsFromInt(collected.getRares(), total.getRares()),
                getPercentsFromInt(collected.getMythics(), total.getMythics())
        );

    }

    private PercentageByRarity(double commons, double uncommons, double rares, double mythics) {
        this.commons = commons;
        this.uncommons = uncommons;
        this.rares = rares;
        this.mythics = mythics;
    }

    private static double getPercentsFromInt(int a, int b) {
        return(double) a / (double) b * 100;
    }
}
