package com.ezen.g15.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.g15.dto.KakaoProfile;
import com.ezen.g15.dto.KakaoProfile.KakaoAccount;
import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.dto.OAuthToken;
import com.ezen.g15.service.MemberService;
import com.google.gson.Gson;

@Controller
public class MemberController {

        @Autowired
        MemberService ms;
        
        @RequestMapping("loginForm")
        public String login_form() {
        return "member/login";
        }
        
        
        
        @RequestMapping(value="/login",method=RequestMethod.POST)
        public String login(
                        @ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result, Model model, HttpServletRequest request) {
                String url = "member/login";
                if(result.getFieldError("id")!=null)
                        model.addAttribute("message",result.getFieldError("id").getDefaultMessage());
                else if(result.getFieldError("pwd")!=null)
                        model.addAttribute("message",result.getFieldError("pwd").getDefaultMessage());
                else {
                        
                        MemberVO mvo= ms.getMember(membervo.getId());
                        if(mvo==null)model.addAttribute("message","ID가 없습니다");
                                else if(mvo.getPwd()==null)model.addAttribute("message","관리자에게 문의하세요");
                                else if(!mvo.getPwd().equals(mvo.getPwd()))model.addAttribute("message","암호가 잘못되었습니다");
                                else if(mvo.getPwd().equals(mvo.getPwd())) {
                                        HttpSession session = request.getSession();
                                        session.setAttribute("loginUser", mvo);
                        
                                url="redirect:/";
                }
        }
                return url;        
        }
        
        @RequestMapping("/logout")
        public String logout(Model model, HttpServletRequest request) {
                HttpSession session = request.getSession();
                session.removeAttribute("loginUser");
                return "redirect:/";
        }
        
        
        @RequestMapping("/kakaostart")
        public @ResponseBody String kakaostart() {
          String a = "<script type='text/javascript'>"
        + "location.href='https://kauth.kakao.com/oauth/authorize?"
        + "client_id=0a12e9117e1fe5a43f4dec0602b709c1&"
        + "redirect_uri=http://localhost:8070/kakaoLogin&"        		        	
        + "response_type=code';"
        + "</script>";
                return a;
        } 
        
        
        @RequestMapping("/kakaoLogin")
        public String login( HttpServletRequest request  ) 
        		throws UnsupportedEncodingException, IOException {
                
        	    // 카카오 아이디, 비번 인증 + 아이디 이메일 제공 동의 후 전송되는 암호화 코드
                String code = request.getParameter("code");               
                // 전송된 암호화 코드를 이용하여 토큰을 생성
                // 주소 url 설정
                String endpoint="https://kauth.kakao.com/oauth/token";
                URL url =new URL(endpoint);  // import java.net.URL;
                // 파라미터 설정
                String bodyData="grant_type=authorization_code&";
                bodyData += "client_id=262e918b5675b24289ca7b6493e959ff&";
                bodyData += "redirect_uri=http://localhost:8070/kakaoLogin&";
                bodyData += "code="+code;
                // Stream 연결 및 토큰 수신
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();   // import java.net.HttpURLConnection;
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                conn.setDoOutput(true);
                // 토큰 수신
                BufferedWriter bw=new BufferedWriter(
                                new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
                );
                bw.write(bodyData);
                bw.flush();
                BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream(), "UTF-8")
                );
                String input="";
                StringBuilder sb=new StringBuilder();  // 조각난 String 을 조립하기 위한 객체
                while((input=br.readLine())!=null){
                                sb.append(input);
                                System.out.println(input); // 수신된 토큰을 콘솔에 출력함.
                }
                
                
                // 위 토큰 애용을 본 따 만든 클래스에 g내ㅜ 파싱으로 옮겨 담슴니다(sb - > OAuthToken.class)           
                Gson gson=new Gson();                
                OAuthToken oAuthToken=gson.fromJson(sb.toString(), OAuthToken.class);
                
                // 토근을 이용해서 사용자 정보를 요청 수신              
                String endpoint2="https://kapi.kakao.com/v2/user/me";
                URL url2 =new URL(endpoint2);
                // import java.net.HttpURLConnection;               
                HttpsURLConnection conn2=(HttpsURLConnection)url2.openConnection();
                conn2.setRequestProperty("Authorization", "Bearer "+oAuthToken.getAccess_token());
                conn2.setDoOutput(true);
                BufferedReader br2=new BufferedReader(
                                new InputStreamReader(conn2.getInputStream(),"UTF-8")
                );
                String input2="";
                StringBuilder sb2=new StringBuilder();
                while((input2=br2.readLine())!=null) {
                        sb2.append(input2);
                        System.out.println(input2);
                }
                
                // 전달받은 회원정보를 kakaoProfile 객체에 담습니다. (sb2 -> KakaoProfile)
                Gson gson2=new Gson();
                KakaoProfile kakaoProfile=gson2.fromJson(sb2.toString(), KakaoProfile.class);
                
                System.out.println(kakaoProfile.getId());
                KakaoAccount ac = kakaoProfile.getAccount();
                System.out.println( ac.getEmail() );
                
                Profile pf = ac.getProfile();
                System.out.println( pf.getNickname() );
                
             
                
                MemberVO mvo = ms.getMember(  kakaoProfile.getId()  );
                if( mvo == null ) {             
                        mvo.setId( kakaoProfile.getId() );
                        mvo.setEmail( ac.getEmail() );
                        mvo.setName( pf.getNickname() );
                        mvo.setProvider("kakao");                      
                        ms.joinKakao( mvo );
                      
                }             
             
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", mvo);        
                
                return "redirect:/main";
        }
        
        
        
        
}
