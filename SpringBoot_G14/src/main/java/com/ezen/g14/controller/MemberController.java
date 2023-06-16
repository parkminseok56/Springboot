package com.ezen.g14.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.g14.dto.KakaoProfile;
import com.ezen.g14.dto.KakaoProfile.KakaoAccount;
import com.ezen.g14.dto.KakaoProfile.KakaoAccount.Profile;
import com.ezen.g14.dto.MemberVO;
import com.ezen.g14.dto.OAuthToken;
import com.ezen.g14.service.MemberService;
import com.google.gson.Gson;

@Controller
public class MemberController {
     
	@Autowired
	 MemberService ms;
	//@Autowired MemberService ms;: MemberService 클래스의 인스턴스를 자동으로 주입합니다. 
	//이를 통해 ms 변수는 MemberService의 객체를 참조하게 됩니다.


	
	@RequestMapping("/")
	// @RequestMapping("/"): 루트 경로 ("/")에 대한 요청을 처리하는 메서드로  애플리케이션의 기본 주소에 접속했을 때 실행됨.
	public String root() {
		return "member/loginForm";
	} 
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	///login" 경로로 POST 요청이 오면 이 메서드가 실행되도록 매핑합니다.
	public String login( @ModelAttribute("dto")
	                    // MemberVO 객체를 "dto"라는 이름으로 모델에 추가합니다.
	                    @Valid MemberVO membervo, 
	                    // @Valid:데이터 유효성 검사를 수행함.
			            BindingResult result,
			            // BindingResult result: 유효성 검사 결과를 저장하는 객체
			            HttpServletRequest request, 
			            // HttpServletRequest request: HTTP 요청 정보를 담고 있는 객체
			            Model model) { 
			            // Model model: 뷰에 데이터를 전달하기 위한 모델 객체.
		
		String url = "member/loginForm";  
		// 초기 URL 값을 "member/loginForm"으로 설정합니다.
		
		if(result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());		
			// 만약 아이디 필드에 오류가 있는 경우, 해당 오류 메시지를 모델에 추가합니다.	
		}else if (result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());	  
			// 만약 비밀번호 필드에 오류가 있는 경우, 해당 오류 메시지를 모델에 추가합니다.					
	    }else { 
		   // 아이디 비번이 정상 입력된 경우
	    	MemberVO mvo = ms.getMember( membervo.getUserid()); 
	    	// 입력된 아이디로 회원 정보를 조회합니다.
	    	if ( mvo == null) model.addAttribute("message","아이디가 없으므니다");
	    	//  회원 정보가 없는 경우, "아이디가 없습니다"라는 오류 메시지를 모델에 추가합니다.	
	    	
	    	else if ( mvo.getPwd() == null)	model.addAttribute("message","DB 오류. 관리자에게 문의하시므니다");
	    	// 회원 정보가 있지만 비밀번호가 없는 경우, "DB 오류. 관리자에게 문의하세요"라는 오류 메시지를 모델에 추가합니다.	
	    	
	    	else if ( !mvo.getPwd().equals(membervo.getPwd()))
	    		model.addAttribute("message","비밀번호가 맞지않스므니다");
	    		
	        else if ( mvo.getPwd().equals( membervo.getPwd())) {
	        	// 회원 정보가 있고 비밀번호가 일치하는 경우:
	        	HttpSession session = request.getSession();
	        	// HttpServletRequest 객체를 사용하여 세션을 가져옴.
	        	session.setAttribute("loginUser", mvo);  
	        	// 세션에 로그인 사용자 정보를 저장합니다.
	        	url  = "redirect:/main";  
	        	//리다이렉트할 URL을 "redirect:/main"으로 설정.
	    	   
	       }
	    }
		return url;  
		// 최종적으로 결정된 URL을 반환하여 로그인 처리 이후에 어떤 페이지로 이동할지 결정함.
		
	}
	
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout( HttpServletRequest request ) {
		HttpSession session = request.getSession();   	
   	    session.invalidate();     	
    	return "redirect:/";  
	   
   }


		
	@RequestMapping("/kakaoLogin")
	public String login( HttpServletRequest request )  throws UnsupportedEncodingException, IOException {
	
		String code = request.getParameter("code");
		
		String endpoint="https://kauth.kakao.com/oauth/token";
		URL url = new URL(endpoint); // import java.net.URL;
		
		String bodyData="grant_type=authorization_code&";
		bodyData += "client_id= 0a12e9117e1fe5a43f4dec0602b709c1\r\n&";
		bodyData += " redirect_uri=http://localhost:8070/kakaoLogin&";
		bodyData += "code=" +code;
		
		
		// Stream 연결
		HttpURLConnection conn=(HttpURLConnection)url.openConnection(); //import java.net.HttpURLConnection;
		// http header 값 넣기
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf=8");
		conn.setDoOutput(true);
		
		
	    // 인증 절차를 완료하고 User info 요청 위한 정보를 요청 및 수신함.
        BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(conn.getOutputStream(), "UTF-8")
        );
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8")
        );
        String input = "";
        StringBuilder sb = new StringBuilder(); // 조각난 String 을 조립하기위한 객체
        while((input=br.readLine())!=null) { 
                sb.append(input);
                System.out.println(input);
        }
		// sb:
        
        
		// 여기서 부터 Gson 으로  파싱 
        // 파싱(Parsing)은 주어진 데이터를 문법적 구조로 분석하고 해석하는 과정을 말함. 
        // 파싱은 특정 형식 또는 규칙에 따라 구성된 데이터를 이해하고 처리하기 위해 사용됨. 
        // 대부분의 경우, 파싱은 텍스트 기반 데이터를 분석하는 과정을 의미하지만, 
        // XML, JSON, HTML과 같은 구조화된 데이터 형식을 처리하는 데에도 사용됨.
        
        // Gson은 Google에서 개발한 Java 기반의 라이브러리로,
        // JSON 데이터를 자바 객체로 변환하거나 자바 객체를 JSON 형식으로 직렬화하는 데 사용됨.
        // Gson은 "Google's JSON"을 줄인 말로, JSON 데이터와 자바 객체 간의 변환을 간편하게 처리할 수 있는 기능을 제공함.
        
        // Gson을 사용하면 JSON 데이터를 자바 객체로 변환할 수 있으며, 
        // 자바 객체를 다시 JSON 형식으로 변환할 수도 있음. 
        // 이를 통해 웹 서버와 클라이언트 사이에서 JSON 데이터를 주고받을 때 유용하게 사용할 수 있음.


        Gson gson = new Gson();
        
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        //oAuthToken <- sb{"access_token
        
        
        String endpoint2="https://kapi.kakao.com/v2/user/me";
        URL url2 = new URL(endpoint2);
        //import java.net.HttpURLConnection;
        HttpURLConnection conn2=(HttpURLConnection)url2.openConnection();
        
        //header 값 넣기
        conn2.setRequestProperty("Authorization", "Bearer" + oAuthToken.getAccess_token());
        conn2.setDoOutput(true);
        
        // UserInfo 수신
        BufferedReader br2 = new BufferedReader(        		
            new InputStreamReader(conn2.getInputStream(), "UTF-8")
        );      		
        String input2 = "";
        StringBuilder sb2 = new StringBuilder(); 
        while((input2=br.readLine())!=null) { 
                 sb2.append(input2);
                 System.out.println(input2);
        }
        
        // sb2에 도착한 실제 사용자 정보를 사용함
        // sb2:
        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile= gson2.fromJson(sb2.toString(), KakaoProfile.class);
        // kakaoProfile 
        
        System.out.println(kakaoProfile.getId() );
        
        KakaoAccount ac = kakaoProfile.getAccount();
        System.out.println(ac.getEmail());
        
        Profile pf = ac.getProfile();
        System.out.println(pf.getNickname());
        
        // kakao 로 부터 얻은 정보로 member 테이블에서 조회함.
        MemberVO mvo = ms.getMember(kakaoProfile.getId() );
        
        
        
		
		return "redirect:/main";  		
	 }
	
	
	
	
	
}
