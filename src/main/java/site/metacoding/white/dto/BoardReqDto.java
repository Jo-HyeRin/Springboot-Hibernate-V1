package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class BoardReqDto {

    @Setter
    @Getter
    public static class BoardSaveDto {
        private String title;
        private String content;
        private User user; // 클라이언트에게 받는 데이터 아님. 서비스 로직.
    }

    // DTO는 여기다 계속 추가하면 된다.
}
