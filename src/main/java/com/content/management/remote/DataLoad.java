package com.content.management.remote;

import com.content.management.repository.ContentRepository;
import com.content.management.repository.LanguageRepository;
import com.content.management.repository.PlatformRepository;
import com.content.management.repository.TypeRepository;
import com.content.management.repository.entities.Content;
import com.content.management.repository.entities.Language;
import com.content.management.repository.entities.Platform;
import com.content.management.repository.entities.ContentType;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class DataLoad {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ContentRepository contentRepository;
    private final LanguageRepository languageRepository;
    private final TypeRepository typeRepository;
    private final PlatformRepository platformRepository;

    public DataLoad(ContentRepository contentRepository, LanguageRepository languageRepository, TypeRepository typeRepository, PlatformRepository platformRepository) {
        this.contentRepository = contentRepository;
        this.languageRepository = languageRepository;
        this.typeRepository = typeRepository;
        this.platformRepository = platformRepository;
    }

    public void getData() {
        String url = "https://run.mocky.io/v3/496e5e30-bbde-4172-846c-86c82b9c76de";
        ResponseEntity<JsonNode> response
                = restTemplate.getForEntity(url , JsonNode.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode jsonNodeArray = response.getBody().get("data").get("Disclaimers").get("Disclaimers");
            if (jsonNodeArray.isArray()) {
                for (JsonNode obj : jsonNodeArray) {
                    JsonNode platformArray = obj.get("platforms");

                    Content content = new Content();
                    content.setData(obj.toString());
                    content.setLang(obj.get("lang").asText());

                    //save platform
                    for(JsonNode platformObj: platformArray)
                    {
                        Platform platform = new Platform();
                        content.setType("Disclaimer");
                        platform.setContent(content);
                        platform.setName(platformObj.asText());
                        platformRepository.save(platform);
                    }
                    //save language
                    Language language = new Language();
                    language.setContent(content);
                    language.setName(obj.get("lang").asText());
                    languageRepository.save(language);

                    //saving type
                    ContentType contentType = new ContentType();
                    contentType.setContent(content);
                    contentType.setName("Disclaimer");

                    typeRepository.save(contentType);
                }
            }
        }
    }
}
