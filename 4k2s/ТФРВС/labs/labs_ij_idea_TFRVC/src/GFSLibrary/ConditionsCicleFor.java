package GFSLibrary;

public interface ConditionsCicleFor {
    public double getStart();

    public interface GetStartLambda {
        public double getStart();
    }

    public boolean continueCicle(double i);

    public interface ContinueCicleLambda {
        public boolean continueCicle(double i);
    }

    public double next(double i);

    public interface NextIndexLambda {
        public double next(double i);
    }


}
