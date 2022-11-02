package site.metacoding.white.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.CommentRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.util.SHA256;

@Slf4j
@ActiveProfiles("test")
// @Transactional // 통합테스트에서 RANDOM_PORT를 사용하면 새로운 스레드로 돌기때문에 rollback 무의미
@Sql("classpath:truncate.sql") // classpath : 자바 classpath 공부하기 . 스프링은 기본적으로 resource 폴더 내에 설정되어있다.
@Transactional // 트랜잭션 안붙이면 영속성 컨텍스트에서 DB로 flush 안됨 (Hibernate 사용시)
@AutoConfigureMockMvc // MockMvc Ioc 컨테이너에 등록
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // 가짜 환경으로 실행
public class UserApiControllerTest {

    private static final String APPLICATION_JSON = "application/json; charset=utf-8";

    @Autowired // : DI 코드
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om; // 잭슨이 가지고 있는 라이브러리 : JSON변환

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SHA256 sha256;

    private MockHttpSession session;

    @BeforeEach
    public void sessionInit() {
        session = new MockHttpSession();
        User user = User.builder().id(1L).username("ssar").build();
        session.setAttribute("sessionUser", new SessionUser(user));
    }

    @BeforeEach
    public void dataInit() {
        String encPassword = sha256.encrypt("1234");
        User user = User.builder().username("ssar").password(encPassword).build();
        User userPS = userRepository.save(user);

        Board board = Board.builder()
                .title("스프링1강")
                .content("트랜잭션관리")
                .user(userPS)
                .build();
        Board boardPS = boardRepository.save(board);

        Comment comment1 = Comment.builder()
                .content("내용좋아요")
                .board(boardPS)
                .user(userPS)
                .build();

        Comment comment2 = Comment.builder()
                .content("내용싫어요")
                .board(boardPS)
                .user(userPS)
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
    }

    @Test
    public void join_test() throws Exception {
        // given : JoinReqDto가 필요하네
        JoinReqDto joinReqDto = new JoinReqDto(); // 해당 dto에 getter setter 만 설정되어있네 setter 이용해주자
        joinReqDto.setUsername("hoho");
        joinReqDto.setPassword("1234");

        String body = om.writeValueAsString(joinReqDto);
        System.out.println(body);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/join").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
        // 요청주소, 메서드(POST 로 작성해도 되지만 실수하지않기위해 !), 헤더와 바디데이터, 응답타입

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1L)); // 코드가 1이면 성공이니까 code를 확인하면 된다.

    }

    @Test
    public void login_test() throws Exception {
        // given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("ssar");
        loginReqDto.setPassword("1234");
        String body = om.writeValueAsString(loginReqDto);

        log.debug("디버그 : " + body);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/login").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1L));
    }

}
