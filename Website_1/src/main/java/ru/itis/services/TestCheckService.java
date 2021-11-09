package ru.itis.services;

import ru.itis.dto.CheburashkaTestForm;
import ru.itis.dto.EntryTestForm;
import ru.itis.models.Point;
import ru.itis.models.User;

import java.util.List;

public interface TestCheckService {
    int checkEntryTest(EntryTestForm form, User user);
    List<Point> getAllResults(int studentId);
    int checkCheburashkaTest(CheburashkaTestForm form, User user);
}
