package spring;

public class ChangePasswordService {
    private MemberDao memberDao;

    public void changePassword(String email, String oldPwd, String newPwd){
        Member member = memberDao.selectByEmail(email);
        // email에 해당하는 Member가 존재하지 않으면 익셉션 발생
        if (member == null){
            throw new MemberNotFoundException();
        }
        // member 클래스의 changePassword() 메서드는 다음 코드처럼
        // oldPassword로 전달한 암호가 일치하지 않으면 익셉셔을 발생시키도록 구현했으므로
        // 암호가 일치하지 않으면 17행 코드 발생 X
        member.changePassword(oldPwd, newPwd);

        memberDao.update(member);
    }
    // setMemberDao로 의존하는 MemberDao를 전달받는다. 세터를 통해서 의존 객체를 주입한다.
    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }
}
