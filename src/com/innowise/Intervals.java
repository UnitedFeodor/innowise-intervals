package com.innowise;

import java.util.*;

public class Intervals {

    public final static String INTERVAL_MIN_2 = "m2";
    public final static String INTERVAL_MAJ_2 = "M2";
    public final static String INTERVAL_MIN_3 = "m3";
    public final static String INTERVAL_MAJ_3 = "M3";
    public final static String INTERVAL_PERF_4 = "P4";
    public final static String INTERVAL_PERF_5 = "P5";
    public final static String INTERVAL_MIN_6 = "m6";
    public final static String INTERVAL_MAJ_6 = "M6";
    public final static String INTERVAL_MIN_7 = "m7";
    public final static String INTERVAL_MAJ_7 = "M7";
    public final static String INTERVAL_PERF_8 = "P8";
    private final static Map<String, Integer[]> INTERVAL_DEGREES_SEMITONES_MAP = Map.ofEntries(
            Map.entry(INTERVAL_MIN_2, new Integer[]{2, 1}),
            Map.entry(INTERVAL_MAJ_2, new Integer[]{2, 2}),
            Map.entry(INTERVAL_MIN_3, new Integer[]{3, 3}),
            Map.entry(INTERVAL_MAJ_3, new Integer[]{3, 4}),
            Map.entry(INTERVAL_PERF_4, new Integer[]{4, 5}),
            Map.entry(INTERVAL_PERF_5, new Integer[]{5, 7}),
            Map.entry(INTERVAL_MIN_6, new Integer[]{6, 8}),
            Map.entry(INTERVAL_MAJ_6, new Integer[]{6, 9}),
            Map.entry(INTERVAL_MIN_7, new Integer[]{7, 10}),
            Map.entry(INTERVAL_MAJ_7, new Integer[]{7, 11}),
            Map.entry(INTERVAL_PERF_8, new Integer[]{8, 12})
    );


    public final static String NOTE_C = "C";
    public final static String NOTE_D = "D";
    public final static String NOTE_E = "E";
    public final static String NOTE_F = "F";
    public final static String NOTE_G = "G";
    public final static String NOTE_A = "A";
    public final static String NOTE_B = "B";

    private final static List<String> NOTE_ORDER_LIST =
            Arrays.asList(NOTE_C,NOTE_D,NOTE_E,NOTE_F,NOTE_G,NOTE_A,NOTE_B);
    private final static Map<String,Integer> NOTE_SEMITONES_TO_NEXT_MAP = Map.ofEntries(
            Map.entry(NOTE_C,2),
            Map.entry(NOTE_D,2),
            Map.entry(NOTE_E,1),
            Map.entry(NOTE_F,2),
            Map.entry(NOTE_G,2),
            Map.entry(NOTE_A,2),
            Map.entry(NOTE_B,1)
    );

    public final static String ACCIDENTAL_NONE = "";
    public final static String ACCIDENTAL_SHARP = "#";
    public final static String ACCIDENTAL_DOUBLE_SHARP = "##";
    public final static String ACCIDENTAL_FLAT = "b";
    public final static String ACCIDENTAL_DOUBLE_FLAT = "bb";
    private final static Map<String,Integer> ACCIDENTAL_SEMITONES_MAP = Map.ofEntries(
            Map.entry(ACCIDENTAL_NONE,0),
            Map.entry(ACCIDENTAL_SHARP,1),
            Map.entry(ACCIDENTAL_FLAT,-1),
            Map.entry(ACCIDENTAL_DOUBLE_SHARP,2),
            Map.entry(ACCIDENTAL_DOUBLE_FLAT,-2)
    );
    public final static String ORDER_ASC = "asc";
    public final static String ORDER_DSC = "dsc";

    public final static int TOTAL_SEMITONES_IN_AN_OCTAVE = 12;
    public final static int TOTAL_DEGREES_IN_AN_OCTAVE = 7;

