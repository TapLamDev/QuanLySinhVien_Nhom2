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
public class VayVon {
     private String maSV,TenVV;
    private double Sotienvay;
    private Date Ngayvay,HanTra;
    private String CMND,SoDT;

    public VayVon() {
    }

    public VayVon(String maSV, String TenVV, double Sotienvay, Date Ngayvay, Date HanTra, String CMND, String SoDT) {
        this.maSV = maSV;
        this.TenVV = TenVV;
        this.Sotienvay = Sotienvay;
        this.Ngayvay = Ngayvay;
        this.HanTra = HanTra;
        this.CMND = CMND;
        this.SoDT = SoDT;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenVV() {
        return TenVV;
    }

    public void setTenVV(String TenVV) {
        this.TenVV = TenVV;
    }

    public double getSotienvay() {
        return Sotienvay;
    }

    public void setSotienvay(double Sotienvay) {
        this.Sotienvay = Sotienvay;
    }

    public Date getNgayvay() {
        return Ngayvay;
    }

    public void setNgayvay(Date Ngayvay) {
        this.Ngayvay = Ngayvay;
    }

    public Date getHanTra() {
        return HanTra;
    }

    public void setHanTra(Date HanTra) {
        this.HanTra = HanTra;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }
    
    
}
