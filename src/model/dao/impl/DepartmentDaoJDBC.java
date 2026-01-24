package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                "INSERT INTO department (Name) "
                    +"Values (?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, department.getName());
            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    department.setId(id);
                }
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResult(rs);
        }

    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement(
              "UPDATE department\n" +
                      "SET Name = ?\n" +
                      "WHERE id = ?;"
            );

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
       PreparedStatement st = null;
       ResultSet rs = null;

       try{
           st = conn.prepareStatement(
                   "SELECT id, name\n" +
                           "FROM department\n" +
                           "WHERE id = ?;"
           );

           st.setInt(1, id);
           rs = st.executeQuery();

           if(rs.next()){
               return instatiateDepartment(rs);
           }

           return null;

       }catch (SQLException e){
           throw new DbException(e.getMessage());
       }finally{
           DB.closeStatement(st);
           DB.closeResult(rs);
       }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Department> list = new ArrayList<>();

        try{
            st = conn.prepareStatement(
                    "SELECT id, Name\n" +
                            "FROM department\n"
            );

            rs = st.executeQuery();
            while (rs.next()){
                list.add(instatiateDepartment(rs));
            }
            return list;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResult(rs);
        }


    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}
