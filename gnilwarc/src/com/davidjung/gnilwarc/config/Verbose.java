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

import com.davidjung.gnilwarc.consts.VerboseLevel;

/** 콘솔출력여부 */
public class Verbose {
	private static VerboseLevel STATUS = VerboseLevel.TMI;

	/** TMI모드 여부 */
	public static boolean isTMI() {
		return STATUS==VerboseLevel.TMI;
	}
	
	/** QUIET모드 여부 */
	public static boolean isQuiet() {
		return STATUS==VerboseLevel.QUIET;
	}
}