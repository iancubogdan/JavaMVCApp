package ro.z2h;

import ro.z2h.annotation.MyController;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Bogdan on 11/11/2014.
 */
public class MyDispatcherServlet extends HttpServlet
{
    @Override
    public void init() throws ServletException
    {
        try
        {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");
            for(Class c : classes)
            {
                if(c.isAnnotationPresent(MyController.class))
                    System.out.println(c.toString());
            }

        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatchReply("POST", req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatchReply("GET", req, resp);
    }

    private void dispatchReply(String httpMethod, HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        //Dispatch
        Object r = dispatch(httpMethod, req, resp);

        //Reply
        reply(r, req, resp);


        //Catch errors
        Exception ex = null;
        sendException(ex, req, resp);
    }

    /*Where an application controller should be called*/
    private Object dispatch(String httpMethod, HttpServletRequest req, HttpServletResponse resp)
    {
        /*pt /test = Hello
        * pr /employee => allEmployees de la Application Controller*/
        String pathInfo = req.getPathInfo();
        if(pathInfo.startsWith("/employee"))
        {
            EmployeeController controller = new EmployeeController();
            return controller.getAllEmployees();
        }
        else if(pathInfo.startsWith("/department"))
        {
            DepartmentController controller = new DepartmentController();
            return controller.getAllDepartments();
        }
        return "Hello";
    }

    /*Used to send the view to the client*/
    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        PrintWriter writer = resp.getWriter();
        writer.printf(r.toString());
    }

    private void sendException(Exception ex, HttpServletRequest req, HttpServletResponse resp)
    {
        System.out.println("There was an exception");
    }
}
