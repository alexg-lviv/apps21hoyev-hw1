package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int actualSize;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[]{};
        this.actualSize = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (this.check_for_lower_bound(temperatureSeries)){
            throw new InputMismatchException();
        }
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        this.actualSize = temperatureSeries.length;
    }

    public double average() {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double res = 0;
        for (double elem:
             this.temperatureSeries) {
            res += elem;
        }
        res /= this.temperatureSeries.length;
        return res;
    }

    public double deviation() {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double mean = this.average();
        double sd = 0;
        for (double elem:
             this.temperatureSeries) {
            sd += Math.pow(elem - mean, 2);
        }
        sd /= this.temperatureSeries.length;
        sd = Math.sqrt(sd);

        return sd;
    }

    public double min() {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double min = this.temperatureSeries[0];
        for (double elem:
             this.temperatureSeries) {
            if(min > elem){
                min = elem;
            }
        }
        return min;
    }

    public double max() {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double max = this.temperatureSeries[0];
        for (double elem:
                this.temperatureSeries) {
            if(max < elem){
                max = elem;
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double min_dif = Math.abs(this.temperatureSeries[0]);
        double min_dif_elem = this.temperatureSeries[0];
        for (double elem:
             this.temperatureSeries) {
            double dif = Math.abs(elem);
            if(dif <= min_dif){
                if(dif == min_dif && min_dif_elem < elem){
                    min_dif_elem = elem;
                }
                else if(dif != min_dif){
                    min_dif_elem = elem;
                }
                min_dif = dif;
            }
        }
        return min_dif_elem;
    }

    public double findTempClosestToValue(double tempValue) {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double min_dif = Math.abs(tempValue - this.temperatureSeries[0]);
        double min_dif_elem = this.temperatureSeries[0];
        for (double elem:
                this.temperatureSeries) {
            double dif = Math.abs(tempValue - elem);
            if (dif <= min_dif){
                if (dif == min_dif && min_dif_elem < elem){
                    min_dif_elem = elem;
                }
                else if (dif != min_dif){
                    min_dif_elem = elem;
                }
                min_dif = dif;
            }
        }
        return min_dif_elem;
    }

    public double[] findTempsLessThen(double tempValue) {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double[] arr = new double[this.temperatureSeries.length];
        int counter = 0;
        for (double elem:
            this.temperatureSeries) {
            if (elem < tempValue) {
                arr[counter] = elem;
                counter += 1;
            }
        }
        arr = Arrays.copyOf(arr, counter);
        return arr;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double[] arr = new double[this.temperatureSeries.length];
        int counter = 0;
        for (double elem:
                this.temperatureSeries) {
            if (elem > tempValue) {
                arr[counter] = elem;
                counter += 1;
            }
        }
        arr = Arrays.copyOf(arr, counter);
        return arr;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (this.actualSize == 0){
            throw new IllegalArgumentException();
        }
        double avg = this.average();
        double sd = this.deviation();
        double min = this.min();
        double max = this.max();
        return new TempSummaryStatistics(avg, sd, min, max);
    }

    public int addTemps(double... temps) {
        if (this.check_for_lower_bound(temps)){
            throw new InputMismatchException();
        }
        if (this.temperatureSeries.length < actualSize+temps.length) {
            this.temperatureSeries = Arrays.copyOf(this.temperatureSeries, this.temperatureSeries.length * 2);
        }
        int size_before = this.actualSize;
        for (int i = 0; i < temps.length; i++){
            this.temperatureSeries[i+size_before] = temps[i];
            this.actualSize += 1;
        }
        return this.actualSize;
    }

    private Boolean check_for_lower_bound(double[] arr){
        for (double elem:
             arr) {
            if(elem < -273){
                return true;
            }
        }
        return false;
    }
}
