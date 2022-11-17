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

/** Review Data */
public class ReviewData {
	/** 구매자 이름 */
	private String userName;
	/** 평점 */
	private int rating;
	/** 상품명 */
	private String productName;
	/** 타이틀 */
	private String title;
	/** 리뷰내용 */
	private String review;
	/** 맛 만족도 */
	private String answer;
	
	/**
	 * Initialize
	 * @param userName [구매자 이름]
	 * @param rating [평점]
	 * @param productName [상품명]
	 * @param title [타이틀]
	 * @param review [리뷰내용]
	 * @param answer [맛 만족도]
	 */
	public ReviewData(String userName, String rating, String productName, String title, String review, String answer) {
		// 평점은 문자열로 받아서 인티저로 컨버팅 - 포맷이 안맞으면 0으로 일괄처리
		try {
			this.rating = Integer.parseInt(rating);
		} catch(NumberFormatException  e) {
			this.rating = 0;
		}
		// 그 외 모두 trim 후 저장
		this.userName    = userName==null    || "".equals(userName)    ? "-":userName.trim();
		this.productName = productName==null || "".equals(productName) ? "-":productName.trim();
		this.title       = title==null       || "".equals(title)       ? "-":title.trim();
		this.review      = review==null      || "".equals(review)      ? "-":review.trim();
		this.answer      = answer==null      || "".equals(answer)      ? "-":answer.trim();
		
		// 리뷰데이터에 화이트스페이스 추가제거
		this.review = this.review.replace("\r", "").replace("\n", "").replace("\t", "");
	}

	public String getUserName() {
		return this.userName;
	}
	
	public int getRating() {
		return this.rating;
	}

	public String getProductName() {
		return this.productName;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getReview() {
		return this.review;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public String getStringWithTab() {
		return String.format("%s\t%d\t%s\t%s\t%s\t%s",
			this.userName, this.rating, this.productName, this.title, this.review, this.answer);
	}
	
	public String getformattedString() {
		return String.format("userName:%s;rating:%d;productName:%s;title:%s;review:%s;answer:%s",
			this.userName, this.rating, this.productName, this.title, this.review, this.answer);
	}
}
