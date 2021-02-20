package study.shop.cidermarket.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import study.shop.cidermarket.helper.PageData;
import study.shop.cidermarket.helper.RegexHelper;
import study.shop.cidermarket.helper.WebHelper;
import study.shop.cidermarket.model.Bbs;
import study.shop.cidermarket.model.Msgbox;
import study.shop.cidermarket.model.Record;
import study.shop.cidermarket.service.MemberService;
import study.shop.cidermarket.service.MsgboxService;
import study.shop.cidermarket.service.ProductService;
import study.shop.cidermarket.service.RecordService;

@Slf4j
@RestController
public class RecordRestController {
	
	@Autowired WebHelper webHelper;
	@Autowired RegexHelper regexHelper;
	
	/** Service 패턴 구현체 주입 */
	@Autowired
	@Qualifier("productService")
	ProductService productService;
	
	@Autowired
	@Qualifier("memberService")
    MemberService memberService;
	
	@Autowired
	@Qualifier("recordService")
	RecordService recordService;
	
	@Autowired
	@Qualifier("RecordMsgboxService")
	MsgboxService recordMsgboxService;
	
	/** 거래내역 판매 택배거래확정 정보 갱신 페이지 */
	@RequestMapping(value="/record/sell/ing/confirm", method=RequestMethod.PUT)
	public Map<String, Object> recordSellingConfirm(
			@RequestParam(value="recono", defaultValue="0") int recono,
			@RequestParam(value="buyer", defaultValue="0") int buyer,
			@RequestParam(value="how", defaultValue="") String how,
			@RequestParam(value="prodno", defaultValue="0") int prodno) {
                
        /** 가져온 값을 이용하여 거래내역 조회 */
        Record input = new Record();
        input.setRecono(recono);
        input.setBuyer(buyer);
        input.setHow(how);
        input.setProdno(prodno);
        
        try {
        	recordService.editConfirmRecord(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
        
        return webHelper.getJsonData();
	}
	
	/** 거래내역 판매 - 직거래 구매 대기자 정보 얻어오기 */
	@RequestMapping(value="/record/sell/ing/jlist", method=RequestMethod.GET)
	public Map<String, Object> recordJSellingConfirm(
			@RequestParam(value="receiver", defaultValue="0") int receiver,
			@RequestParam(value="prodno", defaultValue="0") int prodno) {
                
        /** 가져온 값을 이용하여 거래내역 조회 */
        Msgbox input = new Msgbox();
        input.setProdno(prodno);
        input.setReceiver(receiver);
        
        List<Msgbox> output = null;
        try {
        	output = recordMsgboxService.getMsgboxList(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
        
        /** 3) View 처리 */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("buyer", output);
        return webHelper.getJsonData(data);
	}
	
	/** 거래내역 판매 - 직거래 구매자 확정 정보 갱신 페이지 */
	@RequestMapping(value="/record/sell/ing/jconfirm", method=RequestMethod.PUT)
	public Map<String, Object> recordSellingJConfirm(
			@RequestParam(value="seller", defaultValue="0") int seller,
			@RequestParam(value="prodno", defaultValue="0") int prodno,
			@RequestParam(value="buyer", defaultValue="0") int buyer,
			@RequestParam(value="nickname", defaultValue="0") String nickname) {
                
        /** 가져온 값을 이용하여 거래내역 조회 */
        Record input = new Record();
        input.setProdno(prodno);
        input.setSeller(seller);
        input.setBuyer(buyer);
        input.setNickname(nickname);
        input.setHow("J");
        
        try {
        	recordService.editConfirmRecord(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
        
        return webHelper.getJsonData();
	}
	
	/** 거래내역 판매 거래취소 정보 갱신 페이지 */
	@RequestMapping(value="/record/sell/ing/cancel", method=RequestMethod.PUT)
	public Map<String, Object> recordSellingCancel(
			@RequestParam(value="recono", defaultValue="0") int recono,
			@RequestParam(value="prodno", defaultValue="0") int prodno) {

        /** 가져온 값을 이용하여 거래내역 조회 */
        Record input = new Record();
        input.setRecono(recono);
        input.setProdno(prodno);
        
        try {
        	recordService.editCancelRecord(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
        
        return webHelper.getJsonData();
	}
	
	/** 거래내역 판매 반품승인 정보 갱신 페이지 */
	@RequestMapping(value="/record/sell/ing/refundok", method=RequestMethod.PUT)
	public Map<String, Object> recordSellingRefundOk(
			@RequestParam(value="recono", defaultValue="0") int recono,
			@RequestParam(value="prodno", defaultValue="0") int prodno) {
                
        /** 가져온 값을 이용하여 거래내역 조회 */
        Record input = new Record();
        input.setRecono(recono);
        input.setProdno(prodno);
        
        try {
        	recordService.editRefundOkRecord(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
        
        return webHelper.getJsonData();
	}
	
	/** 거래내역 판매 반품거절 정보 갱신 페이지 */
	@RequestMapping(value="/record/sell/ing/refundx", method=RequestMethod.PUT)
	public Map<String, Object> recordSellingRefundX(
			@RequestParam(value="recono", defaultValue="0") int recono,
			@RequestParam(value="prodno", defaultValue="0") int prodno) {
                
        /** 가져온 값을 이용하여 거래내역 조회 */
        Record input = new Record();
        input.setRecono(recono);
        input.setProdno(prodno);
        
        try {
        	recordService.editRefundXRecord(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
        
        return webHelper.getJsonData();
	}
	
	

    
    
}