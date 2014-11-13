package ro.z2h.service;

import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Bogdan on 11/12/2014.
 */
public class EmployeeServiceImpl implements EmployeeService
{

    @Override
    public List<Employee> findAllEmployees()
    {
        Connection con = DatabaseManager.getConnection("ZTH_10", "passw0rd");
        try
        {
            return new EmployeeDao().getAllEmployees(con);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            DatabaseManager.closeConnection(con);
        }
        return null;
    }

    @Override
    public Employee findOneEmployee(Long id)
    {
        Connection con = DatabaseManager.getConnection("ZTH_10", "passw0rd");

        try
        {
            return new EmployeeDao().getEmployeeById(con,id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            DatabaseManager.closeConnection(con);
        }
        return null;
    }
}
