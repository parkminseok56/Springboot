package com.ezen.g16.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g16.dto.Paging;
import com.ezen.g16.service.BoardService;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class BoardController {

	@Autowired
	BoardService bs;

	@RequestMapping("/main")
	public ModelAndView goMain(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") == null)
			mav.setViewName("member/loginForm");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			paramMap.put("ref_cursor", null);

			bs.selectBoard(paramMap);

			ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			mav.addObject("boardList", list);
			mav.addObject("paging", (Paging) paramMap.get("paging"));
			mav.setViewName("board/main");

		}
		return mav;
	}

	@RequestMapping("/boardView")
	public ModelAndView boardView(@RequestParam("num") int num) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("num", num);
		paramMap.put("ref_cursor1", null);
		paramMap.put("ref_cursor2", null);

		bs.boardViewWithoutCount(paramMap);

		ArrayList<HashMap<String, Object>> list1 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor1");
		ArrayList<HashMap<String, Object>> list2 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor2");

		mav.addObject("board", list1.get(0));
		mav.addObject("replyList", list2);

		mav.setViewName("board/boarView");
		return mav;
	}

	@RequestMapping("/addReply")
	public String addReply(@RequestParam("boardnum") int boardnum, @RequestParam("userid") String userid,
			@RequestParam("content") String content, HttpServletRequest request) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardnum", boardnum);
		paramMap.put("userid", userid);
		paramMap.put("content", content);

		bs.insertReply(paramMap);

		return "redirect:/boardViewWithoutCount?num=" + boardnum;
	}

	@RequestMapping("/boardViewWithoutCount")
	public ModelAndView boardViewWithoutCount(@RequestParam("num") int num, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("num", num);
		paramMap.put("ref_cursor1", null);
		paramMap.put("ref_cursor2", null);

		bs.boardViewWithoutCount(paramMap);

		ArrayList<HashMap<String, Object>> list1 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor1");
		ArrayList<HashMap<String, Object>> list2 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor2");

		mav.addObject("board", list1.get(0));
		mav.addObject("replyList", list2);

		mav.setViewName("board/boarView");
		return mav;
	}

	@RequestMapping("/deleteReply")
	public String reply_delete(@RequestParam("num") int replynum, @RequestParam("boardnum") int boardnum,
			HttpServletRequest request) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("replynum", replynum);
		bs.deleteReply(paramMap);

		return "redirect:/boardViewWithoutCount?num=" + boardnum;
	}

	@RequestMapping("/boardWriteForm")
	public String write_form(HttpServletRequest request) {

		String url = "board/boardWriteForm";
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") == null)
			url = "member/loginForm";

		return url;
	}

	@RequestMapping("/selectimg")
	public String selectimg() {
		return "board/selectimg";

	}

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String fileupload(Model model, @RequestParam("image") MultipartFile file, HttpServletRequest request) {

		String path = context.getRealPath("/upload");
		// System.out.println("path= " + path);
		// System.out.println("multipartFile = " + file);

		Calendar today = Calendar.getInstance();
		long t = today.getTimeInMillis();
		String filename = file.getOriginalFilename(); // 파일 이름 추출
		String fn1 = filename.substring(0, filename.indexOf(".")); // 파일 이름과 확장자 분리
		String fn2 = filename.substring(filename.indexOf(".") + 1);

		if (!file.isEmpty()) { // 업로드할 파일이 존재한다면
			String uploadPath = path + "/" + fn1 + t + "." + fn2;
			System.out.println("파일 저장 경로 = " + uploadPath);
			try {

				file.transferTo(new File(uploadPath));
			} catch (IllegalStateException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			}
		}

		return "board/completupload";

	}

}
