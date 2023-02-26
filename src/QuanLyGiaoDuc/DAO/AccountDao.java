/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyGiaoDuc.DAO;

import fpoly.qlsv.entity.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Xjdbc.XJdbcHelper;

/**
 *
 * @author hoang
 */
public class AccountDao extends QLGDDAO<Account, String> {

   
    String INSERT_SQL = "INSERT INTO TaiKhoan(TenTK,MatKhauTK) VALUES(?,?)";
     String UPDATE_SQL = "UPDATE TaiKhoan set MatKhauTK = ?  WHERE TenTK = ?";
     String DELETE_SQL = "DELETE FROM TaiKhoan WHERE TenTK =?";
     String SELECT_ALL_SQL ="SELECT * FROM TaiKhoan";
     String SELECT_BY_ID_SQL = "SELECT * FROM TaiKhoan WHERE TenTK = ?"; 
    
    @Override
    public void insert(Account entity) {
        XJdbcHelper.update(INSERT_SQL, entity.getUsername(),entity.getPassword());
    }

    @Override
    public void update(Account entity) {
        XJdbcHelper.update(UPDATE_SQL, entity.getPassword(),entity.getUsername());
    }

    @Override
    public void delete(String id) {
        XJdbcHelper.update(DELETE_SQL,id);
    }

    @Override
    public List<Account> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Account selectById(String id) {
        List<Account> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<Account> selectBySQL(String sql, Object... args) {
        List<Account> list = new ArrayList<Account>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbcHelper.query(sql, args);
                while(rs.next()){
                    Account nv = readFromResultSet(rs);
                    list.add(nv);
                }
                
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
        
    }
    
    private Account readFromResultSet(ResultSet rs) throws SQLException {
        Account nv = new Account();
        nv.setUsername(rs.getString("TenTK"));
        nv.setPassword(rs.getString("MatKhauTK"));
        return nv;
    }
    
}
