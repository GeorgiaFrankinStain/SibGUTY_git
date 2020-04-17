package myLibrary;

public class ConditionsCicleForLambda implements ConditionsCicleFor {
    private GetStart getStartLambda;
    private ContinueCicle continueCicleLambda;
    private Next nextLambda;

    public ConditionsCicleForLambda(GetStart getStartLambda, ContinueCicle continueCicleLambda, Next nextLambda) {
        this.getStartLambda = getStartLambda;
        this.continueCicleLambda = continueCicleLambda;
        this.nextLambda = nextLambda;
    }

    @Override
    public int getStart() {
        return getStartLambda.getStart();
    }

    @Override
    public boolean continueCicle(int i) {
        return continueCicleLambda.continueCicle(i);
    }

    @Override
    public int next(int i) {
        return nextLambda.next(i);
    }
}
