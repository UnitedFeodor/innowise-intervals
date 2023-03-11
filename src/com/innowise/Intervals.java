package com.innowise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Intervals {

    public enum IntervalName {
        m2, M2, m3, M3, P4, P5, m6, M6, m7, M7, P8
    }
    private final static List<String> INTERVAL_NAMES_LIST = Arrays.asList("m2", "M2", "m3","M3","P4","P5","m6", "M6", "m7", "M7", "P8");
    private final static Map<String, Integer[]> INTERVAL_DEGREES_SEMITONES_MAP = Map.ofEntries(
            Map.entry("m2", new Integer[]{2, 1}),
            Map.entry("M2", new Integer[]{2, 2}),
            Map.entry("m3", new Integer[]{3, 3}),
            Map.entry("M3", new Integer[]{3, 4}),
            Map.entry("P4", new Integer[]{4, 5}),
            Map.entry("P5", new Integer[]{5, 7}),
            Map.entry("m6", new Integer[]{6, 8}),
            Map.entry("M6", new Integer[]{6, 9}),
            Map.entry("m7", new Integer[]{7, 10}),
            Map.entry("M7", new Integer[]{7, 11}),
            Map.entry("P8", new Integer[]{8, 12})
            );
    private final static Map<String,Integer> NOTE_SEMITONES_TO_NEXT_MAP = Map.ofEntries(
            Map.entry("C",2),
            Map.entry("D",2),
            Map.entry("E",1),
            Map.entry("F",2),
            Map.entry("G",2),
            Map.entry("A",2),
            Map.entry("B",1)
    );

    private final static Map<String,Integer> ACCIDENTAL_SEMITONES_MAP = Map.ofEntries(
            Map.entry("#",1),
            Map.entry("b",1),
            Map.entry("##",2),
            Map.entry("bb",2)
    );
    private final static String ORDER_ASC = "asc";
    private final static String ORDER_DESC = "dsc";



    public enum NoteName {
        C, D, E, F, G, A, B
    }
    // 1 to 7              C-G desc = P4      1-5      1+4 = 5  7-2 = 5

    public enum Accidental {
        SHARP,
        FLAT,
        DOUBLE_SHARP,
        DOUBLE_FLAT
    }
    private final static String INVALID_PARAM_COUNT_EXCEPTION = "Illegal number of elements in input array";
    private final static String INVALID_INTERVAL_PARAM_EXCEPTION = "Illegal interval param in input array";
    private final static String INVALID_NOTE_PARAM_EXCEPTION = "Illegal note param in input array";
    private final static String INVALID_ORDER_PARAM_EXCEPTION = "Illegal order param in input array";

    /**
     * - The function 'intervalConstruction' must take an array of strings as input and return a string.
     * - An array contains three or two elements.
     * - The first element in an array is an interval name, the second is a starting note,
     * and the third indicates whether an interval is ascending or descending.
     * - If there is no third element in an array, the building order is ascending by default.
     * - The function should return a string containing a note name.
     * - Only the following note names are allowed in a return string:
     *     Cbb Cb C C# C## Dbb Db D D# D## Ebb Eb E E# E## Fbb Fb F F# F##
     *     Gbb Gb G G# G## Abb Ab A A# A## Bbb Bb B B# B##
     * - If there are more or fewer elements in the input array, an exception should be thrown: "Illegal number of elements in input array"
     * - Example: |['M2', 'C', 'asc']| -> |D|
     * @param args - [$intervalNameStr, $firstNoteNameStr, ($ascOrDescStr)]
     * @return $secondNoteNameStr
     */
    public static String intervalConstruction(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException(INVALID_PARAM_COUNT_EXCEPTION);
        }
        String intervalNameStr = args[0];
        String firstNoteNameStr = args[1];
        String orderStr = args.length == 3 ? args[2] : ORDER_ASC;
        if (!INTERVAL_DEGREES_SEMITONES_MAP.containsKey(intervalNameStr)) {
            throw new IllegalArgumentException(INVALID_INTERVAL_PARAM_EXCEPTION);
        }
        if (!NOTE_SEMITONES_TO_NEXT_MAP.containsKey(firstNoteNameStr)) {
            throw new IllegalArgumentException(INVALID_NOTE_PARAM_EXCEPTION)
        }
        if (!orderStr.equals(ORDER_ASC) && !orderStr.equals(ORDER_DESC)) {
            throw new IllegalArgumentException(INVALID_ORDER_PARAM_EXCEPTION);
        }


        // $intervalNameStr turns into number of semitones and degrees
        // first find the amount of degrees = the second note needed = $secondNoteNameStr
        // account for $ascOrDescStr by looping or something
        // then add sharps and flats to turn it into the proper one (while also accounting for the accidentals of $firstNoteNameStr)
    }

    private static int getDegreeCountFromInterval(String intervalName) {

    }
    private static int getSemitoneCountFromInterval(String intervalName) {

    }

    /**
     * - The function 'intervalIdentification' must take an array of strings as input and return a string.
     * - An array contains three or two elements.
     * - The first element is the first note in the interval, the second element is the last note in the interval,
     * the third indicates whether an interval is ascending or descending.
     * - If there is no third element in an array, the interval is considered ascending by default.
     * - The function should return a string - name of the interval.
     * - Only the following intervals are allowed in a return string:
     *      m2 M2 m3 M3 P4 P5 m6 M6 m7 M7 P8
     * - If the interval does not fit a description, an exception should be thrown: "Cannot identify the interval".
     * - Example: |['C', 'D']| -> |M2|
     * @param args - [$firstNoteNameStr, $secondNoteNameStr, ($ascOrDescStr)]
     * @return
     */
    public static String intervalIdentification(String[] args) {
    }
}
