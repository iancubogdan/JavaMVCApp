package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 11/11/2014.
 */
@MyController(urlPath = "/department")
public class DepartmentController
{

    @MyRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartments()
    {
        List<Department> departments = new ArrayList<Department>();
        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("HR");
        Department department1 = new Department();
        department1.setId(2L);
        department1.setDepartmentName("Devs");
        departments.add(department);
        departments.add(department1);
        return departments;
    }
}
