package ro.z2h.service;

import ro.z2h.dao.DepartmentDao;
import ro.z2h.dao.JobDao;
import ro.z2h.domain.Department;
import ro.z2h.domain.Job;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Bogdan on 11/13/2014.
 */
public class JobServiceImpl implements JobService
{
    @Override
    public List<Job> findAllJobs()
    {
        Connection con = DatabaseManager.getConnection("ZTH_10", "passw0rd");

        try
        {
            return new JobDao().getAllJobs(con);
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
