package com.isima.mekelti.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Demande {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private Utilisateur utilisateur;
  @ManyToOne
  private Chef chef;
  @ManyToMany
  private List<Plat> plats;
  private Double montant;
  private int livraison;
  private Double note;
  private int statut;

}
