package com.web.freesite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("/main")
public class mainController {

    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "str", description = "2번 반복할 문자열")
    @GetMapping("/test")
    public String test(){
        return "TEST";
    }
}
