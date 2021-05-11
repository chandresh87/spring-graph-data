package com.content.management.repository;

import com.content.management.api.DataModel;
import com.content.management.repository.entities.Content;
import org.neo4j.driver.internal.shaded.io.netty.util.internal.StringUtil;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class DataRepository {

    private final Neo4jClient neo4jClient;

    public DataRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<Content> getContent(DataModel dataModel) {
        StringBuilder query = new StringBuilder("Match ");
        if (!StringUtil.isNullOrEmpty(dataModel.getDataType())) {
            query.append("(t:ContentType)-[:HAVING] ->(c:Content)");
        }

        if (!StringUtil.isNullOrEmpty(dataModel.getLang())) {
            query.append(", (l:Language) -[:HAVING] ->(c:Content) ");
        }

        if (!StringUtil.isNullOrEmpty(dataModel.getPlatform())) {
            query.append(", (p:Platform) -[:HAVING] ->(c:Content) ");
        }

        if (!StringUtil.isNullOrEmpty(dataModel.getDataType())) {
            query.append(" where t.name=");
            query.append("\"");
            query.append(dataModel.getDataType());
            query.append("\"");
        }

        if (!StringUtil.isNullOrEmpty(dataModel.getLang())) {
            query.append(" And l.name=");
            query.append("\"");
            query.append(dataModel.getLang());
            query.append("\"");
        }

        if (!StringUtil.isNullOrEmpty(dataModel.getPlatform())) {
            query.append(" And p.name=");
            query.append("\"");
            query.append(dataModel.getPlatform());
            query.append("\"");
        }
        query.append("  return DISTINCT c as contentData;");
        List<Content> contentList = new ArrayList<>();

        Collection<Content> contentData1 = neo4jClient.query(query.toString()).fetchAs(Content.class).mappedBy((typeSystem, record) -> {
            Content content = new Content();
            content.setData(record.get("contentData").asNode().get("data").asString());
            content.setLang(record.get("contentData").asNode().get("lang").asString());
            content.setType(record.get("contentData").asNode().get("type").asString());

            return content;
        }).all();

        return contentData1;

    }
}
