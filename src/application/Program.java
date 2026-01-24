package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: Seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: Seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for(Seller sellers: list){
            System.out.println(sellers);
        }

        System.out.println("\n=== TEST 3: Seller findAll ===");
        list = sellerDao.findAll();
        for(Seller sellers: list){
            System.out.println(sellers);
        }

    }
}
