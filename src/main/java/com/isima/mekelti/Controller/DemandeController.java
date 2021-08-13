package com.isima.mekelti.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isima.mekelti.Repositroy.ChefRepository;
import com.isima.mekelti.Repositroy.DemandeRepository;
import com.isima.mekelti.entity.Chef;
import com.isima.mekelti.entity.Demande;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/demande")
public class DemandeController {
private final DemandeRepository demandeRepository;
private final ChefRepository chefrepo;
@GetMapping("/getdemandechef/{id}")
  public ResponseEntity getdemandechef(@PathVariable Long id)
{return new ResponseEntity(demandeRepository.findByChefId(id), HttpStatus.OK);}
  @GetMapping("/getdemandeutilisateur/{id}")
  public ResponseEntity getdemandeuser(@PathVariable Long id)
  {return new ResponseEntity(demandeRepository.findByUtilisateurId(id), HttpStatus.OK);}

@PostMapping("/envoyerdemande")
  public ResponseEntity envoyerdemande(@RequestBody Demande demande)
{
  demandeRepository.save(demande);
  return ResponseEntity.status(HttpStatus.OK).body("demande ajoutée");
}
  @PutMapping("/notterDemande/{iddemande}/{note}/{idchef}")
  public ResponseEntity noterdemande(@PathVariable("iddemande") Long iddemande,@PathVariable("note") Double note,@PathVariable("idchef") Long idchef)
  {
// ajouter note ll demande
    System.out.println("here here here here here here here here here here here here here");
   Demande dem = demandeRepository.findById(iddemande).get();
    System.out.println("here here here here here here here here here here here here here");
   dem.setNote(note);
    System.out.println("here here here here here here here here here here here here here");
   demandeRepository.save(dem);
    //calculate note chef jdida
    try {
      if (idchef != null) {
        int i;
        int som=0;
        Double average = 0D;
        List<Demande> feedbacks = demandeRepository.findByChefId(idchef);
        for (i = 0; i < feedbacks.size(); i++) {
          if(feedbacks.get(i).getNote()!=null){
            som++;
          average = average + feedbacks.get(i).getNote();
        }}
        average = average / som;
        Chef chef = chefrepo.findById(idchef).get();
        chef.setNote(average);
        chefrepo.save(chef);
        return new ResponseEntity(average, HttpStatus.OK);
      }

    } catch (Exception e) {
      ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Echec");
    }
    return new ResponseEntity( HttpStatus.OK);

  }
@PutMapping("/changerstatut/{statut}/{id}")
public ResponseEntity getdemandechef(@PathVariable("statut") int statut,@PathVariable("id") Long id)
{
 Demande demande = demandeRepository.findById(id).get();
 demande.setStatut(statut);
 demandeRepository.save(demande);
  return ResponseEntity.status(HttpStatus.OK).body("statut changée");
}

}
