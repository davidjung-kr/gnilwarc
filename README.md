# GNILWARC
Coupang 리뷰 크롤러

# Dependencies
* OpenJDK 11 LTS - [Tested OpenJDK 11 LTS with Microsoft Build](https://learn.microsoft.com/ko-kr/java/openjdk/download)
* [Jsoup](https://jsoup.org)

# Special Thanks
Header, CSS Selector는 아래 Python 소스코드를 참고하였습니다.
* https://github.com/JaehyoJJAng/Coupang-Review-Crawling

# How to use
## Execute a jar file
```
java -jar gnilwarc.jar
제품코드를 입력해주세요: 290505059
[1] 페이지의 데이터 읽기 완료!
...
```
Or,
```
java -jar gnilwarc.jar 290505059
[1] 페이지의 데이터 읽기 완료!
...
```

## Coding
Check out a [com.davidjung.gnilwarc.test.UnitTest.java](https://github.com/davidjung-kr/gnilwarc/blob/main/gnilwarc/src/com/davidjung/gnilwarc/test/UnitTest.java) file.
```
String productCode = "290505059";
int pageStart = PageNumber.START; // 페이지 시작번호:   1페이지(기본값임)
int pageEnd   = PageNumber.END;   // 페이지 종료번호: 300페이지(기본값임)
		
ReviewData[] reviewDatas = Main.getReviewDatas(productCode, pageStart, pageEnd);
```
