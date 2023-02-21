/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

/**
 *
 * @author hoang
 */
public class HocBong {
    private String maSV;
    private String nganh;
    private String loaiHocBong;
    private String xepLoai;
    private String ngayNhan;

    public HocBong() {
    }

    public HocBong(String maSV, String nganh, String loaiHocBong, String xepLoai, String ngayNhan) {
        this.maSV = maSV;
        this.nganh = nganh;
        this.loaiHocBong = loaiHocBong;
        this.xepLoai = xepLoai;
        this.ngayNhan = ngayNhan;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getLoaiHocBong() {
        return loaiHocBong;
    }

    public void setLoaiHocBong(String loaiHocBong) {
        this.loaiHocBong = loaiHocBong;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = ngayNhan;
    }
    
}
