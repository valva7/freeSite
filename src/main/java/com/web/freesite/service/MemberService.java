package com.web.freesite.service;

import com.web.freesite.config.JwtTokenProvider;
import com.web.freesite.domain.Member;
import com.web.freesite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${kakao.req-url}")
    private String kakaoAccessURL;

    @Value("${kakao.apikey}")
    private String kakaoApikey;

    @Value("${kakao.redirect-url}")
    private String kakaoRedirectURL;

    @Value("${kakao.getUser-url}")
    private String kakaoGetUserURL;

    @Value("${kakao.secret}")
    private String kakaoSecret;

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public String[] getToken(String code){

        String accessToken = "";
        String refreshToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type"   , "authorization_code");
            params.add("client_id"    , kakaoApikey);
            params.add("client_secret", kakaoSecret);
            params.add("redirect_uri" , kakaoRedirectURL);
            params.add("code"         , code);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    kakaoAccessURL,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
            log.info(response.getBody().toString());

            accessToken  = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] arrTokens = new String[3];
        arrTokens[0] = accessToken;
        arrTokens[1] = refreshToken;

        return arrTokens;
    }

    @Transactional
    public String[] getUserInfo(String accessToken) throws IOException, ParseException {

        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                kakaoGetUserURL,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");
        log.info(response.getBody().toString());

        long id = (long) jsonObj.get("id");
        boolean hasEmail = (boolean) account.get("has_email");
        String email = "";
        String nickname = String.valueOf(profile.get("nickname"));
        String createdJwtToken = "";

        if (hasEmail) {
            email = String.valueOf(account.get("email"));
            createdJwtToken = jwtTokenProvider.generateToken(email);
        }

        Member getMember = memberRepository.getMemberByEmail(email);
        if(getMember == null){
            // TODO : refresh_token 저장 로직 추가
            Member newMember = new Member();
            newMember.setEmail(email);
            newMember.setAccessToken(createdJwtToken);
            memberRepository.save(newMember);
        } else {
            getMember.setAccessToken(createdJwtToken);
            memberRepository.save(getMember);
        }

        // TODO : 사용자 정보를 DTO로 변환해서 반환하는 부분 추가
        String[] accessArr = new String[] {
                    email, createdJwtToken
                };

        return accessArr;
    }


}
