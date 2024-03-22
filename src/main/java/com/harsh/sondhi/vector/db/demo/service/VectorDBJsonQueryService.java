package com.harsh.sondhi.vector.db.demo.service;


//import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.EmptyJsonMetadataGenerator;
import org.springframework.ai.reader.JsonMetadataGenerator;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VectorDBJsonQueryService {

    @Autowired
    VectorStore vectorStore;

    @Value("classpath:/data/bikes.json")
    Resource resource;

    public List<Document> queryJSONSimpleVectorDBAll(String query){

        JsonReader jsonReader = new JsonReader(resource, "name","shortDescription", "description", "price","tags");
        List<Document> documents = jsonReader.get();
        vectorStore.add(documents);

        List<Document> result = vectorStore.similaritySearch(
                SearchRequest.defaults()
                        .withQuery(query)
                        .withTopK(1)
        );
        return result;
    }

    public List<Document> queryJSONSimpleVectorDBWithMatadata(String query){

        JsonReader jsonReader = new JsonReader(resource, new ProductionMetadataGenerator(),"name","shortDescription", "description", "price","tags");
        List<Document> documents = jsonReader.get();
        vectorStore.add(documents);

        List<Document> result = vectorStore.similaritySearch(
                SearchRequest.defaults()
                        .withQuery(query)
                        .withTopK(1)
        );
        return result;
    }


    public class ProductionMetadataGenerator implements JsonMetadataGenerator{

        @Override
        public Map<String, Object> generate(Map<String, Object> jsonMap) {
            return Map.of("name", jsonMap.get("name"),
                         "shortDescription", jsonMap.get("shortDescription"));
        }
    }

}
