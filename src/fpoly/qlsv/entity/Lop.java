/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

/**
 *
 * @author hoang
 */
public class Lop {
    private String MaLop;
    private String TenLop;
    private String MaNganh;

    public Lop() {
    }

    public Lop(String MaLop, String TenLop, String MaNganh) {
        this.MaLop = MaLop;
        this.TenLop = TenLop;
        this.MaNganh = MaNganh;
    }

    public String getMaLop() {
        return MaLop;
    }

    public void setMaLop(String MaLop) {
        this.MaLop = MaLop;
    }

    public String getTenLop() {
        return TenLop;
    }

    public void setTenLop(String TenLop) {
        this.TenLop = TenLop;
    }

    public String getMaNganh() {
        return MaNganh;
    }

    public void setMaNganh(String MaNganh) {
        this.MaNganh = MaNganh;
    }


    
}
