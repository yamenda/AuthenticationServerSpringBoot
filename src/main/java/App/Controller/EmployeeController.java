package App.Controller;

import App.Model.Department;
import App.Model.Employee;
import App.Service.DepartmentService;
import App.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<Employee>();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        this.employees = this.employeeService.getAll();
        model.addAttribute("employees", this.employees);
        return "employees/index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(Model model, @PathVariable Integer id) {
        Employee employee = this.employeeService.find(id);
        model.addAttribute("employee", employee);
        return "employees/show";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        List<Department> departments = this.departmentService.getAll();
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departments);
        return "employees/create";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String store(@ModelAttribute Employee employee) {
        this.employeeService.insert(employee);
        return "redirect:/employee/" + employee.getId();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
