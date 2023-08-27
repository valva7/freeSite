package com.web.freesite.controller;

import com.web.freesite.service.MemberService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.*;

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
  *   1 2023-08-26       김태욱(메가존)     최초작성
 </PRE>
 */

@OpenAPIDefinition(
        info = @Info(
                title = "로그인 API",
                version = "1.0",
                description = "로그인 API"
        )
)
@Tag(name = "로그인 API", description = "로그인 API Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class loginAllController {

    private final MemberService memberService;

    @Operation(summary = "기본 제공 로그인", description = "기본 제공 로그인 API")
    @PostMapping("/default/login")
    public String defaultLogin(){
        return "login Test";
    }

    @Operation(summary = "KAKAO 로그인", description = "KAKAO 로그인 API")
    @PostMapping("/auth/kakao/token")
    public String kakaoLogin(
            @RequestParam(value = "code") @Schema(description = "Code Value", example = "NaN") String code
    ) throws IOException, ParseException {

        // 사용자 정보 확인 Token 요청
        String[] arrTokens = memberService.getToken(code);

        // Token으로 사용자 정보 저장 or 조회
        String createdJwtToken = memberService.getUserInfo(arrTokens[0]);

        return createdJwtToken;
    }

}


