package br.usp.each.saeg.jaguar.plugin.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class CollectionUtils {

    public static <V> int size(Collection<V> arg) {
        return null == arg ? 0 : arg.size();
    }

    public static <V> List<V> getEvenIndexedValuesFrom(Collection<V> arg) {
        if (null == arg || arg.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return getIndexedValuesFrom(arg, 0);
    }

    public static <V> List<V> getOddIndexedValuesFrom(Collection<V> arg) {
        if (null == arg || arg.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return getIndexedValuesFrom(arg, 1);
    }

    public static <V extends Comparable<V>> V max(List<V> arg) {
        if (arg == null) {
            return null;
        }

        if (arg.size() == 1) {
            return arg.get(0);
        }

        V max = arg.get(0);
        for (V each : arg) {
            if (max.compareTo(each) < 0) {
                max = each;
            }
        }
        return max;
    }

    private static <V> List<V> getIndexedValuesFrom(Collection<V> arg, int initial) {
        if (null == arg || arg.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<V> indexed = new ArrayList<V>();

        for (V each : arg) {
            initial++;
            if (initial % 2 == 0) {
                continue;
            }
            indexed.add(each);
        }
        return indexed;
    }
}
