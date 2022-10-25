package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {
        // boardRepository.save(board);
        // 위에서 파라미터를 boardSaveDto 로 Persist할 수는 없다. 엔티티로 받아야지. 그래서 아래처럼 사용.

        // 핵심 로직
        User userPS = userRepository.findById(boardSaveReqDto.getSessionUser().getId());
        Board board = new Board();
        board.setTitle(boardSaveReqDto.getTitle());
        board.setContent(boardSaveReqDto.getContent());
        board.setUser(userPS);
        // userId가 아니라 User 객체를 넣으면 되는데 어떤 것을 넣어도 상관없지만
        // 영속화 된 객체를 넣는 것이 가장 안전하다.(DB에서 조회해서 들고오기)

        Board boardPS = boardRepository.save(board);

        // DTO 전환
        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);
        return boardSaveRespDto;
    }

    @Transactional(readOnly = true)
    public Board findById(Long id) {
        Board boardPS = boardRepository.findById(id); // 오픈 인뷰가 false니까 조회후 세션 종료
        boardPS.getUser().getUsername(); // Lazy 로딩됨. (근데 Eager이면 이미 로딩되서 select 두번
        // 4. user select 됨?
        System.out.println("서비스단에서 지연로딩 함. 왜? 여기까지는 디비커넥션이 유지되니까");
        return boardPS;
    }

    @Transactional
    public void update(Long id, Board board) {
        Board boardPS = boardRepository.findById(id);
        boardPS.setTitle(board.getTitle());
        boardPS.setContent(board.getContent());
    } // 트랜잭션 종료시 -> 더티체킹을 함

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

}
