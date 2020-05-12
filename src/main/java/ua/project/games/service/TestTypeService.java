package ua.project.games.service;

import lombok.NonNull;
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

    /**
     * method wrapper for controller interaction with TestType repository. Use to find TestType by its id</br>
     * метод обертка для взаємодії контролера та TestType репозиторія. Використовувати для отриманння TestType по id
     * @param id    id of testType we wont to find</br>
     *              id об'єкта testType якого ми хочемо знайти
     * @return      TestType object with specified id</br>
     *              TestType об'єкт з зазначеним id
     * @see TestTypeRepository
     * @see TestType
     */
    public Optional<TestType> getById(long id) {
        return testTypeRepository.findById(id);
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

    /**
     * method for updating TestType entity and save it to repository</br>
     * метод для оновлення сутноті TestType і збереження до бази даних
     * @param testTypeToUpdate      TestType object that from db that we wont to update</br>
     *                              об'єкт TestType з бази даних який ми хочемо оновити
     * @param testType              TestType object with fields that you'll set to entity you wont update</br>
     *                              TestType об'єкт з полями які ми будемо передавати до сутності яку хочемо оновити
     */
    public void updateTestType(@NonNull TestType testTypeToUpdate,@NonNull TestType testType) {
        testTypeToUpdate.setTestType(testType.getTestType());
        testTypeToUpdate.setCurrentStatus(testType.getCurrentStatus());
        testTypeToUpdate.setTestURL(testType.getTestURL());
        testTypeRepository.save(testTypeToUpdate);
    }
}
