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
package com.davidjung.gnilwarc.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.davidjung.gnilwarc.config.DefaultHttpHeader;
import com.davidjung.gnilwarc.config.PageNumber;
import com.davidjung.gnilwarc.config.Verbose;
import com.davidjung.gnilwarc.data.CoupangProductUrlData;
import com.davidjung.gnilwarc.data.ReviewData;

public class Main {
	public static void main(String[] args) {
		// (1) 크롤링 하기 위한 기본데이터 설정
		String productCode = getUserInput(args); // 제품코드 입력받기
		int pageStart = PageNumber.START;        // 페이지 시작번호:   1페이지(기본값임)
		int pageEnd   = PageNumber.END;          // 페이지 종료번호: 300페이지(기본값임)
		
		// (2) 크롤링 수행
		ReviewData[] reviewDatas = getReviewDatas(productCode, pageStart, pageEnd);
		if(reviewDatas.length<=0) {
			System.out.println("조회된 리뷰데이터가 없습니다. 프로그램을 종료합니다.");
			return;
		}
		
		// (3) 리뷰데이터를 한 줄 짜리 문자열로 변환 (ex. 리뷰고객명\t제품명\t점수\t리뷰내용..)
		StringBuffer fileContent = new StringBuffer();
		for(int i=0; i<reviewDatas.length; i++) {
			fileContent.append(reviewDatas[i].getStringWithTab())
				.append("\n");
		}
		
		// (4) 문자열 데이터를 파일로 쓰기
		boolean isSuccess = fetchToFile(
			String.format("%s.txt", productCode),
			fileContent.toString()
		);
		if(isSuccess) {
			System.out.println("처리가 완료되었습니다.");
		}
	}
	
	/**
	 * Get review data array
	 * @param productCode [제품코드]
	 * @param pageStart [페이지 시작번호]
	 * @param pageEnd [페이지 종료번호]
	 * @return
	 */
	public static ReviewData[] getReviewDatas(String productCode, int pageStart, int pageEnd) {
		ArrayList<ReviewData> array = new ArrayList<ReviewData>();
		
		// 첫 페이지부터 마지막페이지까지 읽기 시작
		for(int page=pageStart; page<=pageEnd; page++) {
			Elements articles = getArticlesByPage( new CoupangProductUrlData(productCode, page) ); // 리뷰아티클 목록을 가져온다. 한 페이지당 기본 5개
			if(articles==null || articles.size()<=0) { // 읽어올 아티클이 없으면 종료
				
				if(Verbose.isTMI()) // TMI모드일 때만 콘솔출력
					System.out.printf("[%d] 페이지에 읽어올 리뷰 아티클이 없어 조회를 중단합니다.\n", page);
				
				break;
			}
			int size = articles.size();
			// (3) 해당 페이지에 아티클이 존재하는 경우 - 불러올 리뷰갯수를 취득 (기본 값: 5개)
			for(int articleIndex=0; articleIndex<size; articleIndex++) { 
				ReviewData reviewData = chapReviewDataFromArticle( articles.get(articleIndex) ); // 리뷰 데이터를 하나씩 파싱
				array.add(reviewData);                                                           // 파싱한 리뷰데이터를 버퍼에 임시저장
			}
			if(Verbose.isTMI()) // TMI모드일 때만 콘솔출력
				System.out.printf("[%d] 페이지의 데이터 읽기 완료!\n", page);
		}
		ReviewData[] result = array.toArray(new ReviewData[array.size()]);
		return result;
	}
	
	/** 사용자로부터 제품코드 입력 받기 */
	private static String getUserInput(String[] args) {
		if( args.length>=1 && isNumeric(args[0]) ) {
			return args[0];
		} else {
			boolean ok = false;
			String input = "";
			while(ok==false) {
				System.out.print("제품코드를 입력해주세요: ");
				input = new Scanner(System.in).next().trim();
				ok = isNumeric(input);
			}
			return input;
		}
	}
	
