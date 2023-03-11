import com.innowise.Intervals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.List;

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
                new String[]{"P5", "B", "asc"}
        );
        exprectedReturnsList = Arrays.asList(
                "D",
                "F#"
        );



    }

    @Test
    void intervalConstructionReturns() {
        for (int i = 0; i < inputArraysList.size(); i++) {
            String actualOutput = Intervals.intervalConstruction(inputArraysList.get(i));
            String expectedOutput = exprectedReturnsList.get(i);
            Assertions.assertEquals(
                    expectedOutput,
                    actualOutput,
                    "intervalConstruction for " + Arrays.toString(inputArraysList.get(i))
            );
        }

    }

    @Test
    void intervalConstructionIllegalInput() {

    }
}
