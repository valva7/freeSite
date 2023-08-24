package com.web.freesite.controller;

import org.springframework.stereotype.Controller;
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
@RestController
@RequestMapping("/main")
public class mainController {

    @GetMapping("/test")
    public String test(){
        return "TEST";
    }
}
