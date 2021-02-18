package study.shop.cidermarket.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import study.shop.cidermarket.helper.PageData;
import study.shop.cidermarket.helper.RegexHelper;
import study.shop.cidermarket.helper.WebHelper;
import study.shop.cidermarket.model.Member;
import study.shop.cidermarket.service.MemberService;

@Controller
public class MemberAjaxContorller {
    /** WebHelper 주입 */
    // -> import org.springframework.beans.factory.annotation.Autowired;
    @Autowired WebHelper webHelper;
    
    /** RegexHelper 주입 */
    @Autowired RegexHelper regexHelper;
    
    /** Service 패턴 구현체 주입 */
    @Qualifier("memberService")
    @Autowired MemberService memberService;
    
    /** 로그인 페이지 */
    @RequestMapping(value="/member/login.cider", method=RequestMethod.GET)
    public ModelAndView login(Model model,
    		@RequestParam(value="email", defaultValue="") String email) {
    	
    	model.addAttribute("email", email);
		return new ModelAndView("user/login");
    }
    
    /** ID 찾기 페이지 */
    @RequestMapping(value="/member/find_id.cider", method=RequestMethod.GET)
    public ModelAndView findid(Model model) {
		return new ModelAndView("user/find_id");
    }
    
    /** PW 찾기 페이지 */
    @RequestMapping(value="/member/find_pw.cider", method=RequestMethod.GET)
    public ModelAndView findpw(Model model) {
		return new ModelAndView("user/find_pw");
    }
    
    /** 관리자 멤버 목록 페이지 */
    @RequestMapping(value="/admin/member/list.cider", method=RequestMethod.GET)
    public ModelAndView list(Model model,
            // 검색어
            @RequestParam(value="keyword", required=false) String keyword,
            // 페이지 구현에서 사용할 현재 페이지 번호
            @RequestParam(value="page", defaultValue="1") int nowPage) {
        
        /** 1) 페이지 구현에 필요한 변수값 생성 */
        int totalCount = 0;     // 전체 게시글 수
        int listCount = 10;     // 한 페이지당 표시할 목록 수
        int pageCount = 5;      // 한 그룹당 표시할 페이지 번호 수
        
        /** 2) 데이터 조회하기 */
        // 조회에 필요한 조건값(검색어)를 Beans에 담는다.
        Member input = new Member();
        input.setEmail(keyword);
        
        List<Member> output = null;
        PageData pageData = null;
        
        try {
            // 전체 게시글 수 조회
            totalCount = memberService.getMemberCount(input);
            // 페이지 번호 계산 --> 계산결과를 로그로 출력될 것이다.
            pageData = new PageData(nowPage, totalCount, listCount, pageCount);
            
            // SQL의 LIMIT절에서 사용될 값을 Beans의 static 변수에 저장
            Member.setOffset(pageData.getOffset());
            Member.setListCount(pageData.getListCount());

            // 데이터 조회하기
            output = memberService.getMemberList(input);
        } catch (Exception e) {
            return webHelper.redirect(null, e.getLocalizedMessage());
        }
        
        /** 3) View 처리 */
        model.addAttribute("keyword", keyword);
        model.addAttribute("output", output);
        model.addAttribute("pageData", pageData);
        
        return new ModelAndView("admin/member_adm");
    }
        
    /** 회원가입 작성 폼 페이지 */
    @RequestMapping(value="/member/join.cider", method=RequestMethod.GET)
    public ModelAndView add(Model model) {
        return new ModelAndView("user/join");
    }
}
