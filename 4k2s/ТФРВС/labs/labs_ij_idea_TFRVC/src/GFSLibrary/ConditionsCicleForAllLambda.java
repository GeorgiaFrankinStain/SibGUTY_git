package GFSLibrary;

public class ConditionsCicleForAllLambda implements ConditionsCicleFor {
    private GetStartLambda getStartLambda;
    private ContinueCicleLambda continueCicleLambda;
    private NextIndexLambda nextIndexLambda;

    public ConditionsCicleForAllLambda(GetStartLambda getStartLambda, ContinueCicleLambda continueCicleLambda, NextIndexLambda nextIndexLambda) {
        this.getStartLambda = getStartLambda;
        this.continueCicleLambda = continueCicleLambda;
        this.nextIndexLambda = nextIndexLambda;
    }

    @Override
    public double getStart() {
        return getStartLambda.getStart();
    }

    @Override
    public boolean continueCicle(double i) {
        return continueCicleLambda.continueCicle(i);
    }

    @Override
    public double next(double i) {
        return nextIndexLambda.next(i);
    }
}
