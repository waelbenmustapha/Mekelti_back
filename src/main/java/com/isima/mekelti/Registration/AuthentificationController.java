package com.isima.mekelti.Registration;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isima.mekelti.Repositroy.UtilisateurRepository;
import com.isima.mekelti.Service.ChefService;
import com.isima.mekelti.Service.UtilisateurService;
import com.isima.mekelti.entity.Chef;
import com.isima.mekelti.entity.Utilisateur;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/Authentification")
@AllArgsConstructor
public class AuthentificationController {
 private final UtilisateurService utilisateurService;
private  final UtilisateurRepository utilisateurRepository;
private final ChefService chefService;
 @PostMapping("/registerUser")
 public String register(@RequestBody Utilisateur utilisateur) throws UnsupportedEncodingException, MessagingException {
  return utilisateurService.signup(utilisateur);


}

 @PostMapping("/registerChef")
 public String registerchef(@RequestBody Chef chef) throws UnsupportedEncodingException, MessagingException {
  return chefService.signup(chef);
 }
 @GetMapping("/verify/{code}")
 public String verifyUser(@PathVariable String code) {
  if (utilisateurService.verify(code)) {
   return "verify_success";
  } else {
   return "verify_fail";
  }

 }

 @PostMapping("/login")
 public ResponseEntity login(@Validated @RequestBody Utilisateur utilisateur) {
try{
  if (utilisateur != null) {

   Utilisateur utilisateurinbase = utilisateurRepository.findByEmail(utilisateur.getEmail()).get();
   if (utilisateurinbase != null && utilisateurService.matchPasswords(utilisateur.getMotDePasse(), utilisateurinbase.getMotDePasse())) {
    utilisateurinbase.setNotification(utilisateur.getNotification());
    utilisateurRepository.save(utilisateurinbase);
    return new ResponseEntity<>(utilisateurinbase.getToken(), HttpStatus.OK);
   }
  }
  else {
   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("utilisateur not found");
  }

 }
catch (Exception e) {
 return ResponseEntity.status(HttpStatus.RESET_CONTENT).body("user not found");
}
return null;
 }
 @GetMapping("/userbytoken/{token}")
 public ResponseEntity userbytoken(@PathVariable String token){

  if (token != null) {
   try {

    Utilisateur utilisateur = utilisateurRepository.findByToken(token);
    if(utilisateur!=null) {
     return new ResponseEntity(utilisateur, HttpStatus.OK);
    }

   } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed To get consommateur info");

   }}
  else {
   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token can't be null");
  }
  return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("SOMETHING IS WRONG");

 }

}
