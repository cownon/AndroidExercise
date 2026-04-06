package com.example.quanlysinhvien.model;
import java.util.Date;

public class SinhVien {
    private String id;
    private String hoten;
    private Date ngaysinh;
    private Integer lophocid;

    public SinhVien() {}

    public SinhVien(String id, String hoten, Date ngaysinh, Integer lophocid) {
        this.id = id;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.lophocid = lophocid;
    }

    // Bạn generate Getters và Setters cho tất cả các thuộc tính ở đây nhé

    public String getId() {
        return id;
    }

    public String getHoten() {
        return hoten;
    }

    public Integer getLophocid() {
        return lophocid;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setLophocid(Integer lophocid) {
        this.lophocid = lophocid;
    }
}