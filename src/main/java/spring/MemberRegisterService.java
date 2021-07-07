package spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
    private MemberDao memberDao; // MemberDao의 객체 생성

    // MemberDao 생성자
    public MemberRegisterService(MemberDao memberDao) { 
        this.memberDao = memberDao;
    }

    // 이 클래스의 regist 메서드에서 앞서 작성한 memberDao, selectByEmail 메서드를 이용해 
    // 동일한 이메일을 갖는 회원 데이터가 존재하는 지확인한다. 
    // DuplicateMemberException을 발생
    public Long regist(RegisterRequest req){
        Member member = memberDao.selectByEmail(req.getEmail());
        if (member != null){
            throw new DuplicateMemberException("dup email"+req.getEmail());
        }
        Member newMember= new Member(
                req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now()
        );
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
