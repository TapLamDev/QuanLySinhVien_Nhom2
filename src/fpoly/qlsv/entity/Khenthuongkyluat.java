/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

import java.util.Date;

/**
 *
 * @author Phuc
 */
public class Khenthuongkyluat {
    private String MaSV,HocKy;
    private Date NgayThang;
    private String NoiDung,Nguoiky,GhiChu;

    public Khenthuongkyluat() {
    }

    public Khenthuongkyluat(String MaSV, String HocKy, Date NgayThang, String NoiDung, String Nguoiky, String GhiChu) {
        this.MaSV = MaSV;
        this.HocKy = HocKy;
        this.NgayThang = NgayThang;
        this.NoiDung = NoiDung;
        this.Nguoiky = Nguoiky;
        this.GhiChu = GhiChu;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public String getHocKy() {
        return HocKy;
    }

    public void setHocKy(String HocKy) {
        this.HocKy = HocKy;
    }

    public Date getNgayThang() {
        return NgayThang;
    }

    public void setNgayThang(Date NgayThang) {
        this.NgayThang = NgayThang;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String NoiDung) {
        this.NoiDung = NoiDung;
    }

    public String getNguoiky() {
        return Nguoiky;
    }

    public void setNguoiky(String Nguoiky) {
        this.Nguoiky = Nguoiky;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    
}
