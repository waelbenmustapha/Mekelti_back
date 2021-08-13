package com.isima.mekelti.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data

@DiscriminatorValue("Chef")
@Entity
public class Chef extends  Utilisateur{
  @OneToOne
private Shop shop;
  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "favoris")
  private List<Utilisateur> likes;
  private Double note=0D;

}
