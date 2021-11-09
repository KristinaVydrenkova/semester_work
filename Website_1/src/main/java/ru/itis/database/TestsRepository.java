package ru.itis.database;

import ru.itis.dto.EntryTestForm;
import ru.itis.models.Point;

import java.util.List;

public interface TestsRepository {
    List<String> getTestAnswers(int testNumber);
    void addScores(int scores, int studentId, int testNumber,int numberOfPossibleScores);
    int getScores(int studentId,int testNumber);
    List<Point> getAllScores(int studentId);
}
