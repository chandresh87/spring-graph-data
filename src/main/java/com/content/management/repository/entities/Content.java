package com.content.management.repository.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = JSOGGenerator.class)
@Node
public class Content {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String lang;
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
