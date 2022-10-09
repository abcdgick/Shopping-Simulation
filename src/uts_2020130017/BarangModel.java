/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uts_2020130017;

import javafx.scene.image.Image;

/**
 *
 * @author USER
 */
public class BarangModel {
    private String IDBarang, namaBarang, jenis;
    private Image image;
    private int harga, jumlah, stok, sub;
    private double diskon;

    public BarangModel(){
        
    }
    
    public BarangModel(String IDBarang, String namaBarang, String jenis, Image image, int harga, int jumlah, int stok, double diskon) {
        this.IDBarang = IDBarang;
        this.namaBarang = namaBarang;
        this.jenis = jenis;
        this.image = image;
        this.harga = harga;
        this.jumlah = jumlah;
        this.stok = stok;
        this.diskon = diskon;
        this.sub = 0;
    }
    
    public String getIDBarang() {
        return IDBarang;
    }

    public void setIDBarang(String IDBarang) {
        this.IDBarang = IDBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }
    
    
}
