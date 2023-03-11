import com.innowise.Intervals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

import static com.innowise.Intervals.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntervalIdentificationTest {

    List<String[]> inputArraysList;
    List<String> exprectedReturnsList;
    @BeforeAll
    void initParamsArray() {
        inputArraysList = Arrays.asList(
                new String[]{"C", "D"},
                new String[]{"B", "F#", ORDER_ASC},
                new String[]{"Fb", "Gbb"},

                new String[]{"G", "F#", ORDER_ASC},
                new String[]{"Bb", "A", ORDER_DSC},
                new String[]{"Cb", "Abb", ORDER_DSC},

                new String[]{"G#", "D#", ORDER_DSC},
                new String[]{"E", "B", ORDER_DSC},
                new String[]{"E#", "D#", ORDER_DSC},

                new String[]{"B", "G#", ORDER_DSC},
                new String[]{"A","A",ORDER_DSC}

        );

        exprectedReturnsList = Arrays.asList(
                INTERVAL_MAJ_2,
                INTERVAL_PERF_5,
                INTERVAL_MIN_2,

                INTERVAL_MAJ_7,
                INTERVAL_MIN_2,
                INTERVAL_MAJ_3,

                INTERVAL_PERF_4,
                INTERVAL_PERF_4,
                INTERVAL_MAJ_2,

                INTERVAL_MIN_3,
                INTERVAL_PERF_8
        );
    }

    @Test
    void intervalConstructionReturns() {
        for (int i = 0; i < inputArraysList.size(); i++) {

            String actualOutput = Intervals.intervalIdentification(inputArraysList.get(i));
            String expectedOutput = exprectedReturnsList.get(i);

            Assertions.assertEquals(
                    expectedOutput,
                    actualOutput,
                    "intervalIdentification for " + Arrays.toString(inputArraysList.get(i))
            );
        }
    }

    @Test
    void intervalConstructionIllegalInput() {
        Assertions.assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[1])),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[4])),

                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{"","D"})),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{INTERVAL_PERF_4,"",ORDER_ASC})),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{INTERVAL_MIN_2,"C",""})),

                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{null,null,null})),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{INTERVAL_MAJ_3,null,null})),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{INTERVAL_MIN_6,"A",null})),

                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{"that one","A"})),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{INTERVAL_MAJ_6,"H"})),
                () -> assertThrows(IllegalArgumentException.class, () -> intervalConstruction(new String[]{INTERVAL_PERF_5,"D","order"}))
        );
    }
}
