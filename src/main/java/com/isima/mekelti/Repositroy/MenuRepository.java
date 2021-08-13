package com.isima.mekelti.Repositroy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isima.mekelti.entity.Menu;
@Repository
public interface MenuRepository extends CrudRepository<Menu,Long> {

}
