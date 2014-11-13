package ro.z2h.service;

import ro.z2h.domain.Department;

import java.util.List;

/**
 * Created by iancu_000 on 13-Nov-14.
 */

public interface DepartmentService
{
    List<Department> findAllDepartments();
}
