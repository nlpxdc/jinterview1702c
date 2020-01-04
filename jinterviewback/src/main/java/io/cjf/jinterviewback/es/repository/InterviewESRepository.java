package io.cjf.jinterviewback.es.repository;

import io.cjf.jinterviewback.es.document.InterviewESDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface InterviewESRepository extends ElasticsearchRepository<InterviewESDocument, Integer> {

    List<InterviewESDocument> findByCompanyOrStudentNameOrNote(String keyword);

    List<InterviewESDocument> findByCompany(String keyword);

}
