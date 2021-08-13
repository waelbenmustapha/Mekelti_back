package com.isima.mekelti.Controller;

import java.util.List;

import org.apache.coyote.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isima.mekelti.Repositroy.ChefRepository;
import com.isima.mekelti.Repositroy.MenuRepository;
import com.isima.mekelti.Repositroy.PlatRepository;
import com.isima.mekelti.Repositroy.ShopRepository;
import com.isima.mekelti.Repositroy.UtilisateurRepository;
import com.isima.mekelti.entity.Menu;
import com.isima.mekelti.entity.Plat;
import com.isima.mekelti.entity.Shop;
import com.isima.mekelti.entity.Utilisateur;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {
  private final ShopRepository shopRepository;
private final MenuRepository menuRepository;
private final ChefRepository chefRepository;
private final PlatRepository platRepository;
  @GetMapping("/getall")
  public Iterable<Shop> returnallshops() {

    return shopRepository.findAll();

  }
  @GetMapping("/getallmenus")
  public Iterable<Menu> returnfops() {

    return menuRepository.findAll();

  }
  @PostMapping("/ajouterdesplats/{id}")
  public ResponseEntity ajouterplat(@RequestBody List<Plat> plat,@PathVariable Long id){
Menu menu= menuRepository.findById(id).get();

    plat.forEach(element->{
      menu.getPlats().add(element);
    });
menuRepository.save(menu);
    return ResponseEntity.status(HttpStatus.OK).body("Plat ajoutée");

    }
    @DeleteMapping("/supprimerplate/{id}")
    public ResponseEntity suppplat(@PathVariable Long id){
platRepository.delete(platRepository.findById(id).get());
      return ResponseEntity.status(HttpStatus.OK).body("Plat supprimé");
  }

  @PutMapping("/editplat/{id}")
  public ResponseEntity editplat(@PathVariable Long id,@RequestBody Plat plt){

  Plat plat =  platRepository.findById(id).get();
  plat.setNom(plt.getNom());
  plat.setPrix(plt.getPrix());
  plat.setImage(plt.getImage());
  platRepository.save(plat);
    return ResponseEntity.status(HttpStatus.OK).body("Plat modifier");

  }

    @PostMapping("/ajoutermenu/{id}")
  public ResponseEntity ajoutermenu(@RequestBody Menu menu,@PathVariable Long id){
menuRepository.save(menu);

Shop shopwithmen = shopRepository.findById(id).get();
shopwithmen.getMenus().add(menu);
    shopRepository.save(shopwithmen);
    return ResponseEntity.status(HttpStatus.OK).body("Menu ajoutée");

  }
  @GetMapping("/findbyshopname/{name}")
  public ResponseEntity findbyshopmenu(@PathVariable("name") String name)
  {
    return new ResponseEntity(chefRepository.findByshopname(name), HttpStatus.OK);
  }
}
