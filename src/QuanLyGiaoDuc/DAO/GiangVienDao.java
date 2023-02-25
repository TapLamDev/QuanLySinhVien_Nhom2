/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyGiaoDuc.DAO;

import fpoly.qlsv.entity.GiangVien;
import Xjdbc.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class GiangVienDao{
     
    String INSERT_SQL = "exec sp_DangKy ?,'123',?,?,?,?,?,?,''";
    String UPDATE_SQL = "UPDATE GiangVien ?,?,?,?,?,?,?,?";
    String DELETE_SQL = "exec sp_DeleteGV ?";
    String SELECT_ALL_SQL ="SELECT * FROM GiangVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM GiangVien WHERE MaGV = ?"; 
    
    public void insert(GiangVien entity) {
        Jdbc.update(INSERT_SQL, entity.getMaGV(),entity.getTen(),entity.getGioiTinh(),entity.getDiaChi(),entity.getEmail(),entity.getSdt(),entity.getAnh());
    }

    public void update(GiangVien entity) {
        Jdbc.update(UPDATE_SQL, entity.getMaGV(),entity.getTen(),entity.getGioiTinh(),entity.getDiaChi(),entity.getEmail(),entity.getSdt(),entity.getAnh());
    }

    public void delete(String id) {
        Jdbc.update(DELETE_SQL, id);
    }

    public List<GiangVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    public GiangVien selectById(String id) {
        List<GiangVien> list = selectBySQL(SELECT_BY_ID_SQL, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    protected List<GiangVien> selectBySQL(String sql, Object... args) {
        List<GiangVien> list = new ArrayList<GiangVien>();
        try {
            ResultSet rs = null;
            try {
                rs = Jdbc.query(sql, args);
                while(rs.next()){
                GiangVien gv = new GiangVien(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                list.add(gv);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
//    public List<GiangVien> selectByKeywords(String key){
//        String sql = "SELECT * FROM ChuyenDe WHERE MaGV LIKE ?";
//        return this.selectBySQL(sql, "%"+key+"%");
//    }
}
