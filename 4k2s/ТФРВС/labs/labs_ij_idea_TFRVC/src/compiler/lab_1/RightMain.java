package compiler.lab_1;

import myLibrary.ConditionsCicleFor;
import myLibrary.ConditionsCicleForLambda;

public class RightMain {
    public static void main(String[] args) {
        ConditionsCicleFor conditionCicle1 = new ConditionsCicleForLambda(
                () -> 1,
                (i) -> i < 10000,
                (i) -> i * 10
        );
        ConditionsCicleFor conditionCicle2 = new ConditionsCicleForLambda(
                () -> 65527,
                (i) -> i <= 65536,
                (i) -> i++
        );
        ConditionsCicleFor conditionCicle3 = conditionCicle1;


    }
}
