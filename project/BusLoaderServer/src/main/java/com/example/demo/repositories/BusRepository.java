package com.example.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.model.Bus;

@RepositoryRestResource(collectionResourceRel = "busses", path = "busses")
public interface BusRepository extends PagingAndSortingRepository<Bus, Long> {
    //List<WebsiteUser> findByName(@Param("name") String name);
}
