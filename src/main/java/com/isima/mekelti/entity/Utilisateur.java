package com.isima.mekelti.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import com.isima.mekelti.UtilisateurRole;

import lombok.Data;


@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "Role",
    discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("Utilisateur")

public class Utilisateur {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nom;
  private String email;
  private String motDePasse;
  private String numeroTelephone;
  private String image;
  private String notification;
  private String verificationCode;
  private String token;
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
      name = "Utilisateur_shop",
      joinColumns = @JoinColumn(name = "Utilisateur_id"),
      inverseJoinColumns = @JoinColumn(name = "Chef_id")


  )
  private List<Chef> favoris = new ArrayList<>();
  private Boolean enabled = false;
  @Column(name = "Role", insertable = false, updatable = false)
  private String Role = getClass().getAnnotation(DiscriminatorValue.class).value();

}