package spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {
    private static long nextid = 0;
    // Map은 List와 마찬가지로 인터페이스
    // Map 인터페이스를 구현한 Map 자료형에는 대표적으로 HashMap
    // key와 value를 한 쌍으로 가진다.
    private Map<String, Member> map = new HashMap<>();

    public Member selectByEmail(String email){
        return map.get(email);
    }

    public void insert(Member member){
        member.setId(++nextid);
        map.put(member.getEmail(),member);
    }
    public void update(Member member){
        map.put(member.getEmail(), member);
    }

    public Collection<Member> selectAll() {
        return map.values();
    }
}