    private final static String NULL_PARAM_EXCEPTION = "Input param is null";
    private final static String INVALID_PARAM_COUNT_EXCEPTION = "Illegal number of elements in input array";
    private final static String INVALID_INTERVAL_PARAM_EXCEPTION = "Illegal interval param in input array";
    private final static String INVALID_NOTE_PARAM_EXCEPTION = "Illegal note param in input array";
    private final static String INVALID_ORDER_PARAM_EXCEPTION = "Illegal order param in input array";
    private final static String INVALID_INTERVAL_DESCRIPTION_EXCEPTION = "Cannot identify the interval";

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
     * @param args - [intervalNameStr, firstNoteNameStr, (ascOrDescStr)]
     * @return secondNoteNameStr
     */
    public static String intervalConstruction(String[] args) {

        validateArrLengthAndNullArgs(args);
        String intervalName = validateIntervalName(args[0]);

        String firstNoteName = parseNaturalNote(args[1]);
        String firstNoteAccidental = parseAccidental(args[1]);

        String intervalNoteOrder = args.length == 3 ? validateNoteOrder(args[2])  : ORDER_ASC;

        if (intervalName.equals(INTERVAL_PERF_8)) {
            return firstNoteName;
        }

        // -1 because an interval is inclusive of all its degrees
        int degreeCountToNextNote = getDegreeDistanceForInterval(intervalName) - 1;
        String secondNoteName = getNextNoteByDegreesFromFirst(firstNoteName,degreeCountToNextNote,intervalNoteOrder);
        
        int intervalSemitoneDistance = getSemitoneDistanceForInterval(intervalName);
        int naturalNotesSemitoneDistance = getSemitoneDistanceBetweenNaturalNotes(firstNoteName,secondNoteName,intervalNoteOrder);
        int firstNoteAccidentalSemitones = accidentalToSemitones(firstNoteAccidental);

        if (intervalNoteOrder.equals(ORDER_DSC)) {
            intervalSemitoneDistance = -intervalSemitoneDistance;
            naturalNotesSemitoneDistance = -naturalNotesSemitoneDistance;
        }

        int semitoneDifference = intervalSemitoneDistance - naturalNotesSemitoneDistance + firstNoteAccidentalSemitones;
        String accidentalNeeded = semitonesToAccidental(semitoneDifference);

        String secondNoteWithAccidental = secondNoteName + accidentalNeeded;
        return secondNoteWithAccidental;
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
     * @param args - [firstNoteNameStr, secondNoteNameStr, (ascOrDescStr)]
     * @return intervalNameStr
     */
    public static String intervalIdentification(String[] args) {
        validateArrLengthAndNullArgs(args);

        String firstNoteName = parseNaturalNote(args[0]);
        String firstNoteAccidental = parseAccidental(args[0]);

        String secondNoteName = parseNaturalNote(args[1]);
        String secondNoteAccidental = parseAccidental(args[1]);

        String intervalNoteOrder = args.length == 3 ? validateNoteOrder(args[2]) : ORDER_ASC;

        if (secondNoteName.equals(firstNoteName)) {
            return INTERVAL_PERF_8;
        }

        // +1 because an interval is inclusive of all its degrees
        int degreesBetweenNotes = getDegreeDistanceBetweenNaturalNotes(firstNoteName,secondNoteName,intervalNoteOrder) + 1;
        int semitonesBetweenNotes = getSemitoneDistanceBetweenNaturalNotes(firstNoteName,secondNoteName,intervalNoteOrder);

        int firstNoteAccidentalSemitones = accidentalToSemitones(firstNoteAccidental);
        int secondNoteAccidentalSemitones = accidentalToSemitones(secondNoteAccidental);

        if (intervalNoteOrder.equals(ORDER_DSC)) {
            semitonesBetweenNotes = semitonesBetweenNotes + firstNoteAccidentalSemitones - secondNoteAccidentalSemitones;
        } else {
            semitonesBetweenNotes = semitonesBetweenNotes - firstNoteAccidentalSemitones + secondNoteAccidentalSemitones;
        }

        Integer[] intervalMapEntry = new Integer[]{degreesBetweenNotes,Math.abs(semitonesBetweenNotes)};

        String intervalName = getKeyByValue(INTERVAL_DEGREES_SEMITONES_MAP,intervalMapEntry);
        if (intervalName == null) {
            throw new IllegalArgumentException(INVALID_INTERVAL_DESCRIPTION_EXCEPTION);
        }

        return intervalName;
    }


    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value instanceof Integer[] && Arrays.equals((Integer[]) value, (Integer[]) entry.getValue())) {
                return entry.getKey();
            } else if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    private static String semitonesToAccidental(int semitoneDifference) {
        return getKeyByValue(ACCIDENTAL_SEMITONES_MAP,semitoneDifference);
    }

    private static int accidentalToSemitones(String accidental) {
        return ACCIDENTAL_SEMITONES_MAP.get(accidental);
    }

    private static String parseAccidental(String noteNameWithAccidentals) {
        String accidentalStr = noteNameWithAccidentals.substring(1);
        String result = ACCIDENTAL_SEMITONES_MAP.keySet().stream()
                .filter(e -> !e.equals(ACCIDENTAL_NONE))
                .filter(accidentalStr::equals)
                .findAny()
                .orElse(ACCIDENTAL_NONE);
            return result;
    }


