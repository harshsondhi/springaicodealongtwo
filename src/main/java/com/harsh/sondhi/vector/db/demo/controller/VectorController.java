package com.harsh.sondhi.vector.db.demo.controller;

import com.harsh.sondhi.vector.db.demo.record.Answer;
import com.harsh.sondhi.vector.db.demo.record.Question;
import com.harsh.sondhi.vector.db.demo.service.LLMService;
import com.harsh.sondhi.vector.db.demo.service.VectorDBJsonQueryService;
import com.harsh.sondhi.vector.db.demo.service.VectotDBQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vi")
public class VectorController {

    @Autowired
    VectotDBQueryService vectotDBQueryService;

    @Autowired
    VectorDBJsonQueryService vectorDBJsonQueryService;

    @Autowired
    LLMService llmService;


    @GetMapping("/search/simplevector/db")
    public String genVectorSearchContent(@RequestParam String query){
        return vectotDBQueryService.simpleVector(query).get(0).getContent().toString();
    }

    //EXAMPLE: http://localhost:8080/api/vi/search/simplevectorjson/all/db?query=i am looking for e-MTB which is good for me
    @GetMapping("/search/simplevectorjson/all/db")
    public String genVectorSearchJSONContent(@RequestParam String query){
        return vectorDBJsonQueryService.queryJSONSimpleVectorDBAll(query).get(0).getContent().toString();
    }

    @GetMapping("/search/simplevectorjson/matdata/db")
    public String genVectorSearchJSONMatdatContent(@RequestParam String query){
        return vectorDBJsonQueryService.queryJSONSimpleVectorDBWithMatadata(query).get(0).getMetadata().toString();
    }

//    EXAMPLE:
//    http://localhost:8080/api/vi/query
//
//    {
//        "question": "how to use restcontroller"
//    }
    @PostMapping("/query")
    public Answer askQuestionToOenAI(@RequestBody Question question){
        return llmService.ask(question);
    }
}
