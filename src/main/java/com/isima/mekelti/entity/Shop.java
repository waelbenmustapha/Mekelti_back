package com.isima.mekelti.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nom;
  private String type;
  private String image;
  private Double latitude;
  private Double longitude;
  private int livraison;
  @OneToOne
  @JsonIgnore
  private Chef chef;
  @OneToMany
  private List<Menu> menus;
}
