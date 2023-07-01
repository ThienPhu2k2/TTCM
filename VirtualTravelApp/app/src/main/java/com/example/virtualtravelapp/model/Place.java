package com.example.virtualtravelapp.model;

public class Place {
	private int id;
	private String name;
	private String image;
	private String latlng;
	private String detail;
	private int idDiaDanh;

	private int price;

	private int age;

	private int weather;

	public Place(){}

	public Place(int id, String name, String image, String latlng, String detail, int idDiaDanh, int price, int age, int weather) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.latlng = latlng;
		this.detail = detail;
		this.idDiaDanh = idDiaDanh;
		this.price = price;
		this.age = age;
		this.weather = weather;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLatlng() {
		return latlng;
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getIdDiaDanh() {
		return idDiaDanh;
	}

	public void setIdDiaDanh(int idDiaDanh) {
		this.idDiaDanh = idDiaDanh;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWeather() {
		return weather;
	}

	public void setWeather(int weather) {
		this.weather = weather;
	}

	/*public Place(int id,String name,String image,String latlng,String detail,int idDiaDanh ){
		this.id = id;
		this.name = name;
		this.image = image;
		this.latlng = latlng;
		this.detail = detail;
		this.idDiaDanh = idDiaDanh;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLatlng() {
		return latlng;
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getIdDiaDanh() {
		return idDiaDanh;
	}

	public void setIdDiaDanh(int idDiaDanh) {
		this.idDiaDanh = idDiaDanh;
	}*/
}
