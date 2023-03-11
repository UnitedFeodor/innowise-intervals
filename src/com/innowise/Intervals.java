package com.innowise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Intervals {

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

    // 1 to 7              C-G desc = P4      1-5      1+4 = 5  7-2 = 5
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
            Map.entry("",0),
            Map.entry("#",1),
            Map.entry("b",-1),
            Map.entry("##",2),
            Map.entry("bb",-2)
    );
    private final static String ORDER_ASC = "asc";
    private final static String ORDER_DESC = "dsc";


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

        String intervalName = args[0];
        String firstNoteName = parseNaturalNote(args[1]);
        String firstNoteAccidental = parseAccidental(args[1]);
        String intervalNoteOrder = args.length == 3 ? args[2] : ORDER_ASC;
        
        if (!INTERVAL_DEGREES_SEMITONES_MAP.containsKey(intervalName)) {
            throw new IllegalArgumentException(INVALID_INTERVAL_PARAM_EXCEPTION);
        }
        if (!NOTE_SEMITONES_TO_NEXT_MAP.containsKey(firstNoteName) || !ACCIDENTAL_SEMITONES_MAP.containsKey(firstNoteAccidental)) {
            throw new IllegalArgumentException(INVALID_NOTE_PARAM_EXCEPTION);
        }
        if (!intervalNoteOrder.equals(ORDER_ASC) && !intervalNoteOrder.equals(ORDER_DESC)) {
            throw new IllegalArgumentException(INVALID_ORDER_PARAM_EXCEPTION);
        }
        // TODO if p8 then return res without calculations
        int degreeCountToNextNote = getDegreeCountFromInterval(intervalName);
        String secondNoteName = getNoteByDegreesToIt(firstNoteName,degreeCountToNextNote,intervalNoteOrder);
        
        int neededSemitoneCountToNextIntervalNote = getSemitoneCountFromInterval(intervalName);
        int semitonesBetweenNaturalNotes = getSemitonesBetweenNotes(firstNoteName,secondNoteName,intervalNoteOrder);

        int firstNoteAccidentalSemitones = accidentalToSemitones(firstNoteAccidental);
        // TODO maybe abs in first difference
        int semitoneDifference = (semitonesBetweenNaturalNotes - firstNoteAccidentalSemitones) - neededSemitoneCountToNextIntervalNote;
        /*
        |['P5', 'B', 'asc']|F#|
            B -> F
            5 degrees but add only 5-1=4
            7 semitones but actually count 6 -> add 1 so #

        |['P4', 'G#', 'dsc']|D#|
            G -> D
            4 degrees but add only 4-1=3
            5 semitones but actually count 5-1(from initial) -> add 1 so #

         |['m2', 'Fb', 'asc']|Gbb|
            F -> G
            2 degrees but only add 2-1=1
            1 semitone but actually count 2+1(from initial) -> remove 2 so bb

            needed = (naturalCount - (initialAccidental)) - secondAccidental
            secondAccidental = (naturalCount - (initialAccidental)) - needed
         */
        String accidentalNeeded = semitonesToAccidental(semitoneDifference);

        String secondNoteWithAccidental = secondNoteName + accidentalNeeded;
        // $intervalNameStr turns into number of semitones and degrees
        // first find the amount of degrees = the second note needed = $secondNoteNameStr
        // account for $ascOrDescStr by looping or something
        // then add sharps and flats to turn it into the proper one (while also accounting for the accidentals of $firstNoteNameStr)

        return secondNoteWithAccidental; // TODO implement
    }

    private static String semitonesToAccidental(int semitoneDifference) {
        return new String();  // TODO implement
    }

    private static int accidentalToSemitones(String accidental) {
        return 0;  // TODO implement
    }

    private static String parseAccidental(String noteNameWithAccidentals) {
        return new String(); // TODO implement
    }

    private static String parseNaturalNote(String noteNameWithAccidentals) {
        return new String(); // TODO implement
    }

    private static int getSemitonesBetweenNotes(String firstNoteName, String secondNoteName, String intervalNoteOrder) {
        return 0; // TODO implement
    }

    private static String getNoteByDegreesToIt(String firstNoteName, int degreeCountToNextNote, String intervalNoteOrder) {
        return new String(); // TODO implement
    }


    private static int getDegreeCountFromInterval(String intervalName) {
        return 0; // TODO implement
    }
    private static int getSemitoneCountFromInterval(String intervalName) {
        return 0; // TODO implement

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
        return new String(); // TODO implement
    }
}
