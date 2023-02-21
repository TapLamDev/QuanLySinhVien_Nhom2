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
public class HocPhi {
    private  String MaSV;
    private String hoTen;
    private String MaKH;
    private String MaHD;
    private double tien;
    private Date thoiGian;

    public HocPhi() {
    }

    public HocPhi(String MaSV, String hoTen, String MaKH, String MaHD, double tien, Date thoiGian) {
        this.MaSV = MaSV;
        this.hoTen = hoTen;
        this.MaKH = MaKH;
        this.MaHD = MaHD;
        this.tien = tien;
        this.thoiGian = thoiGian;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public double getTien() {
        return tien;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }
    
}
