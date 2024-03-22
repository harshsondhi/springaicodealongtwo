package com.harsh.sondhi.vector.db.demo.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.vectorstore.SimpleVectorStore;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRAGVectorStore {
    SimpleVectorStore simpleVectorStore;
    String description;
}
