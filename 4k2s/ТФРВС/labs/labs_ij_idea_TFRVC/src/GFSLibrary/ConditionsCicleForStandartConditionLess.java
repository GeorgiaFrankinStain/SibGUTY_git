package GFSLibrary;

public class ConditionsCicleForStandartConditionLess implements ConditionsCicleFor {
    private double start;
    private double end;
    private NextIndexLambda nextIndexLambda;

    public ConditionsCicleForStandartConditionLess(double start, double end, NextIndexLambda nextIndexLambda) {
        this.start = start;
        this.end = end;
        this.nextIndexLambda = nextIndexLambda;
    }

    @Override
    public double getStart() {
        return start;
    }

    @Override
    public boolean continueCicle(double i) {
        return i < end;
    }

    @Override
    public double next(double i) {
        return nextIndexLambda.next(i);
    }
}
