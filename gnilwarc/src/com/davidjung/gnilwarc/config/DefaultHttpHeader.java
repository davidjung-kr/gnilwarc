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
package com.davidjung.gnilwarc.config;

/** 기본 헤더데이터 */
public class DefaultHttpHeader {
	public static String AUTHORITY = "www.coupang.com";
	public static String METHOD = "GET";
	public static String ACCEPT = "*/*";
	public static String ACCEPT_ENCODING = "gzip, deflate, br";
	public static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.104 Whale/3.13.131.36 Safari/537.36";
	public static String SEC_CH_UA_PLATFORM = "macOS";
	public static String SEC_CH_UA_MOBILE = "?0";
	public static String SEC_FETCH_DEST = "empty";
	public static String SEC_FETCH_MODE = "cors";
	public static String SEC_FETCH_SITE = "same-origin";
	public static String COOKIE = "PCID=31489593180081104183684; _fbp=fb.1.1644931520418.1544640325; gd1=Y; X-CP-PT-locale=ko_KR; MARKETID=31489593180081104183684; sid=03ae1c0ed61946c19e760cf1a3d9317d808aca8b; overrideAbTestGroup=%5B%5D; x-coupang-origin-region=KOREA; x-coupang-accept-language=ko_KR;";
}