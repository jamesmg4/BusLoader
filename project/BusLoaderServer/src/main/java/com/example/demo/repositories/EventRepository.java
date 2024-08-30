package com.example.demo.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.Projections.CustomEvent;
import com.example.demo.model.Event;
@RepositoryRestResource(collectionResourceRel = "events", path = "events", excerptProjection = CustomEvent.class)
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
    //List<WebsiteUser> findByName(@Param("name") String name);
}
