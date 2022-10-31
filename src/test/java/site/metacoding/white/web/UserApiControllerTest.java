package site.metacoding.white.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.service.UserService;

@ActiveProfiles("test")
// @Transactional // 통합테스트에서 RANDOM_PORT를 사용하면 새로운 스레드로 돌기때문에 rollback 무의미
@Sql("classpath:truncate.sql") // classpath : 자바 classpath 공부하기 . 스프링은 기본적으로 resource 폴더 내에 설정되어있다.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // 롤백이 안된다.
public class UserApiControllerTest {

    @Autowired
    private TestRestTemplate rt; // postman으로 요청하는 것처럼 버퍼로 직접 요청을 할 수 있게 해준다. 통신에 이용하는 라이브러리.
    @Autowired
    private ObjectMapper om; // 잭슨이 가지고 있는 라이브러리 : JSON변환
    @Autowired // : DI 해주는 코드
    private UserService userService;

    private static HttpHeaders headers;

    @BeforeAll
    public static void init() { // 테스트할때마다 매번 뉴 할 수 없으니까 모든 테스트 실행 전에 한번만 뜨면 된다.
        // @BeforeAll 은 항상 static으로 만들어야 한다.
        headers = new HttpHeaders(); // http 요청 header에 필요
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void join_test() throws JsonProcessingException { // 회원가입 테스트
        // given : JoinReqDto가 필요하네
        JoinReqDto joinReqDto = new JoinReqDto(); // 해당 dto에 getter setter 만 설정되어있네 setter 이용해주자
        joinReqDto.setUsername("hoho");
        joinReqDto.setPassword("1234");

        String body = om.writeValueAsString(joinReqDto);
        System.out.println("디버그 : " + body);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/join", HttpMethod.POST, request, String.class);
        // 요청주소, 메서드(POST 로 작성해도 되지만 실수하지않기위해 !), 헤더와 바디데이터, 응답타입

        // then
        // System.out.println(response.getStatusCode());
        // System.out.println(response.getBody());

        // response는 JSON 즉 String이다. 다시 파싱을 위해서 객체로 다시 바꿔준다.
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code"); // $는 최상위를 뜻한다.
        Assertions.assertThat(code).isEqualTo(1); // 코드가 1이면 성공이니까 code를 확인하면 된다.
    }

    @Test
    public void login_test() throws JsonProcessingException { // 로그인 테스트
        // data init : DB에 회원 정보가 있어야 한다. insert 해보자.
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("hoho");
        joinReqDto.setPassword("1234");
        userService.save(joinReqDto);

        // given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("hoho");
        loginReqDto.setPassword("1234");
        String body = om.writeValueAsString(loginReqDto);
        System.out.println("디버그 : " + body);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/login", HttpMethod.POST, request, String.class);
        System.out.println("디버그 : " + response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String username = dc.read("$.data.username"); // jayway Jsonpath 라이브러리 문법
        Assertions.assertThat(code).isEqualTo(1);
        Assertions.assertThat(username).isEqualTo("hoho");
    }
}
