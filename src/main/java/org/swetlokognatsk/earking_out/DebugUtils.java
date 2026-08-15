package org.swetlokognatsk.earking_out;

import java.util.LinkedList;
import java.util.List;

public final class DebugUtils {
    
    public static long start;

    public static List<Double> diffs = new LinkedList<>();
    
    public static void startStopwatch() {
        start = System.nanoTime();
        System.out.println("start: " + start);
    }

    public static void stopStopwatch() {
        var end = System.nanoTime();
        System.out.println("end: " + end);
        var diff = (end - start);
        System.out.println("diff (ns): " + diff);
        var diffMillis = diff / 1_000_000_000D;
        System.out.println("diff (s): " + diffMillis);
        
        diffs.add(diffMillis);
        var avgDiff = diffs.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        System.out.println("avg diff (s): " + avgDiff);
    }
}
