package ua.project.games.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.games.entity.TestType;
import ua.project.games.entity.enums.CurrentStatus;
import ua.project.games.repository.TestTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestTypeService {

    private final TestTypeRepository testTypeRepository;

    /**
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param testTypeRepository об'кт репозиторію TestTypeRepository, який містить медоти для роботи з таблицею test_type
     * @see TestTypeRepository
     * @see Autowired
     */
    @Autowired
    public TestTypeService(TestTypeRepository testTypeRepository) {
        this.testTypeRepository = testTypeRepository;
    }

    public List<TestType> getAll() {
        return testTypeRepository.findAll();
    }

    public void deleteTestbyId(long testTypeId) {
        Optional<TestType> testToUpdate = testTypeRepository.findById(testTypeId);
        testToUpdate.ifPresent(testType -> {
            testType.setCurrentStatus(CurrentStatus.Deleted);
            testTypeRepository.save(testType);
        });
    }

    public void activateTestById(long testTypeId) {
        Optional<TestType> testToUpdate = testTypeRepository.findById(testTypeId);
        testToUpdate.ifPresent(testType -> {
            testType.setCurrentStatus(CurrentStatus.Active);
            testTypeRepository.save(testType);
        });
    }
}
