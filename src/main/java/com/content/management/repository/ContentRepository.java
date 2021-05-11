package com.content.management.repository;

import com.content.management.repository.entities.Content;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ContentRepository extends Neo4jRepository<Content, Long> {
}
