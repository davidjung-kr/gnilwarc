/*******************************************************************************
 * gnilwarc - COUPANG 리뷰 크롤러
 * 
 * <pre>
 * Special Thanks
 *  - Header, CSS Selector는 아래 Python 소스코드를 참고하였습니다.
 *  - https://github.com/JaehyoJJAng/Coupang-Review-Crawling
 * 
 * 3th Party
 *  - Jsoup: https://jsoup.org/
 * </pre>
 * 
 * @author davidjung-kr
 * @see https://github.com/davidjung-kr/gnilwarc
 */
package com.davidjung.gnilwarc.data;

/** 쿠팡 제품상세 URL */
public class CoupangProductUrlData {
	/** 제품코드 */
	private String productCode = "";
	/** 리뷰 페이지번호 */
	private int pageNo = 0;
	
	/**
	 * Initialize
	 * @param productCode [제품코드]
	 * @param pageNo [리뷰 페이지번호]
	 */
	public CoupangProductUrlData(String productCode, int pageNo) {
		this.productCode = productCode;
		this.pageNo = pageNo;
	}
	
	/**
	 * Get a Coupang product URL String
	 * @return 제품상세 URL
	 */
	public String get() {
		StringBuffer url = new StringBuffer("https://www.coupang.com/vp/product/reviews?productId=")
			.append(this.productCode)
			.append("&page=")
			.append(pageNo)
			.append("&size=5&sortBy=ORDER_SCORE_ASC&ratings=&q=&viRoleCode=3&ratingSummary=true");
		return url.toString();
	}
	
	/** 제폼코드 */
	public String getProductCode() {
		return this.productCode;
	}
	
	/** 리뷰 페이지번호 */
	public int getPageNo() {
		return this.pageNo;
	}
}