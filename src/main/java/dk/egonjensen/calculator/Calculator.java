package dk.egonjensen.calculator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Calculator
 */
@Service
public class Calculator {

    @Cacheable("sum")
    int sum(int a, int b) {
        return a + b;
    }

}
