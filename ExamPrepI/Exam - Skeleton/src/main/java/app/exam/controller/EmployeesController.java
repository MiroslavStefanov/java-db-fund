package app.exam.controller;

import app.exam.domain.dto.json.EmployeeJSONImportDTO;
import app.exam.parser.ValidationUtil;
import app.exam.parser.interfaces.Parser;
import app.exam.service.api.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class EmployeesController {

    private final Parser parser;

    private final EmployeeService employeeService;

    @Autowired
    public EmployeesController(@Qualifier("JSONParser") Parser parser, EmployeeService employeeService) {
        this.parser = parser;
        this.employeeService = employeeService;
    }

    public String importDataFromJSON(String jsonContent){
        StringBuilder sb = new StringBuilder();
        try {
            EmployeeJSONImportDTO[] dtos = this.parser.read(EmployeeJSONImportDTO[].class, jsonContent);
            dtos =
                    Arrays.stream(dtos)
                            .peek(x -> {
                        if(ValidationUtil.isValid(x))
                            sb.append(String.format("Record %s successfully imported.", x.getName())).append("\r\n");
                        else
                            sb.append("Error: invalid data.\r\n");
                    })
                            .filter(ValidationUtil::isValid)
                            .toArray(EmployeeJSONImportDTO[]::new);
            this.employeeService.createMany(dtos);
            return sb.toString();
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
