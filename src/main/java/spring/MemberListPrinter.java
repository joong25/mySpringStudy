package spring;

import java.util.Collection;

public class MemberListPrinter {
    private MemberDao memberDao;
    private MemberPrinter printer;

    public MemberListPrinter(MemberDao memberDao, MemberPrinter printer){
        this.memberDao = memberDao;
        this.printer = printer;

    }
    public void printAll(){
        Collection<Member> members = memberDao.selectAll();
        // forEach 함수는 for 같은 반복문을 처리할 때 사용하는 함수
        // 람다식을 이용해 forEach사용
        // [ ].forEach(a->printer.print(a)) 식으로 표현하면 된다
        members.forEach(m->printer.print(m));

    }
}
