package com.cero.cm.biz.v1.authenticated.test;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"TEST"})
public class TestController {

    @Operation(
            summary = "TEST-JWT",
            description = "TEST"
    )
    @RequestMapping(value = "/test")
    public void test() {
        System.out.println("TEST");
    }
}
