/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

/**
 *
 * @author hoang
 */
public class TotNghiep {
    private String maSV;
    private String nganh;
    private String xepLoai;

    public TotNghiep() {
    }

    public TotNghiep(String maSV, String nganh, String xepLoai) {
        this.maSV = maSV;
        this.nganh = nganh;
        this.xepLoai = xepLoai;
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

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }
    
    
}
