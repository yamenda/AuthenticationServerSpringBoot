package App.Service;

import App.Model.Employee;
import App.Repository.EmployeeRepository;
import App.Util.IterableConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee insert(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public List<Employee> getAll() {
        return IterableConverter.toList(this.employeeRepository.findAll());
    }

    public Employee find(Integer id) {
        return this.employeeRepository.findOne(id);
    }
}
