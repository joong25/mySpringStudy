package main;

import assembler.Assembler;
import config.AppCtx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForSpring {

    private static ApplicationContext ctx = null;

    public static void main(String[] args) throws IOException {
        // AnnotationConfigApplicationContext를 사용해서 스프링 컨테이너를 생성한다.
        // 스프링 컨테이너는 Assembler와 동일하게 객체를 생성하고 의존 객체를 주입한다.

        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        // 콘솔에서 입력받기 위해 System.in 을 이용하여 BufferedReader를 생성한다.
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.println("명령어를 입력하세요:");
            String command = reader.readLine();
            if(command.equalsIgnoreCase("exit")){
                System.out.println("종료합니다.");
                break;
            }
            // 입력한 문자열이 new로 시작하면 processNewCommand 메서드를 실행한다.
            if(command.startsWith("new ")){
                processNewCommand(command.split(" "));
                continue;
            }
            // 입력한 문자열이 change로 시작하면 processChangeCommand메서드를 실행한다.
            else if(command.startsWith("change ")){
                processChangeCommand(command.split(" "));
                continue;
            }
            else if(command.equals("list")){
                processListCommand();

            }
            else if(command.startsWith("info")){
                processInfoCommand(command.split(" "));
                continue;
            }
            else if(command.equals("version")){
                processVersionCommand();
                continue;
            }
            //명령어 잘못 입력할 경우 도움말 출력하는 printHelp 메서드 실행
            printHelp();
        }

    }

    // 새로운 회원 정보를 생성

    // 스프링 컨테이너로부터 이름이 "memberRegSvc"인 빈 객체를 구한다.
    private static void processNewCommand(String[] arg){
        if(arg.length !=5){
            printHelp();
            return;
        }
        MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);

        // 입력한 암호값이 올바르지 않거나
        if(!req.isPasswordEqualToConfirmPassword()){
            System.out.println("암호와 확인이 일치하지 않습니다.\n");
            return;
        }
        try{
            regSvc.regist(req); // 이 행이 실행되면 새로운 회원 데이터가 보관된다.
            // 어셈블러 클래스는 MemberRegisterService 객체를 생성할 때 MemberDao 객체를 주입했다.
            System.out.println("등록했습니다.\n");
        } catch (DuplicateMemberException e){
            System.out.println("이미 존재하는 이메일입니다.\n"); // 이미 동일한 이메일이 존재하면 에러 메시지 출력
        }
    }
    // 결론적으로 ProcessNewCommand 메서드가 실행되면 MemberDao객체의 Map 타입 필드인 map에 회원 데이터가 추가된다.

    private static void processChangeCommand(String[] arg){
        if (arg.length !=4){
            printHelp();
            return;
        }
        // 스프링 컨테이너로부터 이름이 "changePwdSvc"인 빈 객체를 구한다.
        ChangePasswordService changePwdSvc =
                ctx.getBean("changePwdSvc", ChangePasswordService.class);
        try{
            // 변경에 성공하면 MemberDao의 map에 보관된 회원 데이터의 암호가 변경된다
            changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("암호를 변경했습니다.\n");
        } catch(WrongIdPasswordException e){
            System.out.println("이메일과 암호가 일치하지 않습니다.\n");
        }
    }

    private static void processListCommand(){
        MemberListPrinter listPrinter =
                ctx.getBean("listPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }
    private static void processInfoCommand(String[] arg){
        if(arg.length != 2){
            printHelp();
            return;
        }
        MemberInfoPrinter infoPrinter=
                ctx.getBean("infoPrinter", MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
    }
    private static void processVersionCommand(){
        VersionPrinter versionPrinter =
                ctx.getBean("versionPrinter",VersionPrinter.class);//getBean("빈의 이름", 빈의 타입);
        versionPrinter.print();
    }
    private static void printHelp(){
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법:");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }
}