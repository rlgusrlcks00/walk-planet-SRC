package com.cero.cm.biz.v1.unauthenticated.test;

import com.cero.cm.db.mapper.test.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Test2Service {

    private final TestMapper testMapper;

    public TestResponse getTestResponse() {
        return testMapper.findTestResponse();
    }
}
