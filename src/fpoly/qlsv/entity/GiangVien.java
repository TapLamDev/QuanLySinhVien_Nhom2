/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

/**
 *
 * @author hoang
 */
public class GiangVien {

    private String MaGV;
    private String TenGV;
    private boolean GioiTinh;
    private String ĐiaChi;
    private String Email;
    private String Sđt;
    private String Anh;

    public GiangVien() {
    }

    public GiangVien(String MaGV, String TenGV, boolean GioiTinh, String ĐiaChi, String Email, String Sđt, String Anh) {
        this.MaGV = MaGV;
        this.TenGV = TenGV;
        this.GioiTinh = GioiTinh;
        this.ĐiaChi = ĐiaChi;
        this.Email = Email;
        this.Sđt = Sđt;
        this.Anh = Anh;
    }

    public String getMaGV() {
        return MaGV;
    }

    public void setMaGV(String MaGV) {
        this.MaGV = MaGV;
    }

    public String getTenGV() {
        return TenGV;
    }

    public void setTenGV(String TenGV) {
        this.TenGV = TenGV;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getĐiaChi() {
        return ĐiaChi;
    }

    public void setĐiaChi(String ĐiaChi) {
        this.ĐiaChi = ĐiaChi;
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

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String Anh) {
        this.Anh = Anh;
    }

}
