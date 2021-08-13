package com.isima.mekelti.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isima.mekelti.Repositroy.ChefRepository;
import com.isima.mekelti.Repositroy.UtilisateurRepository;
import com.isima.mekelti.entity.Chef;
import com.isima.mekelti.entity.Utilisateur;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/chef")
public class UtilisateurController {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final ChefRepository chefrepo;
private final UtilisateurRepository userrepo;
  @GetMapping("/getall")
  public Iterable<Chef> returnallchefs() {

    return chefrepo.findAll();

  }

@PutMapping("/modifiercompte")
public void modifiercompte(@Validated @RequestBody Utilisateur user)
{
  Utilisateur util = userrepo.findById(user.getId()).get();
  util.setNom(user.getNom());
  util.setImage(user.getImage());
  util.setNumeroTelephone(user.getNumeroTelephone());

  if(user.getMotDePasse()!="")

  {  String encodedPassword =  bCryptPasswordEncoder.encode(user.getMotDePasse());

    util.setMotDePasse(encodedPassword);}
  userrepo.save(util);
}
  @PostMapping("/ajouter")
  public void ajouterchef(@Validated @RequestBody Utilisateur user) {
    Utilisateur newchef = new Utilisateur();
    newchef.setNom(user.getNom());
    newchef.setNumeroTelephone(user.getNumeroTelephone());
    userrepo.save(newchef);
  }
@PostMapping ("/fav/{userid}/{chefid}")
  public void fav(@PathVariable("userid") Long userid,@PathVariable("chefid") Long chefid)
{
  Utilisateur utilis = userrepo.findById(userid).get();
  utilis.getFavoris().add(chefrepo.findById(chefid).get());
  userrepo.save(utilis);

}

}
