package site.metacoding.white.dto;

import org.junit.jupiter.api.Test;

import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto.UserDto;

public class BoardSaveRespDtoTest {

    @Test
    public void innerclass_test() {
        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto();
        boardSaveRespDto.setId(1L);
        boardSaveRespDto.setTitle("제목1");
        boardSaveRespDto.setContent("내용1");
        boardSaveRespDto.setUserDto(null);

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("ssar");

        boardSaveRespDto.setUserDto(userDto);
    }
}
