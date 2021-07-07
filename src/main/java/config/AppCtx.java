package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.*;

// @Configuration은 스프링 설정 클래스를 의미 
@Configuration
public class AppCtx {

    // 생성한 객체를 스프링 빈이라고 설정
    // 세 개의 메서드에 Bean 어노테이션을 붙였는데 각각의 메서드마다 한 개의 빈 객체 생성
    // memberDao() 메서드로 생성한 빈 객체는 memberDao라는 이름으로 스프링에 등록
    @Bean 
    public MemberDao memberDao(){
        return new MemberDao();
    }

    @Bean
    public MemberRegisterService memberRegSvc(){
        return new MemberRegisterService(memberDao());
    }

    // ChangePasswordService 타입의 빈을 설정하여 메서드는 세터를 이용해서 의존 객체 주입
    @Bean
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao()); // 세터를 사용
        return pwdSvc;
    }

    // memberPrinter 타입의 빈을 설정
    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter(memberDao(), memberPrinter());
    }

    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDao());
        infoPrinter.setPrinter(memberPrinter());
        return infoPrinter;
    }

    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
