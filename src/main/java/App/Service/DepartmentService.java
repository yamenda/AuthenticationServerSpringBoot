package App.Service;

import App.Model.Department;
import App.Repository.DepartmentRepository;
import App.Util.IterableConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public Department insert(Department department) {
        return this.departmentRepository.save(department);
    }

    public List<Department> getAll() {
        return IterableConverter.toList(this.departmentRepository.findAll());
    }

    public Department find(Integer id) {
        return this.departmentRepository.findOne(id);
    }
}
