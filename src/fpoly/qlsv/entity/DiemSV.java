/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpoly.qlsv.entity;

/**
 *
 * @author hoang
 */
public class DiemSV {

    private int id;
    private String MaSV;
    private String HoTen;
    private String TenMon;
    private double Lab;
    private double Assment;
    private double Quiz;
    private double diemTB;

    public DiemSV() {
    }

    public DiemSV(int id, String MaSV, String HoTen, String TenMon, double Lab, double Assment, double Quiz, double diemTB) {
        this.id = id;
        this.MaSV = MaSV;
        this.HoTen = HoTen;
        this.TenMon = TenMon;
        this.Lab = Lab;
        this.Assment = Assment;
        this.Quiz = Quiz;
        this.diemTB = diemTB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    public double getLab() {
        return Lab;
    }

    public void setLab(double Lab) {
        this.Lab = Lab;
    }

    public double getAssment() {
        return Assment;
    }

    public void setAssment(double Assment) {
        this.Assment = Assment;
    }

    public double getQuiz() {
        return Quiz;
    }

    public void setQuiz(double Quiz) {
        this.Quiz = Quiz;
    }

    public double getDiemTB() {
        return (Lab + Assment + Quiz) / 3;
    }

    public void setDiemTB(double diemTB) {
        this.diemTB = diemTB;
    }

}
