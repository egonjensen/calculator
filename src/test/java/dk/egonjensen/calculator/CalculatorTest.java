package dk.egonjensen.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquala;

public class CalculatorTest {

    private Calculator calculator = new Calculator();

    @Test
    public void testSum() {
        assertEquals(5, calculator.sum(2, 3));
    }

}
