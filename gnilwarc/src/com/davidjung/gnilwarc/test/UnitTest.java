package com.davidjung.gnilwarc.test;

import com.davidjung.gnilwarc.app.Main;
import com.davidjung.gnilwarc.config.PageNumber;
import com.davidjung.gnilwarc.data.ReviewData;

public class UnitTest {
	void run() throws Exception {
		String productCode = "290505059"; // 일리 과테말라 최고
		int pageStart = PageNumber.START; // 페이지 시작번호:   1페이지(기본값임)
		int pageEnd   = PageNumber.END;   // 페이지 종료번호: 300페이지(기본값임)
		
		ReviewData[] reviewDatas = Main.getReviewDatas(productCode, pageStart, pageEnd);
		if(reviewDatas.length <=0) {
			throw new Exception("Unittes failed");
		}
	}
}
