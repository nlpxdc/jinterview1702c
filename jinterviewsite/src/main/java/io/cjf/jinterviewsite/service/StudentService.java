package io.cjf.jinterviewsite.service;

import io.cjf.jinterviewsite.po.Student;

public interface StudentService {
    Student getByOpenid(String openid);
}
