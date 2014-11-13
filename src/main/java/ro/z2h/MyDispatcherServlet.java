package ro.z2h;


import org.codehaus.jackson.map.ObjectMapper;
import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;
import ro.z2h.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Array;
import java.text.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Bogdan on 11/11/2014.
 */
public class MyDispatcherServlet extends HttpServlet
{
    Map<String, MethodAttributes> map = new HashMap<String, MethodAttributes>();
    @Override
    public void init() throws ServletException
    {

        try
        {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");
            for(Class c : classes)
            {
                if(c.isAnnotationPresent(MyController.class))
                {
                    MyController ctrlAnnotation = (MyController)c.getAnnotation(MyController.class);
                    System.out.println("Controller: " + ctrlAnnotation.urlPath());

                    Method methods[] = c.getMethods();
                    for (Method method : methods)
                    {
                        if(method.isAnnotationPresent(MyRequestMethod.class))
                        {
                            MyRequestMethod methAnnotation = (MyRequestMethod)method.getAnnotation(MyRequestMethod.class);
                            String completePath =ctrlAnnotation.urlPath()+ methAnnotation.urlPath();
                            System.out.println("Complete path: " + completePath);
                            System.out.println("Method path: " + methAnnotation.urlPath());
                            System.out.println("Method type: " + methAnnotation.methodType());

                            MethodAttributes methodAttributes = new MethodAttributes();
                            methodAttributes.setControllerClass(c.getName());
                            methodAttributes.setMethodName(method.getName());
                            methodAttributes.setMethodType(methAnnotation.methodType());
                            map.put(completePath, methodAttributes);
                        }

                    }
                }
            }

            System.out.println("Map: " + map);

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
        MethodAttributes methodAttributes;
//        if(pathInfo.startsWith("/employee"))
//        {
//            EmployeeController controller = new EmployeeController();
//            return controller.getAllEmployees();
//        }
//        else if(pathInfo.startsWith("/department"))
//        {
//            DepartmentController controller = new DepartmentController();
//            return controller.getAllDepartments();
//        }

        methodAttributes = map.get(pathInfo);
        if(methodAttributes != null)
        {
            try
            {
                Class<?> controllerClass = Class.forName(methodAttributes.getControllerClass());
                Object controllerInstance = controllerClass.newInstance();
                Method method = controllerClass.getMethod(methodAttributes.getMethodName());
                //Enumeration<String> stringEnum = req.getParameterNames();
                Object responseProcesare = method.invoke(controllerInstance);
                return responseProcesare;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*Used to send the view to the client*/
    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(r);
        writer.printf(s);
    }

    private void sendException(Exception ex, HttpServletRequest req, HttpServletResponse resp)
    {
        System.out.println("There was an exception");
    }
}
