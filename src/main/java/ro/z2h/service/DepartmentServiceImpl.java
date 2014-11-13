package ro.z2h.service;

import ro.z2h.dao.DepartmentDao;
import ro.z2h.domain.Department;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by iancu_000 on 13-Nov-14.
 */
public class DepartmentServiceImpl implements DepartmentService
{

    @Override
    public List<Department> findAllDepartments()
    {
        Connection con = DatabaseManager.getConnection("ZTH_10", "passw0rd");

        try
        {
            return new DepartmentDao().getAllDepartments(con);
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
