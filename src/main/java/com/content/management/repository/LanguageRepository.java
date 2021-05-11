package com.content.management.repository;

import com.content.management.repository.entities.Content;
import com.content.management.repository.entities.Language;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LanguageRepository extends Neo4jRepository<Language, Long> {
}
