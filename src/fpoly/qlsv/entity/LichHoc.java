/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

import java.util.Date;

/**
 *
 * @author hoang
 */
public class LichHoc {

    private String MaPH;
    private Date ThoiGian;
    private String MaMon;
    private String MaGV;
    private String MaL;

    public LichHoc() {
    }

    public LichHoc(String MaPH, Date ThoiGian, String MaMon, String MaGV, String MaL) {
        this.MaPH = MaPH;
        this.ThoiGian = ThoiGian;
        this.MaMon = MaMon;
        this.MaGV = MaGV;
        this.MaL = MaL;
    }

    public String getMaPH() {
        return MaPH;
    }

    public void setMaPH(String MaPH) {
        this.MaPH = MaPH;
    }

    public Date getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(Date ThoiGian) {
        this.ThoiGian = ThoiGian;
    }

    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String MaMon) {
        this.MaMon = MaMon;
    }

    public String getMaGV() {
        return MaGV;
    }

    public void setMaGV(String MaGV) {
        this.MaGV = MaGV;
    }

    public String getMaL() {
        return MaL;
    }

    public void setMaL(String MaL) {
        this.MaL = MaL;
    }

}
