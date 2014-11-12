package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;

/**
 * Created by Bogdan on 11/11/2014.
 */
@MyController(urlPath = "/employee")
public class EmployeeController
{

    @MyRequestMethod(urlPath = "/all")
    public String getAllEmployees()
    {
        return "allEmplployees";
    }

    @MyRequestMethod(urlPath = "/one")
    public String getOneEmployee()
    {
        return "oneEmployee";
    }
}
