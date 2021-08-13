package com.isima.mekelti.Repositroy;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isima.mekelti.entity.Chef;
import com.isima.mekelti.entity.Utilisateur;
@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long> {

  Optional<Utilisateur> findByEmail(String email);
  Utilisateur findByToken(String token);
  @Modifying
  @Transactional
  @Query(nativeQuery = true,
      value = "insert into mekelti.utilisateur_likes values(?1,?2);")
  void addfavoris(Long iduser,Long idchef);
  Utilisateur findByVerificationCode(String verificationCode);
}
