package com.isima.mekelti.Repositroy;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isima.mekelti.entity.Shop;

@Repository
public interface ShopRepository extends CrudRepository<Shop,Long> {

}
