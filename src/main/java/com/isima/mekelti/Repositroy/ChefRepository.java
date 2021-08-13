package com.isima.mekelti.Repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isima.mekelti.entity.Chef;
import com.isima.mekelti.entity.Utilisateur;

@Repository
public interface ChefRepository extends CrudRepository<Chef,Long> {

  @Query(nativeQuery = true,
      value = "select * from utilisateur c,shop s where c.shop_id = s.id and s.nom LIKE %?1%")
  List<Chef> findByshopname(String name);

}