	/**
	 * Get articles from Coupang Product URL
	 * @param coupangUrl [상품주소객체]
	 * @return
	 */
	private static Elements getArticlesByPage(CoupangProductUrlData coupangUrl) {
		String url = coupangUrl.get();
		Document doc = new Document(url);
		try {
			doc = Jsoup.connect(url)          // ---------- 헤더 설정 ----------
				.header("authority",          DefaultHttpHeader.AUTHORITY)
				.header("method",             DefaultHttpHeader.METHOD)
				.header("accept",             DefaultHttpHeader.ACCEPT)
				.header("accept-encoding",    DefaultHttpHeader.ACCEPT_ENCODING)
				.header("user-agent",         DefaultHttpHeader.USER_AGENT)
				.header("sec-ch-ua-platform", DefaultHttpHeader.SEC_CH_UA_PLATFORM)
				.header("sec-ch-ua-mobile",   DefaultHttpHeader.SEC_CH_UA_MOBILE)
				.header("sec-fetch-dest",     DefaultHttpHeader.SEC_FETCH_DEST)
				.header("sec-fetch-mode",     DefaultHttpHeader.SEC_FETCH_MODE)
				.header("sec-fetch-site",     DefaultHttpHeader.SEC_FETCH_SITE)
				.header("cookie",             DefaultHttpHeader.COOKIE)
				.header("referer",            url)
				.get(); // GET요청쓰
			if(doc==null) {
				throw new Exception(String.format("GET연결을 실패했습니다. => [%s]", coupangUrl));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Elements articles = doc.select("article.sdp-review__article__list");
		if(articles.size() <= 0) {
			System.out.printf("[%s] %d 페이지에 해당하는 리뷰 정보가 없습니다.\n",
				coupangUrl.getProductCode(), coupangUrl.getPageNo()
			);
			return null;
		}
		return articles;
	}

	/**
	 * Get review data from Jsoup HTML Element instance
	 * @param article [Jsoup HTML Element]
	 * @return
	 */
	private static ReviewData chapReviewDataFromArticle(Element article) {
		ReviewData reviewData = new ReviewData(
			selectFirstATextWithNullSafety(article, "span.sdp-review__article__list__info__user__name"), // 구매자이름
			article.selectFirst("div.sdp-review__article__list__info__product-info__star-orange").attr("data-rating").toString(), // 평점
			selectFirstATextWithNullSafety(article, "div.sdp-review__article__list__info__product-info__name"), // 상품명
			selectFirstATextWithNullSafety(article, "div.sdp-review__article__list__headline"), // 타이틀
			selectFirstATextWithNullSafety(article, "div.sdp-review__article__list__review > div"), // 리뷰내용
			selectFirstATextWithNullSafety(article, "span.sdp-review__article__list__survey__row__answer")
		);
		return reviewData;
	}
	
	/**
	 * Get text from HTML Element instance
	 * @param element [Jsoup HTML Element]
	 * @param eval [CSS selector]
	 * @return
	 */
	private static String selectFirstATextWithNullSafety(Element element, String eval) {
		try {
			return element.selectFirst(eval).text();
		} catch(NullPointerException e) {
			return "";
		}
	}
	
	/**
	 * File write
	 * @param fileName [파일명]
	 * @param fileContent [파일내용]
	 * @return 성공여부
	 */
	private static boolean fetchToFile(String fileName, String fileContent) {
		// 파일이름이나 내용이 없을 경우 실패로 간주
		if(fileName==null || fileName.isEmpty() || fileContent==null || fileContent.isEmpty()) {
			return false;
		}
		// 파일쓰기 시도
		try{
			BufferedWriter io = new BufferedWriter(
				new FileWriter( new File(fileName) )
			);
			io.write(fileContent);
			io.close();
		} catch (IOException e) {
			System.out.printf("파일쓰기 에러가 발생했습니다. 파일경로:[%s]\n", fileName);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static boolean isNumeric(String something) {
		try {
			Double.parseDouble(something);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
}