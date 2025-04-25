package com.chatop.backend.dto;

public class RentalRequestDto {
  private String name;
  private double surface;
  private double price;
  private String picture;
  private String description;
  private Integer owner_id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getSurface() {
    return surface;
  }

  public void setSurface(double surface) {
    this.surface = surface;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getOwner_id() {
    return owner_id;
  }

  public void setOwner_id(Integer owner_id) {
    this.owner_id = owner_id;
  }
}
