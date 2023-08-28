package com.web.freesite.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
class 한글 설명(요약)
<PRE>
한글 설명(상세)
</PRE>
@author   : 김태욱(메가존)(megazone.tukim@seegene.com)
@History
<PRE>
* No  Date           Author            Desc
*---- ------------ ---------------- ------------------------------------
*   1 2023-08-24       김태욱(메가존)     최초작성
</PRE>
*/
@OpenAPIDefinition(
        info = @Info(
                title = "swagger test API",
                version = "1.0",
                description = "This is a sample API"
        )
)
@Tag(name = "swagger test API", description = "swagger test API Controller")
@Slf4j
@RestController
@RequestMapping("/demo")
public class demoController {

    @Operation(summary = "swagger test", description = "swagger test")
    @PostMapping("/test")
    public String test(){
        return "Test";
    }

}
