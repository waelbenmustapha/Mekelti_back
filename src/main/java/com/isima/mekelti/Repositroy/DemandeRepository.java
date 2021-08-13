package com.isima.mekelti.Repositroy;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isima.mekelti.entity.Demande;

@Repository
public interface DemandeRepository extends CrudRepository<Demande,Long> {
  List<Demande> findByChefId(Long id);
  List<Demande> findByUtilisateurId(Long id);
}
