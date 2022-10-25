package site.metacoding.white.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;

public class BoardRespDto {

    @NoArgsConstructor
    @Setter
    @Getter
    public static class BoardSaveRespDto {
        private Long id;
        private String title;
        private String content;
        private UserDto userDto;
        // private User user; - 여기도 Entity를 사용할 수 없기 때문에 UserDto를 사용.
        // 여기서 이 UserDto는 외부파일생성, 외부클래스추가 등 다양한 방법으로 이용할 수 있지만
        // 나는 내부클래스를 사용하려고 한다.

        @NoArgsConstructor
        @Setter
        @Getter
        public static class UserDto {
            // innerclass
            // public, static 없으면 외부에서 접근이 안된다(=new 할 수 없다)
            // BoardSaveRespDto에서만 사용할 수 있는 DTO!
            private Long id;
            private String username;

            public UserDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

        public BoardSaveRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userDto = new UserDto(board.getUser());
        }

    }
}
