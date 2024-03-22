package com.harsh.sondhi.vector.db.demo.service;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VectotDBQueryService {

    @Autowired
    VectorStore vectorStore;

    public List<Document> simpleVector(String query){
        List<Document> documents = new ArrayList<>();
        documents = List.of(
                new Document("To be, or not to be, that is the question: Whether 'tis nobler in the mind to suffer The slings and arrows of outrageous fortune, Or to take arms against a sea of troubles And by opposing end them.", Map.of("author", "William Shakespeare")),
                new Document("In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit down on or to eat: it was a hobbit-hole, and that means comfort.", Map.of("author", "J.R.R. Tolkien")),
                new Document("The Hitchhiker's Guide to the Galaxy has a few things to say on the subject of towels.", Map.of("author", "Douglas Adams"))
                );
        vectorStore.add(documents);
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        List<Document> reults = vectorStore.similaritySearch(
                SearchRequest.defaults()
                        .withQuery(query)
                        .withTopK(2)
                        .withSimilarityThreshold(.65)
        );

//                List<Document> reults = vectorStore.similaritySearch(
//                SearchRequest.defaults()
//                        .withQuery(query)
//                        .withFilterExpression(b.eq("author", "William Shakespeare").build())

//        );


        return reults;
    }


}
