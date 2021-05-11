package com.content.management.repository;

import com.content.management.repository.entities.Content;
import com.content.management.repository.entities.Platform;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PlatformRepository extends Neo4jRepository<Platform, Long> {
}
