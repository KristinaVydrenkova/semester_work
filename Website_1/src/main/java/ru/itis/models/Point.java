package ru.itis.models;

import java.util.Objects;

public class Point {
    private int testNumber;
    private String testName;
    private int scores;
    private int maxScore;

    public Point() {
    }

    public Point(int testNumber, String testName, int scores, int maxScore) {
        this.testNumber = testNumber;
        this.testName = testName;
        this.scores = scores;
        this.maxScore = maxScore;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return testNumber == point.testNumber &&
                scores == point.scores &&
                maxScore == point.maxScore &&
                Objects.equals(testName, point.testName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testNumber, testName, scores, maxScore);
    }

    @Override
    public String toString() {
        return "Point{" +
                "testNumber=" + testNumber +
                ", testName='" + testName + '\'' +
                ", scores=" + scores +
                ", maxScore=" + maxScore +
                '}';
    }
}
