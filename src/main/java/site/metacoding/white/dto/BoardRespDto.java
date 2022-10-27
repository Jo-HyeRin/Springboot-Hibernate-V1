package site.metacoding.white.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.User;

public class BoardRespDto {

    @Setter
    @Getter
    public static class BoardSaveRespDto {
        private Long id;
        private String title;
        private String content;
        private UserDto userDto;

        @Setter
        @Getter
        public static class UserDto {
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

    @Setter
    @Getter
    public static class BoardDetailRespDto {
        private Long id;
        private String title;
        private String content;
        private BoardUserDto userDto;
        private List<CommentDto> commentList = new ArrayList<>();

        @Setter
        @Getter
        public static class BoardUserDto {
            private Long id;
            private String username;

            public BoardUserDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

        @Setter
        @Getter
        public static class CommentDto {
            private Long id;
            private String comtent;
            private CommentUserDto userDto;

            public CommentDto(Comment comment) {
                this.id = comment.getId();
                this.comtent = comment.getContent();
                this.userDto = new CommentUserDto(comment.getUser());
            }
        }

        @Setter
        @Getter
        public static class CommentUserDto {
            private Long id;
            private String username;

            public CommentUserDto(User user) {
                this.id = user.getId(); // Lazy
                this.username = user.getUsername(); // Lazy
            }
        }

        public BoardDetailRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userDto = new BoardUserDto(board.getUser());

            // List<CommentDto> <---List<Comment>
            this.commentList = board.getCommentList().stream()
                    .map((comment) -> new CommentDto(comment))
                    .collect(Collectors.toList());
        }
    }

    @Setter
    @Getter
    public static class BoardAllRespDto {
        private Long id;
        private String title;
        private UserDto userDto;

        @Setter
        @Getter
        public static class UserDto {
            private Long id;
            private String username;

            public UserDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

        public BoardAllRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.userDto = new UserDto(board.getUser());
        }

    }

    @Setter
    @Getter
    public static class BoardUpdateRespDto {
        private Long id;
        private String title;
        private String content;
        private UserDto userDto;

        @Setter
        @Getter
        public static class UserDto {
            private Long id;

            public UserDto(User user) {
                this.id = user.getId();
            }
        }

        public BoardUpdateRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userDto = new UserDto(board.getUser());
        }

    }

}
