package com.innowise;

public class Intervals {

    public enum IntervalName {
        m2, M2, m3, M3, P4, P5, m6, M6, m7, M7, P8
    }

    public enum NoteName {
        C, D, E, F, G, A, B
    }

    public enum Accidental {
        SHARP,
        FLAT,
        DOUBLE_SHARP,
        DOUBLE_FLAT
    }

    /**
     * - The function 'intervalConstruction' must take an array of strings as input and return a string.
     * - An array contains three or two elements.
     * - The first element in an array is an interval name, the second is a starting note, and the third indicates whether an interval is ascending or descending.
     * - If there is no third element in an array, the building order is ascending by default.
     * - The function should return a string containing a note name.
     * - Only the following note names are allowed in a return string: <br>
     *     Cbb Cb C C# C## Dbb Db D D# D## Ebb Eb E E# E## Fbb Fb F F# F## Gbb Gb G G# G## Abb Ab A A# A## Bbb Bb B B# B## <br>
     * - If there are more or fewer elements in the input array, an exception should be thrown: "Illegal number of elements in input array"
     * @param args - [$intervalNameStr, $firstNoteNameStr, ($ascOrDescStr)]
     * @return $secondNoteNameStr
     */
    public static String intervalConstruction(String[] args) {

    }

    /**
     * - The function 'intervalIdentification' must take an array of strings as input and return a string.
     * - An array contains three or two elements.
     * - The first element is the first note in the interval, the second element is the last note in the interval, the third indicates whether an interval is ascending or descending.
     * - If there is no third element in an array, the interval is considered ascending by default.
     * - The function should return a string - name of the interval.
     * - Only the following intervals are allowed in a return string: <br>
     *      m2 M2 m3 M3 P4 P5 m6 M6 m7 M7 P8
     * - If the interval does not fit a description, an exception should be thrown: "Cannot identify the interval".
     * @param args - [$firstNoteNameStr, $secondNoteNameStr, ($ascOrDescStr)]
     * @return
     */
    public static String intervalIdentification(String[] args) {
    }
}
