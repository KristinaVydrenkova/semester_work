package ru.itis.services;

import ru.itis.database.TestsRepository;
import ru.itis.dto.CheburashkaTestForm;
import ru.itis.dto.EntryTestForm;
import ru.itis.models.Point;
import ru.itis.models.User;

import java.util.List;

public class TestCheckServiceImpl implements TestCheckService {
    private TestsRepository testsRepository;
    private final static int ENTRY_TEST_NUMBER = 1;
    private final static int CHEBURASHKA_TEST_NUMBER = 2;
    private final static int NUMBER_OF_POSSIBLE_SCORES_ENTRY_TEST = 5;
    private final static int NUMBER_OF_POSSIBLE_SCORES_CHEBURASHKA_TEST = 5;
    private final static String[] TEST_NAMES = {"Входной тест","Чебурашка"};


    public TestCheckServiceImpl(TestsRepository testsRepository){
        this.testsRepository = testsRepository;
    }
    @Override
    public int checkEntryTest(EntryTestForm form, User user) {
        if(testsRepository.getScores(user.getId(),ENTRY_TEST_NUMBER)==-1) {
            List<String> answers = testsRepository.getTestAnswers(ENTRY_TEST_NUMBER);
            int scores = 0;
            if (form.getQ1().equals(answers.get(0))) {
                scores++;
            }
            if (form.getQ2().equals(answers.get(1))) {
                scores++;
            }
            if (form.getQ3().equals(answers.get(2))) {
                scores++;
            }
            if (form.getQ4().equals(answers.get(3))) {
                scores++;
            }
            if (form.getQ5().equals(answers.get(4))) {
                scores++;
            }
            testsRepository.addScores(scores, user.getId(), ENTRY_TEST_NUMBER,NUMBER_OF_POSSIBLE_SCORES_ENTRY_TEST);
            return scores;
        }else {
            return -1;
        }
    }

    @Override
    public List<Point> getAllResults(int studentId) {
        List<Point> points = testsRepository.getAllScores(studentId);
        if(points.size()!=0){
            for(int i = 0; i<points.size(); i++){
                points.get(i).setTestName(TEST_NAMES[i]);
            }
        }
        return points;
    }

    @Override
    public int checkCheburashkaTest(CheburashkaTestForm form, User user) {
        if(testsRepository.getScores(user.getId(),CHEBURASHKA_TEST_NUMBER)==-1) {
            List<String> answers = testsRepository.getTestAnswers(CHEBURASHKA_TEST_NUMBER);
            int scores = 0;
            if (form.getQ1().equals(answers.get(0))) {
                scores++;
            }
            if (form.getQ2().equals(answers.get(1))) {
                scores++;
            }
            if (form.getQ3().equals(answers.get(2))) {
                scores++;
            }
            if (form.getQ4().equals(answers.get(3))) {
                scores++;
            }
            if (form.getQ5().equals(answers.get(4))) {
                scores++;
            }
            testsRepository.addScores(scores, user.getId(), CHEBURASHKA_TEST_NUMBER,NUMBER_OF_POSSIBLE_SCORES_CHEBURASHKA_TEST);
            return scores;
        }else {
            return -1;
        }
    }
}
