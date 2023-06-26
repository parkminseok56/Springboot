package com.ezen.g16.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g16.dto.KakaoProfile;
import com.ezen.g16.dto.KakaoProfile.KakaoAccount;
import com.ezen.g16.dto.KakaoProfile.KakaoAccount.Profile;
import com.ezen.g16.dto.MemberVO;
import com.ezen.g16.dto.OAuthToken;
import com.ezen.g16.service.MemberService;
import com.google.gson.Gson;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;

	@RequestMapping("/")
	public String root(HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session.getAttribute("loginUser") != null)
			return "redirect:/main";
		else
			return "member/loginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result, Model model,
			HttpServletRequest request) {

		String url = "member/loginForm";

		if (result.getFieldError("userid") != null)
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
		else if (result.getFieldError("pwd") != null)
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		else {

			// userid 를 보내서 MemberVO를 리턴
			// userid는 IN 변수에 보내고, "MemberVO에 해당하는 변수"를 OUT 변수로 보냅니다.

			// 그러나 프로시져에서 select 결과는 cursor 이므로, OUT 변수에 비어 잇는 cursor 변수를 넣어야 하는 상황임.
			// 현재 위치에서 PL/SQL의 커서변수를 만들 수는 없음. 다만, 그를 담을 수 있는 Object 변수는 가능함.
			// 그래서 보내는 값, 받는 변수 모두를 수용할 수 있는 HashMap을 사용함.
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", membervo.getUserid()); // 해쉬맵에 검색에 필요한 아이디를 넣어줌.
			paramMap.put("ref_cursor", null); // OUT 변수에 비어 있는 자료형을 전송
			// 이로써 HashMap에는 IN 변수, OUT 변수가 모두 담긴 셈임.

			// getMember 메서드는 리턴값도 paramMap에 담겨서 되돌아올것이 떄문에 별도의 리턴 메서드를 만들지 않음
			ms.getMember(paramMap);

			// 프로시져를 실행하고 돌아온 결과 : paramMap의 키 값 ref_cursor 자리에 ArrayList<HashMap<String,
			// Object>
			// 가 담겨져 옴. <HashMap<String, Object> 안에는 레코드의 필드명을 키값으로 한 필드값들이 들어있음.

			// paramMap에서 ArrayList<HashMap<String, Object> 를 꺼냄
			ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			// 프로시져를 결과 : 레코드의 리스트, 각 레코드 <필드명,필드값> 형태의 해쉬맵 임.
			// ArrayList안에 담겨져 있는 <HashMap<String, Object>는 <필드명,필드값> 임.
			// 지금은 아이디로 검색한 결과 한 개이지만, 보통 여러개로 저장되어 옴.

			// 해쉬맵으로 받아오는 루틴의 특성상 키값이 모두 "대문자"임. 그래서 jsp 에서 사용할 떄도 모두 대문자를 사용해야함.

			// * 리스트의 결과가 아무것도 없는지를 먼저 조사함.
			if (list.size() == 0) {
				model.addAttribute("message", "아이디가 없읍니다");
				return "member/loginForm";

			}

			// 조회결과가 있다면 리스트에 담겨 있는 해쉬맵의 첫 번쨰를 꺼냄.
			HashMap<String, Object> mvo = list.get(0);

			if (mvo.get("PWD") == null)
				model.addAttribute("message", "비밀번호 오류. 관리자에게 문의하세요");
			else if (!mvo.get("PWD").equals(membervo.getPwd()))
				model.addAttribute("message", "비밀번호가 맞지않스므니다");
			else if (mvo.get("PWD").equals(membervo.getPwd())) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", mvo);
				url = "redirect:/main";
			}
		}
		return url;
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {

		HttpSession session = request.getSession();
		// 현재 요청의 세션 객체를 가져옴. getSession() 메소드는 요청과 관련된 세션을 반환함.

		session.invalidate();
		// session.invalidate(): 세션을 무효화하여 사용자의 세션 상태를 종료함. 이로 인해 세션에 저장된 데이터는 모두 삭제됨.
		return "redirect:/";
	}

	@RequestMapping("/kakaostart")
	public @ResponseBody String kakaostart() {
		String a = "<script type='text/javascript'>" + "location.href='https://kauth.kakao.com/oauth/authorize?"
				+ "client_id=0a12e9117e1fe5a43f4dec0602b709c1&" + "redirect_uri=http://localhost:8070/kakaoLogin&"
				+ "response_type=code';" + "</script>";
		return a;
	}

	@RequestMapping("/kakaoLogin")
	public String login(HttpServletRequest request) throws UnsupportedEncodingException, IOException {

		// 카카오 아이디, 비번 인증 + 아이디 이메일 제공 동의 후 전송되는 암호화 코드
		String code = request.getParameter("code");
		// 전송된 암호화코드를 이용해서 토큰을 요청
		// 토큰 요청 주소 url 설정 및 파라미터
		String endpoint = "https://kauth.kakao.com/oauth/token";
		URL url = new URL(endpoint); // import java.net.URL;
		String bodyData = "grant_type=authorization_code&";
		bodyData += "client_id=0a12e9117e1fe5a43f4dec0602b709c1&";
		bodyData += "redirect_uri=http://localhost:8070/kakaoLogin&";
		bodyData += "code=" + code;
		// Stream 연결 및 토큰 수신
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // import java.net.HttpURLConnection;
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoOutput(true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(bodyData);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String input = "";
		StringBuilder sb = new StringBuilder(); // 조각난 String 을 조립하기위한 객체
		while ((input = br.readLine()) != null) {
			sb.append(input);
			System.out.println(input); // 수신된 토큰을 콘솔에 출력합니다
		}

		Gson gson = new Gson();
		OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);

		String endpoint2 = "https://kapi.kakao.com/v2/user/me";
		URL url2 = new URL(endpoint2);
		// import java.net.HttpURLConnection;
		HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
		conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
		conn2.setDoOutput(true);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
		String input2 = "";
		StringBuilder sb2 = new StringBuilder();
		while ((input2 = br2.readLine()) != null) {
			sb2.append(input2);
			System.out.println(input2);
		}

		// 전달받은 회원정보를 kakaoProfile 객체에 담습니다.( sb2 -> kakaoProfile )
		Gson gson2 = new Gson();
		KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
		KakaoAccount ac = kakaoProfile.getAccount();
		Profile pf = ac.getProfile();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", kakaoProfile.getId());
		paramMap.put("ref_cursor", null);
		ms.getMember(paramMap);
		// 해시맵에 담겨온 ref_cursor의 형태는 해시맵들의 리스트이고, 해시맵 하나는 검색결과의 레코드 하나에 해당함.
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");

		if (list == null || list.size() == 0) {
			paramMap.put("userid", kakaoProfile.getId());
			paramMap.put("email", ac.getEmail());
			paramMap.put("name", pf.getNickname());
			paramMap.put("provider", "kakao");
			ms.joinKakao(paramMap);

			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);
			list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		}

		HashMap<String, Object> mvo = list.get(0);

		HttpSession session = request.getSession();
		session.setAttribute("loginUser", mvo);

		return "redirect:/main";
	}

	@RequestMapping("/memberJoinForm")
	public String join_form() {
		return "member/memberJoinForm";
	}

	@RequestMapping("/idcheck")
	public ModelAndView id_check(@RequestParam("userid") String userid) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("ref_cursor", null);
		ms.getMember(paramMap);
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		if (list == null || list.size() == 0)
			mav.addObject("result", -1);
		else
			mav.addObject("result", 1);
		mav.addObject("userid", userid);
		mav.setViewName("member/idcheck");

		return mav;
	}

	@RequestMapping(value = "memberJoin", method = RequestMethod.POST)
	public ModelAndView memberJoin(@ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result,
			@RequestParam("re_id") String reid, @RequestParam("pwd_check") String pwchk, Model model) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/memberJoinForm");
		mav.addObject("re_id", reid);

		if (result.getFieldError("userid") != null)
			mav.addObject("message", "아이디 입력하세요");
		else if (result.getFieldError("pwd") != null)
			mav.addObject("message", "비밀번호 입력하세요");
		else if (!reid.equals(membervo.getUserid()))
			mav.addObject("message", "id 중복체크를 하지 않았습니다");
		else if (!pwchk.equals(membervo.getPwd()))
			mav.addObject("message", "비밀번호 확인이 일치하지 않습니다");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", membervo.getUserid());
			paramMap.put("pwd", membervo.getPwd());
			paramMap.put("name", membervo.getName());
			paramMap.put("email", membervo.getEmail());
			paramMap.put("phone", membervo.getPhone());

			ms.insertMember(paramMap);

			mav.addObject("message", "회원가입이 완료되었습니다. 로그인 하세요");
			mav.setViewName("member/loginForm");
		}
		return mav;
	}

	@RequestMapping("/memberEditForm")
	public ModelAndView memEditForm(HttpServletRequest request) {

		// 세션에서 loginUser 속 데이터들을 MemberVO 형태의 dto에 옮겨담고, 수정페이지로 이동함,
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser = (HashMap<String, Object>) session.getAttribute("loginUser"); // 세션에서
																											// loginUser
																											// 추출

		MemberVO dto = new MemberVO();
		dto.setUserid((String) loginUser.get("USERID"));
		dto.setPwd((String) loginUser.get("PWD"));
		dto.setName((String) loginUser.get("NAME"));
		dto.setEmail((String) loginUser.get("EMAIL"));
		dto.setPhone((String) loginUser.get("PHONE"));
		dto.setProvider((String) loginUser.get("PROVIDER"));

		mav.addObject("dto", dto);
		mav.setViewName("member/memberEditForm");
		return mav;
	}

	@RequestMapping(value = "memberEdit", method = RequestMethod.POST)
	public String memberEdit(
			@ModelAttribute("dto") 
			@Valid MemberVO membervo, 
			BindingResult result,
			@RequestParam(value="pwd_check", required=false) String pwchk, 
			Model model,
			HttpServletRequest request) {

		String url = "member/memberEditForm";
		
		if (membervo.getProvider()==null && result.getFieldError("pwd") != null)
			model.addAttribute("message", "비밀번호를 입력하세요");		
		else if (result.getFieldError("name") != null)
			model.addAttribute("message", "이름을 입력하세요");
		
		else if (membervo.getProvider()==null && pwchk!=null && !pwchk.equals(membervo.getPwd()))
			model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다");
		
		else if (result.getFieldError("email") != null) 
			model.addAttribute("message", "이메일을 입력하세요");
		else if (result.getFieldError("phone") != null) 
			model.addAttribute("message", "전화번호를 입력하세요");								
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", membervo.getUserid());
			
			if(membervo.getProvider()!=null) paramMap.put("pwd","");
			else paramMap.put("pwd", membervo.getPwd());
			
			paramMap.put("pwd", membervo.getPwd());
			paramMap.put("name", membervo.getName());
			paramMap.put("email", membervo.getEmail());
			paramMap.put("phone", membervo.getPhone());
            
			if(membervo.getProvider()!=null) paramMap.put("provider",membervo.getProvider());
			else paramMap.put("provider", null);
			
			ms.updateMember(paramMap);
			
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", paramMap);
			
		    url="redirect:/main";
		}
		return url;
	}

}
