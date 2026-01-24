package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class ProgramDepartment {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        List<Department> list = departmentDao.findAll();
        for(Department department: list){
            System.out.println(department);
        }

        System.out.println("\n=== Find department by id ===");
        System.out.println(departmentDao.findById(2));

        System.out.println("\n=== Insert department ===");
        Department department = new Department(null, "RH");
        departmentDao.insert(department);
        System.out.println("Department inserted !");

        System.out.println("\n=== Insert department ===");
        department.setName("HR");
        departmentDao.update(department);
        System.out.println("Department updated !");

        sc.close();
    }
}
