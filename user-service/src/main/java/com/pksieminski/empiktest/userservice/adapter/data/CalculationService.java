package com.pksieminski.empiktest.userservice.adapter.data;

import com.pksieminski.empiktest.userservice.adapter.data.model.CalculationResult;
import org.springframework.stereotype.Component;

@Component
public class CalculationService {

    private final Double SLOPE = 6.0;
    private final Double CONSTANT_TERM = 2.0;

    public CalculationResult calculate(Long a, Long b) {
        if (a == 0L) {
            // I made assumption there - that for 0 we can return 0 isntead of throwing exception
            return new CalculationResult(0.0);
        }

        return new CalculationResult(SLOPE / a.doubleValue() * (CONSTANT_TERM + b.doubleValue()));
    }
}
