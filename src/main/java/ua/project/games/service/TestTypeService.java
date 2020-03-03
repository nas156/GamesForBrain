package ua.project.games.service;

import org.springframework.stereotype.Service;
import ua.project.games.entity.TestType;
import ua.project.games.entity.enums.CurrentStatus;
import ua.project.games.repository.TestTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestTypeService {

    private final TestTypeRepository testTypeRepository;


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
