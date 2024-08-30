package com.example.demo.repositories;


import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.Projections.CustomAttendee;
import com.example.demo.model.Attendee;

@RepositoryRestResource(collectionResourceRel = "attendees", path = "attendees", excerptProjection = CustomAttendee.class)
public interface AttendeeRepository extends PagingAndSortingRepository<Attendee, Long>, QuerydslPredicateExecutor<Attendee> {
    
}
