package org.example;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


public class AppTest {
    public final static double EPS = 1e-6;
    App app;

    @BeforeEach
    public void setUp() {
        app = new App();
    }

    @ParameterizedTest
    @MethodSource("provideDataForTestCalculation")
    public void testCalculation(double x, double expected) {
        double actual = app.calculation(x);
        assertThat(actual).isCloseTo(expected, Offset.offset(EPS));
    }

    private static Stream<Arguments> provideDataForTestCalculation() {
        return Stream.of(
                Arguments.of(1, -3.85840734),
                Arguments.of(1.3, 11.60627797),
                Arguments.of(1.5, 2.633597275)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTestStepCount")
    public void testStepCount(double start, double end, double step, int expected) {
        int actual = app.stepCount(start, end, step);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideDataForTestStepCount() {
        return Stream.of(
                Arguments.of(0.7, 2.0, 0.005, 261),
                Arguments.of(1.0, 1.5, 0.005, 101),
                Arguments.of(0.7, 1.0, 0.005, 61)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTestFillX")
    public void testFillX(int index, double expected) {
        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double actual = app.fillX(start, end, step)[index];
        assertThat(actual).isCloseTo(expected, Offset.offset(EPS));
    }

    public static Stream<Arguments> provideDataForTestFillX() {
        return Stream.of(
                Arguments.of(0, 0.7),
                Arguments.of(120, 1.3),
                Arguments.of(260, 2.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTestFillY")
    public void testFillY(int index, double expected) {
        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double[] x = app.fillX(start, end, step);
        double actual = app.fillY(x)[index];
        assertThat(actual).isCloseTo(expected, Offset.offset(EPS));
    }

    public static Stream<Arguments> provideDataForTestFillY() {
        return Stream.of(
                Arguments.of(0, -12.746333885),
                Arguments.of(120, 11.606277975),
                Arguments.of(260, 2.73264402)
        );
    }

    @Test
    public void testGetMaxValueIndex() {
        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double[] x = app.fillX(start, end, step);
        int actual = app.getMaxValueIndex(app.fillY(x));
        assertThat(actual).isEqualTo(120);
    }

    @Test
    public void testGetMinValueIndex() {
        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double[] x = app.fillX(start, end, step);
        int actual = app.getMinValueIndex(app.fillY(x));
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void testGetArraySum() {
        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double[] x = app.fillX(start, end, step);
        double actual = app.getArraySum(app.fillY(x));
        assertThat(actual).isCloseTo(-157.16288208, Offset.offset(EPS));
    }

    @Test
    public void testGetArrayAvg() {
        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double[] x = app.fillX(start, end, step);
        double actual = app.getArrayAvg(app.fillY(x));
        assertThat(actual).isCloseTo(-0.60215663, Offset.offset(EPS));
    }

    @Test
    public void testGetMinAndMaxValue() {
        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double[] x = app.fillX(start, end, step);
        double[] y = app.fillY(x);
        String actual = app.getMinAndMaxValue(y);
        System.out.println(y[0]);
        System.out.println(y[120]);

        assertThat(actual).isEqualTo("Min: -12.746334, index: 0. Max: 11.606278, index: 120");
    }
}
























