package com.hrd.repository;

import com.hrd.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmployeeRepository {

    @Autowired
    private WebClient.Builder webClient;

    public EmployeeDto getEmployeeById(Long id) {
        EmployeeDto employeeDto = webClient.build().get()
                .uri("http://employee-service/api/v1/employee/{id}", id)
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .block();
        return employeeDto;
    }
}
