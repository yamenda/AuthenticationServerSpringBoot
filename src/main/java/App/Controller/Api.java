package App.Controller;


import App.Model.Employee;
import App.Model.Users;
import App.Service.EmployeeService;
import App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class Api {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/allemployee", method = RequestMethod.GET)
    public List<Employee> allEmployee() {
        List<Employee> employees = this.employeeService.getAll();
        return employees;
    }


    @RequestMapping(value = "/alluser", method = RequestMethod.GET)
    public List<Users> alluser() {
        List<Users> users = this.userService.getAll();
        return users;
    }

}
