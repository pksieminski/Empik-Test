package com.pksieminski.empiktest.userservice.adapter.data;

import com.pksieminski.empiktest.userservice.adapter.data.model.CalculationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("UnitTest")
@DisplayName("Unit tests for CalculationService")
public class CalculationServiceUnitTest {

    private CalculationService service;

    @BeforeEach
    public void setUp() {
        service = new CalculationService();
    }

    @Test
    @DisplayName("Should return without fraction when dividable")
    public void whenCalledCalculate_givenDividableNumbers_thenReturnWithoutFraction() {
        Long a = 3L;
        Long b = 2L;
        Double expected = 8.0;

        CalculationResult result = service.calculate(a, b);

        assertThat(result.getCalculation(), is(expected));
    }

    @Test
    @DisplayName("Should return with fraction when non dividable")
    public void whenCalledCalculate_givenNotDividableNumbers_thenReturnWithFraction() {
        Long a = 5L;
        Long b = 1L;
        Double expected = 3.5999999999999996;

        CalculationResult result = service.calculate(a, b);

        assertThat(result.getCalculation(), is(expected));
    }

    @Test
    @DisplayName("Should return zero when left is zero")
    public void whenCalledCalculate_givenZeroLeftParam_thenReturnProperResult() {
        Long a = 0L;
        Long b = 9L;
        Double expected = 0.0;

        CalculationResult result = service.calculate(a, b);

        assertThat(result.getCalculation(), is(expected));
    }

    @Test
    @DisplayName("Should return proper result when right is zero")
    public void whenCalledCalculate_givenZeroRightParam_thenReturnProperResult() {
        Long a = 10L;
        Long b = 0L;
        Double expected = 1.2;

        CalculationResult result = service.calculate(a, b);

        assertThat(result.getCalculation(), is(expected));
    }
}
