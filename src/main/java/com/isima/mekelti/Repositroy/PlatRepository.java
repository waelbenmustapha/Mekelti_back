package com.isima.mekelti.Repositroy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isima.mekelti.entity.Plat;
@Repository
public interface PlatRepository extends CrudRepository<Plat,Long> {

}
