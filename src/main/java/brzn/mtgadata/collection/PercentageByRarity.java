package brzn.mtgadata.collection;

//todo do testowania
final class PercentageByRarity {

    private double commons;
    private double uncommons;
    private double rares;
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
        double result = (double) a / (double) b;
        return Math.round(result * 100) / 100;
    }
}
