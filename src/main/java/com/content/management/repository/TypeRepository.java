package com.content.management.repository;

import com.content.management.repository.entities.ContentType;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TypeRepository extends Neo4jRepository<ContentType, Long> {
}
