package app.exam.config;

import app.exam.domain.dto.json.EmployeeJSONImportDTO;
import app.exam.domain.entities.Employee;
import app.exam.domain.entities.Position;
import app.exam.repository.PositionRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.Optional;

@Configuration
@Transactional
public class MapperConfig {

    private final ModelMapper mapper;

    private final PositionRepository positionRepository;

    @Autowired
    public MapperConfig(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
        mapper = new ModelMapper();
        configure();
    }

    private void configure() {
        employeeImportMapping();
    }

    private void employeeImportMapping() {

        Converter<String, Position> con = ctx -> {
            Position pos = this.positionRepository.findByName(ctx.getSource());
            if(pos == null) {
                pos = new Position();
                pos.setName(ctx.getSource());
                this.positionRepository.save(pos);
            }
            return pos;
        };

        this.mapper.createTypeMap(EmployeeJSONImportDTO.class, Employee.class).addMappings(m -> m.using(con).map(EmployeeJSONImportDTO::getPosition, Employee::setPosition));
    }

    @Bean
    ModelMapper getMapper() {
        return this.mapper;
    }
}