    private static String parseNaturalNote(String noteNameWithAccidentals) {
        String noteName = noteNameWithAccidentals;
        if (noteNameWithAccidentals.length() > 1) {
            noteName = noteNameWithAccidentals.substring(0,1);
        }
        String result = NOTE_ORDER_LIST.stream()
                .filter(noteName::equals)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_NOTE_PARAM_EXCEPTION));

        return result;
    }

    private static void validateArrLengthAndNullArgs(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException(NULL_PARAM_EXCEPTION);
        }
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException(INVALID_PARAM_COUNT_EXCEPTION);
        }

        if (args[0] == null || args[1] == null || (args.length == 3 && args[2] == null)) {
            throw new IllegalArgumentException(NULL_PARAM_EXCEPTION);
        }

    }

    private static String validateNoteOrder(String noteOrder) {
        if (!noteOrder.equals(ORDER_ASC) && !noteOrder.equals(ORDER_DSC)) {
            throw new IllegalArgumentException(INVALID_ORDER_PARAM_EXCEPTION);
        }
        return noteOrder;
    }

    private static String validateIntervalName(String intervalName) {
        if (!INTERVAL_DEGREES_SEMITONES_MAP.containsKey(intervalName)) {
            throw new IllegalArgumentException(INVALID_INTERVAL_PARAM_EXCEPTION);
        }
        return intervalName;
    }


    private static int getSemitoneDistanceBetweenNaturalNotes(String firstNoteName,
                                                              String secondNoteName,
                                                              String noteOrder) {

        int firstNoteInd = NOTE_ORDER_LIST.indexOf(firstNoteName);
        int secondNoteInd = NOTE_ORDER_LIST.indexOf(secondNoteName);

        int maxNoteInd = Math.max(firstNoteInd,secondNoteInd);
        int minNoteInd = Math.min(firstNoteInd,secondNoteInd);
        List<String> noteOrderSublist = NOTE_ORDER_LIST.subList(minNoteInd,maxNoteInd);

        int semitonesBetween = 0;
        for (String noteName : noteOrderSublist) {
            semitonesBetween += NOTE_SEMITONES_TO_NEXT_MAP.get(noteName);
        }

        if (secondNoteInd < firstNoteInd) {
            noteOrder = getInvertedOrder(noteOrder);
        }

        if (noteOrder.equals(ORDER_DSC)) {
            semitonesBetween =
                    (TOTAL_SEMITONES_IN_AN_OCTAVE - semitonesBetween % TOTAL_SEMITONES_IN_AN_OCTAVE);
        }
        return semitonesBetween;
    }

    private static int getDegreeDistanceBetweenNaturalNotes(String firstNoteName,
                                                              String secondNoteName,
                                                              String noteOrder) {

        int firstNoteInd = NOTE_ORDER_LIST.indexOf(firstNoteName);
        int secondNoteInd = NOTE_ORDER_LIST.indexOf(secondNoteName);

        int maxNoteInd = Math.max(firstNoteInd,secondNoteInd);
        int minNoteInd = Math.min(firstNoteInd,secondNoteInd);
        List<String> noteOrderSublist = NOTE_ORDER_LIST.subList(minNoteInd,maxNoteInd);

        int degreesBetween = noteOrderSublist.size();

        if (secondNoteInd < firstNoteInd) {
            noteOrder = getInvertedOrder(noteOrder);
        }

        if (noteOrder.equals(ORDER_DSC)) {
            degreesBetween =
                    (TOTAL_DEGREES_IN_AN_OCTAVE - degreesBetween % TOTAL_DEGREES_IN_AN_OCTAVE);
        }
        return degreesBetween;
    }

    private static final String getInvertedOrder(String orderStr) {
        if (orderStr.equals(ORDER_DSC)) {
            return ORDER_ASC;
        } else if (orderStr.equals(ORDER_ASC)) {
            return ORDER_DSC;
        } else {
            throw new IllegalArgumentException(INVALID_ORDER_PARAM_EXCEPTION);
        }

    }

    private static String getNextNoteByDegreesFromFirst(String firstNoteName,
                                                        int degreeCountToNextNote,
                                                        String intervalNoteOrder) {

        if (intervalNoteOrder.equals(ORDER_DSC)) {
            degreeCountToNextNote = -degreeCountToNextNote;
        }

        int secondNoteIndex =
                Math.abs(NOTE_ORDER_LIST.size() + NOTE_ORDER_LIST.indexOf(firstNoteName) + degreeCountToNextNote)
                % NOTE_ORDER_LIST.size();
        return NOTE_ORDER_LIST.get(secondNoteIndex);
    }


    private static int getDegreeDistanceForInterval(String intervalName) {
        return INTERVAL_DEGREES_SEMITONES_MAP.get(intervalName)[0];
    }
    private static int getSemitoneDistanceForInterval(String intervalName) {
        return INTERVAL_DEGREES_SEMITONES_MAP.get(intervalName)[1];

    }

}
