import com.innowise.Intervals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.List;

import static com.innowise.Intervals.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(Lifecycle.PER_CLASS)
public class IntervalConstructionTest {

    List<String[]> inputArraysList;
    List<String> exprectedReturnsList;
    @BeforeAll
    void initParamsArray() {

        inputArraysList = Arrays.asList(
                new String[]{INTERVAL_MAJ_2, "C", ORDER_ASC},
                new String[]{INTERVAL_PERF_5, "B", ORDER_ASC},
                new String[]{INTERVAL_MIN_2, "Bb", ORDER_DSC},
                new String[]{INTERVAL_MAJ_3, "Cb", ORDER_DSC},
                new String[]{INTERVAL_PERF_4, "G#", ORDER_DSC},
                new String[]{INTERVAL_MIN_3, "B", ORDER_DSC},
                new String[]{INTERVAL_MIN_2, "Fb", ORDER_ASC},
                new String[]{INTERVAL_MAJ_2, "E#", ORDER_DSC},
                new String[]{INTERVAL_PERF_4, "E", ORDER_DSC},
                new String[]{INTERVAL_MIN_2, "D#", ORDER_ASC},
                new String[]{INTERVAL_MAJ_7, "G", ORDER_ASC},
                new String[]{INTERVAL_PERF_8,"A",ORDER_DSC}

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
                "F#",
                "A"
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
        Assertions.assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[1])),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[4])),

                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"","D"})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{INTERVAL_PERF_4,"",ORDER_ASC})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{INTERVAL_MIN_2,"C",""})),

                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{null,null,null})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{INTERVAL_MAJ_3,null,null})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{INTERVAL_MIN_6,"A",null})),

                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{"that one","A"})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{INTERVAL_MAJ_6,"H"})),
                () -> assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(new String[]{INTERVAL_PERF_5,"D","order"}))
        );
    }
}
