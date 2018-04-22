package app.exam.service;

import app.exam.domain.dto.json.EmployeeJSONImportDTO;
import app.exam.domain.entities.Employee;
import app.exam.parser.interfaces.ModelParser;
import app.exam.repository.EmployeeRepository;
import app.exam.service.api.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelParser parser;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelParser parser) {
        this.employeeRepository = employeeRepository;
        this.parser = parser;
    }

    @Override
    public void create(EmployeeJSONImportDTO importDTO) {
        this.employeeRepository.save(parser.convert(importDTO, Employee.class));
    }

    @Override
    public void createMany(EmployeeJSONImportDTO[] importDTO) {
        this.employeeRepository.save(Arrays.stream(importDTO).map(x -> this.parser.convert(x, Employee.class)).collect(Collectors.toList()));
    }
}
