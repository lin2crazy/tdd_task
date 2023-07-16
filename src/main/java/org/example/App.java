package org.example;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class App {
    private final double a = 1.65;
    private final double EPS = 1e-6;

    public static void main(String[] args) {
        App app = new App();

        double start = 0.7;
        double end = 2.0;
        double step = 0.005;
        double[] x = app.fillX(start, end, step);
        double[] y = app.fillY(x);
        System.out.println(app.getMinAndMaxValue(y));
    }

    public double calculation(double x) {
        if (x + EPS < 1.3) {
            return PI * pow(x, 2) - 7 / pow(x, 2);
        } else if (abs(x - 1.3) < EPS) {
            return a * pow(x, 3) + 7 * sqrt(x);
        } else {
            return log(x + 7 * sqrt(abs(x + a)));
        }
    }

    public int stepCount(double start, double end, double step) {
        return (int) (Math.round((end - start) / step) + 1);
    }

    public double[] fillX(double start, double end, double step) {
        return IntStream
                .range(0, stepCount(start, end, step))
                .mapToDouble(i -> start + i * step)
                .toArray();
    }

    public double[] fillY(double[] x) {
        return DoubleStream.of(x).map(this::calculation).toArray();
    }

    public int getMaxValueIndex(double[] x) {
        if(x.length > 0) {
            double max = Arrays.stream(x).max().getAsDouble();
            for (int i = 0; i < x.length; i++) {
                if(x[i] == max) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getMinValueIndex(double[] x) {
        if(x.length > 0) {
            double min = Arrays.stream(x).min().getAsDouble();
            for (int i = 0; i < x.length; i++) {
                if(x[i] == min) {
                    return i;
                }
            }
        }
        return -1;
    }

    public double getArraySum(double[] x) {
        return Arrays.stream(x).sum();
    }

    public double getArrayAvg(double[] x) {
        return Arrays.stream(x).average().orElse(0);
    }

    public String getMinAndMaxValue(double[] x) {
        if(x.length > 0) {
            double min = Arrays.stream(x).min().getAsDouble();
            double max = Arrays.stream(x).max().getAsDouble();
            return String.format("Min: %f, index: %d\nMax: %f, index: %d", min, getMinValueIndex(x), max, getMaxValueIndex(x));
        }
        return "array is empty";
    }
}
