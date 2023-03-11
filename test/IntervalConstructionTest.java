import com.innowise.Intervals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(Lifecycle.PER_CLASS)
public class IntervalConstructionTest {

    List<String[]> inputArraysList;
    List<String> exprectedReturnsList;
    @BeforeAll
    void initParamsArray() {
        /*
        |['M2', 'C', 'asc']|D|
        |['P5', 'B', 'asc']|F#|
        |['m2', 'Bb', 'dsc']|A|
        |['M3', 'Cb', 'dsc']|Abb|
        |['P4', 'G#', 'dsc']|D#|
        |['m3', 'B', 'dsc']|G#|
        |['m2', 'Fb', 'asc']|Gbb|
        |['M2', 'E#', 'dsc']|D#|
        |['P4', 'E', 'dsc']|B|
        |['m2', 'D#', 'asc']|E|
        |['M7', 'G', 'asc']|F#|
         */
        inputArraysList = Arrays.asList(
                new String[]{"M2", "C", "asc"},
                new String[]{"P5", "B", "asc"},
                new String[]{"m2", "Bb", "dsc"},
                new String[]{"M3", "Cb", "dsc"},
                new String[]{"P4", "G#", "dsc"},
                new String[]{"m3", "B", "dsc"},
                new String[]{"m2", "Fb", "asc"},
                new String[]{"M2", "E#", "dsc"},
                new String[]{"P4", "E", "dsc"},
                new String[]{"m2", "D#", "asc"},
                new String[]{"M7", "G", "asc"}

        );
        exprectedReturnsList = Arrays.asList(
                "D",
                "F#",
                "A",
                "Abb",
                "D#",
                "G#",
                "Gbb",
                "D#",
                "B",
                "E",
                "F#"
        );
    }

    @Test
    void intervalConstructionReturns() {
        for (int i = 0; i < inputArraysList.size(); i++) {
            String actualOutput = Intervals.intervalConstruction(inputArraysList.get(i));
            String expectedOutput = exprectedReturnsList.get(i);
            //System.out.println(Arrays.toString(inputArraysList.get(i)) +" actual: "+ actualOutput + " expected: " + expectedOutput);

            Assertions.assertEquals(
                    expectedOutput,
                    actualOutput,
                    "intervalConstruction for " + Arrays.toString(inputArraysList.get(i))
            );


        }

    }

    @Test
    void intervalConstructionIllegalInput() {
        Assertions.assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[1])),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[4])),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"","D"})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"P4","","asc"})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"m2","C",""})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{null,null,null})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"M3",null,null})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"m6","A",null})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"that one","A"})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"M6","H"})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"P5","D","i don't care"}))
        );
    }
}
