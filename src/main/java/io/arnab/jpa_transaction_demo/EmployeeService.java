package io.arnab.jpa_transaction_demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EmployeeService(EmployeeRepository employeeRepository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional("transactionManager")
    public void createEmployee(CreateEmployeeDTO createEmployeeDTO) throws JsonProcessingException {
        Employee employee = new Employee(createEmployeeDTO.name(), createEmployeeDTO.dob(), createEmployeeDTO.gender().toString());
        employeeRepository.save(employee);
        kafkaTemplate.send("employee-created", objectMapper.writeValueAsString(employee));
    }
}
