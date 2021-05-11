package com.content.management.api;

import com.content.management.remote.DataLoad;
import com.content.management.repository.ContentRepository;
import com.content.management.repository.DataRepository;
import com.content.management.repository.entities.Content;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ContentController {

    private ContentRepository contentRepository;
    private DataRepository dataRepository;
    private DataLoad dataLoad;

    public ContentController(ContentRepository contentRepository, DataRepository dataRepository, DataLoad dataLoad) {
        this.contentRepository = contentRepository;
        this.dataRepository = dataRepository;
        this.dataLoad = dataLoad;
    }

    @PostMapping(path = "/getContent")
    public Collection<Content> getContent(@RequestBody  DataModel dataModel)
    {

        return dataRepository.getContent(dataModel);
    }

    @GetMapping(path = "/addContent")
    public void addData()
    {
        dataLoad.getData();
    }
}
