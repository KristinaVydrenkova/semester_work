package ru.itis.dto;

import java.util.Objects;

public class EntryTestForm {
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;

    public EntryTestForm() {
    }

    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public String getQ5() {
        return q5;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntryTestForm that = (EntryTestForm) o;
        return q1 == that.q1 &&
                q2 == that.q2 &&
                q3 == that.q3 &&
                q4 == that.q4 &&
                q5 == that.q5;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q1, q2, q3, q4, q5);
    }
}
