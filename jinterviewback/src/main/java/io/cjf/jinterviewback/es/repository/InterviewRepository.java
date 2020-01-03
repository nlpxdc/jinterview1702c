package io.cjf.jinterviewback.es.repository;

import io.cjf.jinterviewback.es.document.InterviewDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface InterviewRepository extends ElasticsearchRepository<InterviewDocument, Integer> {

    List<InterviewDocument> findByCompanyOrStudentNameOrNote(String keyword);

}
