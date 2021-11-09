package ru.itis.models;

import java.util.Objects;

public class Task {
    private int id;
    private int teacherId;
    private String task;
    private String deadline;
    private String teacherName;

    public Task() {
    }

    public Task(int id, int teacherId, String task, String deadline) {
        this.id = id;
        this.teacherId = teacherId;
        this.task = task;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return id == task1.id &&
                teacherId == task1.teacherId &&
                Objects.equals(task, task1.task) &&
                Objects.equals(deadline, task1.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacherId, task, deadline);
    }
}
