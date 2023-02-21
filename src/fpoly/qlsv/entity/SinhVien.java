/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

/**
 *
 * @author hoang
 */
public class SinhVien {
	private String MaSV; 
	private String TenSV; 
	private boolean GioiTinh;
	private String DiaChi; 
	private String Email; 
	private String Sđt;	 
	private String MaL;
        private String Anh;

    public SinhVien() {
    }

    public SinhVien(String MaSV, String TenSV, boolean GioiTinh, String DiaChi, String Email, String Sđt, String MaL, String Anh) {
        this.MaSV = MaSV;
        this.TenSV = TenSV;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.Email = Email;
        this.Sđt = Sđt;
        this.MaL = MaL;
        this.Anh = Anh;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public String getTenSV() {
        return TenSV;
    }

    public void setTenSV(String TenSV) {
        this.TenSV = TenSV;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSđt() {
        return Sđt;
    }

    public void setSđt(String Sđt) {
        this.Sđt = Sđt;
    }

    public String getMaL() {
        return MaL;
    }

    public void setMaL(String MaL) {
        this.MaL = MaL;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String Anh) {
        this.Anh = Anh;
    }

    
}
