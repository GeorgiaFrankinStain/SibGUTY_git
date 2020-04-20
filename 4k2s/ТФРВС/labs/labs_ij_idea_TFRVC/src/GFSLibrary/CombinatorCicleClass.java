package GFSLibrary;

import java.util.HashMap;
import java.util.Map;

public class CombinatorCicleClass implements CombinatorCicle {
    private CombinerFunction function;

    public CombinatorCicleClass(CombinerFunction function) {
        this.function = function;
    }

    public interface CombinerFunction {
        public void print(Map<String, Double> staticData);
    }

    @Override
    public void callFunctionAllCombinationCicle(Map<String, Double> staticData, Map<String, ConditionsCicleFor> dinamicData) {

        if (dinamicData.size() == 0) {
            function.print(staticData);
            return;
        }


        Map<String, ConditionsCicleFor> recursionDinamicData = this.clone(dinamicData);
        Map.Entry<String, ConditionsCicleFor> firstIterableItem = recursionDinamicData.entrySet().iterator().next();
        recursionDinamicData.remove(firstIterableItem.getKey());



        Map<String, Double> recursionStaticData = this.clone(staticData);
        ConditionsCicleFor currentIterableVariable = firstIterableItem.getValue();
        for (
                double i = currentIterableVariable.getStart();
                currentIterableVariable.continueCicle(i);
                i = currentIterableVariable.next(i)
        ) {
            recursionStaticData.put(firstIterableItem.getKey(), i);

            this.callFunctionAllCombinationCicle(recursionStaticData, recursionDinamicData);
        }


    }


    // <start> <private_methods>
    private static <K, V> Map<K, V> clone(Map<K, V> original) {
        Map<K, V> copy = new HashMap<>();
        copy.putAll(original);
        return copy;
    }
    // <end> <private_methods>
}
