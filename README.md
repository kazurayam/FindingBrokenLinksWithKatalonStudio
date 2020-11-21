Finding Broken Links in web pages with Katalon Studio
=========

This is a [Katalon Studio](https://www.katalon.com/) project for demonstration purpose.
You can download it from [Releases](https://github.com/kazurayam/FindingBrokenLinksWithKatalonStudio/releases) page, unzip, open and run it.

This project was developed using Katalon Studio 7.2.2, but will work in older versions as well.

## Problem to solve

How can I find broken links in web pages?

## My solution

### Problem analysis

I would describe the "Fining broken links" problem as follows:

1. I want to visit a web and and to find all `<a href="url">` tags in the page, and check if the linked URL is actually accessible
2. In order to check the accesibility, I want to make HTTP GET Request per each links, and report a list of Response Status-URL pairs
3. I want to check the value of Status Code of HTTP Response. I will regard the value `200`, `301`, `302` as OK. Others Status Code values are regarded as *broken*.
4. I want to visit multiple web pages in a test run.
5. I want to count the number of broken links
5. If one or more links are broken, I want to be notified of it with a FAILURE message displayed in red color; if no broken links found the test should finish quietly
6. I want the test runs as fast as possible.
7. I want to solve this problem in Katalon Studio.

### How to run the demo

Start Katalon Studio, open the project and run  [`Test Cases/main`](Scripts/main/Script1605930113008.groovy).

### Code

The entry point is 
- [`Test Cases/main`](Scripts/main/Script1605930113008.groovy)

The `main` is short, looks like this:

```
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * This test case finds broken <a href="..."> tags in web pages and report
 */

int brokenLinks = 0

brokenLinks += WebUI.callTestCase(findTestCase("findBrokenAnchorsInPage"),
	["pageUrl": "https://www.google.com/search?q=katalon"])

// more pages to check as many as you want ...

if (brokenLinks > 0) {
	KeywordUtil.markFailed("one or more broken links are found")
}
```

The `main` script calls another worker script 
[`Test Cases/findBrokenAnchrosInPage`](Scripts/findBrokenAnchorsInPage/Script1605929957737.groovy) while specifying the URL of web page to visit. You can repeat calling the worker script for checking any URLs you want.

### Worker script and Custom Keyword

The [`Test Cases/findBrokenAnchrosInPage`](Scripts/findBrokenAnchorsInPage/Script1605929957737.groovy) opens the specified page, find all `<a href="URL">` elements contained there, make a list of URLs to link, then call Custom Keyword `com.kazurayam.ksbackyard.LinkTestUtils.findBrokenLinks(List<String>)` which checks the accessibility of listed URLS and print reports.

Please read the source of 
[`Keywords/com.kazurayam.ksbackyard.LinkTestUtils.groovy`](Keywords/com/kazurayam/ksbackyard/LinkTestUtils.groovy) to find out the implementation detail.

### Test Result

When I ran the test against the URL https://www.google.com/search?q=katalon, the test emitted the following output.

```
*** All <a> elements in https://www.google.com/search?q=katalon ***
200 <a href=https://support.google.com/websearch/answer/181196?hl=ja>
301 <a href=https://www.google.co.jp/intl/ja/about/products?tab=wh>
200 <a href=https://accounts.google.com/ServiceLogin?hl=ja&passive=true&continue=https://www.google.com/search%3Fq%3Dkatalon&ec=GAZAAQ>
200 <a href=https://www.google.com/webhp?hl=ja&sa=X&ved=0ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQPAgI>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://www.google.com/search?q=katalon&source=lnms&tbm=vid&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoAXoECBgQAw>
200 <a href=https://www.google.com/search?q=katalon&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoAnoECBgQBA>
200 <a href=https://www.google.com/search?q=katalon&source=lnms&tbm=nws&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoA3oECBgQBQ>
302 <a href=https://maps.google.com/maps?q=katalon&um=1&ie=UTF-8&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoBHoECBgQBg>
200 <a href=https://www.google.com/search?q=katalon&source=lnms&tbm=shop&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoAHoECBgQCg>
200 <a href=https://www.google.com/search?q=katalon&source=lnms&tbm=bks&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoAXoECBgQCw>
302 <a href=https://www.google.com/flights?q=katalon&source=lnms&tbm=flm&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoAnoECBgQDA>
200 <a href=https://www.google.com/search?q=katalon&source=lnms&tbm=fin&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_AUoA3oECBgQDQ>
200 <a href=https://www.google.com/preferences>
200 <a href=https://www.google.com/preferences?hl=ja&prev=https://www.google.com/search?q%3Dkatalon>
200 <a href=https://www.google.com/preferences?hl=ja&prev=https://www.google.com/search?q%3Dkatalon#languages>
302 <a href=https://www.google.com/setprefs?safeui=on&sig=0_-MdVNtEeDKWcNMLSDsTEecY0Yc4%3D&prev=https://www.google.com/search?q%3Dkatalon>
200 <a href=https://www.google.com/advanced_search?q=katalon&hl=ja>
302 <a href=https://www.google.com/history/optout?hl=ja>
200 <a href=https://www.google.com/history/privacyadvisor/search/unauth?utm_source=googlemenu>
200 <a href=https://support.google.com/websearch/?source=g&hl=ja>
200 <a href=https://www.google.com/search?q=katalon&source=lnt&tbs=lr:lang_1ja&lr=lang_ja&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQpwV6BAgYEB8>
200 <a href=https://www.google.com/search?q=katalon&source=lnt&tbs=qdr:h&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQpwV6BAgYECQ>
200 <a href=https://www.google.com/search?q=katalon&source=lnt&tbs=qdr:d&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQpwV6BAgYECU>
200 <a href=https://www.google.com/search?q=katalon&source=lnt&tbs=qdr:w&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQpwV6BAgYECY>
200 <a href=https://www.google.com/search?q=katalon&source=lnt&tbs=qdr:m&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQpwV6BAgYECc>
200 <a href=https://www.google.com/search?q=katalon&source=lnt&tbs=qdr:y&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQpwV6BAgYECg>
200 <a href=https://www.google.com/search?q=katalon&source=lnt&tbs=li:1&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQpwV6BAgYEC8>
302 <a href=https://www.katalon.com/>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:AY6Tl3FzBkMJ:https://www.katalon.com/+&cd=1&hl=ja&ct=clnk&gl=jp>
200 <a href=https://www.google.com/search?q=related:https://www.katalon.com/+katalon&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQHzAAegQIBRAG>
200 <a href=https://translate.google.com/translate?hl=ja&sl=en&u=https://www.katalon.com/&prev=search&pto=aue>
200 <a href=https://www.katalon.com/sign-up/>
200 <a href=https://www.katalon.com/pricing/>
200 <a href=https://www.katalon.com/katalon-studio/>
200 <a href=https://docs.katalon.com/katalon-studio/docs/index.html>
200 <a href=https://qiita.com/kazurayam/items/aece5d314f84893a0a14>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:vHAftIdtb6EJ:https://qiita.com/kazurayam/items/aece5d314f84893a0a14+&cd=2&hl=ja&ct=clnk&gl=jp>
200 <a href=https://qiita.com/RyomaMaeda/items/0d02c1687e8bc4e57944>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:RNVBRUJGoqYJ:https://qiita.com/RyomaMaeda/items/0d02c1687e8bc4e57944+&cd=3&hl=ja&ct=clnk&gl=jp>
200 <a href=https://blog.engineer.adways.net/entry/advent_calendar_2018/07>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:k2-k8oSBCXIJ:https://blog.engineer.adways.net/entry/advent_calendar_2018/07+&cd=4&hl=ja&ct=clnk&gl=jp>
200 <a href=https://chrome.google.com/webstore/detail/katalon-recorder-selenium/ljdobmomdgdljniojadhoplhkpialdid?hl=ja>
200 <a href=https://www.itreview.jp/products/katalon-studio/reviews>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:3NPH48fjbtwJ:https://www.itreview.jp/products/katalon-studio/reviews+&cd=6&hl=ja&ct=clnk&gl=jp>
200 <a href=http://take-web.com/cad/2019/03/katalon02/>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=http://webcache.googleusercontent.com/search?q=cache:J8Ty-CNXBqAJ:take-web.com/cad/2019/03/katalon02/+&cd=7&hl=ja&ct=clnk&gl=jp>
200 <a href=https://devtab.jp/entry/internal/103>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:87OELWRWjQoJ:https://devtab.jp/entry/internal/103+&cd=8&hl=ja&ct=clnk&gl=jp>
200 <a href=https://www.sprasia.co.jp/blog/about-katalon-studio>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:bItVmyO2LCcJ:https://www.sprasia.co.jp/blog/about-katalon-studio+&cd=9&hl=ja&ct=clnk&gl=jp>
200 <a href=https://okometsubulog.hatenablog.com/entry/computer/katalon-recorder-190826>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://webcache.googleusercontent.com/search?q=cache:62oOYgH-tTkJ:https://okometsubulog.hatenablog.com/entry/computer/katalon-recorder-190826+&cd=10&hl=ja&ct=clnk&gl=jp>
200 <a href=https://www.google.com/search?q=katalon+studio+%E6%97%A5%E6%9C%AC%E8%AA%9E&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoAHoECBYQAQ>
200 <a href=https://www.google.com/search?q=katalon+recorder+%E3%82%B3%E3%83%9E%E3%83%B3%E3%83%89%E4%B8%80%E8%A6%A7&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoAXoECBYQAg>
200 <a href=https://www.google.com/search?q=Katalon+Chrome&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoAnoECBYQAw>
200 <a href=https://www.google.com/search?q=Katalon+Studio+%E7%84%A1%E6%96%99&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoA3oECBYQBA>
200 <a href=https://www.google.com/search?q=katalon+recorder+%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoBHoECBYQBQ>
200 <a href=https://www.google.com/search?q=Katalon+user-agent&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoBXoECBYQBg>
200 <a href=https://www.google.com/search?q=Katalon+Studio+%E6%9C%89%E5%84%9F&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoBnoECBYQBw>
200 <a href=https://www.google.com/search?q=Katalon+Studio+Enterprise&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoB3oECBYQCA>
200 <a href=https://www.google.com/search?q=Katalon+Studio+license&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoCHoECBYQCQ>
200 <a href=https://www.google.com/search?q=Katalon+Selenium&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ1QIoCXoECBYQCg>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=10&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBA6>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=20&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBA8>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=30&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBA->
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=40&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBBA>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=50&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBBC>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=60&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBBE>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=70&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBBG>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=80&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBBI>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=90&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8tMDegQIGBBK>
200 <a href=https://www.google.com/search?q=katalon&ei=V664X9q7GIu6wAPG57GQBg&start=10&sa=N&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ8NMDegQIGBBM>
200 <a href=https://www.google.com/search?q=katalon&tbm=isch&source=iu&ictx=1&fir=agH-OHUAGpr6oM%252Cs1xcty8VPvH_DM%252C%252Fg%252F11g0gdvypx&vet=1&usg=AI4_-kSzcTzeJR8PP1TPHUSzRMD567VD2A&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_B16BAgPEAM#imgrc=agH-OHUAGpr6oM>
200 <a href=https://www.google.com/search?q=katalon&tbm=isch&source=iu&ictx=1&fir=Pp6NM6wsYad_JM%252C2WIkL7xlWx0bNM%252C_&vet=1&usg=AI4_-kTyNReEfY30eUAwcMzIZ502DTioXw&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_h16BAgPEAU#imgrc=Pp6NM6wsYad_JM>
200 <a href=https://www.google.com/search?q=katalon&tbm=isch&source=iu&ictx=1&fir=li-VPjPjbHhdiM%252C2WIkL7xlWx0bNM%252C_&vet=1&usg=AI4_-kRRiS5q1J0r1B46J3Gr41Q76zCrKA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_h16BAgPEAc#imgrc=li-VPjPjbHhdiM>
200 <a href=https://www.google.com/search?q=katalon&tbm=isch&source=iu&ictx=1&fir=Zz_El5lR0OzrFM%252C2WIkL7xlWx0bNM%252C_&vet=1&usg=AI4_-kQFsQF_ebSW53jA63T7ccD95KGECg&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_h16BAgPEAk#imgrc=Zz_El5lR0OzrFM>
200 <a href=https://www.google.com/search?q=katalon&tbm=isch&source=iu&ictx=1&fir=pA44ogVw6OpwNM%252C2WIkL7xlWx0bNM%252C_&vet=1&usg=AI4_-kQ1cmGEuF8F5MaxhFZRNERpZLg-Ew&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ_h16BAgPEAs#imgrc=pA44ogVw6OpwNM>
200 <a href=https://www.google.com/search?source=univ&tbm=isch&q=katalon&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQiR56BAgPEAw>
200 <a href=https://en.wikipedia.org/wiki/Katalon_Studio>
200 <a href=https://en.wikipedia.org/wiki/Katalon_Studio>
200 <a href=https://www.google.com/search?q=katalon+studio+%E3%83%97%E3%83%AD%E3%82%B0%E3%83%A9%E3%83%9F%E3%83%B3%E3%82%B0%E8%A8%80%E8%AA%9E&stick=H4sIAAAAAAAAAOPgE-LVT9c3NEw3SE8pqyyo0NLJKLfST87PyUlNLsnMz9Mvzk8rKU8sSrUqKMpPL0rMzc3MS1fIScxLL01MT13EqpWdWJKYk5-nUFxSmpKZr_C4efrj5rWPmzY8bl75uHn-4-bNQPaLFQ0vVs0DAMc_iVRsAAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQ6BMoADASegQIDRAC>
200 <a href=https://www.google.com/search?q=Groovy&stick=H4sIAAAAAAAAAOPgE-LVT9c3NEw3SE8pqyyoUOLUz9U3MMoqtjDT0skot9JPzs_JSU0uyczP0y_OTyspTyxKtSooyk8vSszNzcxLV8hJzEsvTUxPXcTK5l6Un19WuYOVEQA2eCsDVgAAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQmxMoATASegQIDRAD>
200 <a href=https://www.google.com/search?q=Katalon+Studio&stick=H4sIAAAAAAAAAONgFuLVT9c3NEw3SE8pqyyoUELlaglnJ1vpJ-fn5ubnWRVnpqSWJ1YWL2Ll804sSczJz1MILilNyczfwcoIAAOBc2RJAAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQMSgAMBN6BAgQEAE>
200 <a href=https://www.google.com/search?q=Katalon+Studio&stick=H4sIAAAAAAAAAONgFuLVT9c3NEw3SE8pqyyoUELlaglnJ1vpJ-fn5ubnWRVnpqSWJ1YWL2Ll804sSczJz1MILilNyczfwcoIAAOBc2RJAAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQzTooATATegQIEBAC>
200 <a href=https://www.google.com/search?q=Selenium&stick=H4sIAAAAAAAAAONgFuLVT9c3NEw3SE8pqyyoUOLUz9U3SLYwsijT4nPOz83NzwvOTEktT6wsXsTKEZyak5qXWZq7g5URAM9rDMc6AAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQxA0wE3oECBAQBQ>
200 <a href=https://www.google.com/search?q=appium&stick=H4sIAAAAAAAAAONgFuLVT9c3NEw3SE8pqyyoUIJwk00tTQyLqgy1-Jzzc3Pz84IzU1LLEyuLF7GyJRYUZJbm7mBlBACP5TAsPAAAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQxA0wE3oECBAQBw>
200 <a href=https://www.google.com/search?q=testcomplete&stick=H4sIAAAAAAAAAONgFuLVT9c3NEw3SE8pqyyoUOLSz9U3MKlMKzBP0uJzzs_Nzc8LzkxJLU-sLF7EylOSWlySnJ9bkJNakrqDlREAq6gCxT8AAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQxA0wE3oECBAQCQ>
200 <a href=https://www.google.com/search?q=ranorex&stick=H4sIAAAAAAAAAONgFuLVT9c3NEw3SE8pqyyoUOLSz9U3MDLPMbZI0eJzzs_Nzc8LzkxJLU-sLF7Eyl6UmJdflFqxg5URAG1WHk46AAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQxA0wE3oECBAQCw>
200 <a href=https://www.google.com/search?q=JMeter&stick=H4sIAAAAAAAAAONgFuLVT9c3NEw3SE8pqyyoUOLUz9U3MKksSM7Q4nPOz83NzwvOTEktT6wsXsTK5uWbWpJatIOVEQDvJGOxOAAAAA&sa=X&ved=2ahUKEwiaw_SR_JLtAhULHXAKHcZzDGIQxA0wE3oECBAQDQ>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://www.google.com/search?q=katalon#>
301 <a href=https://support.google.com/websearch?p=ws_settings_location&hl=ja>
301 <a href=https://support.google.com/websearch/?p=ws_results_help&hl=ja&fg=1>
200 <a href=https://www.google.com/search?q=katalon#>
200 <a href=https://policies.google.com/privacy?hl=ja&fg=1>
200 <a href=https://policies.google.com/terms?hl=ja&fg=1>
Broken/All: 0/106
```
The test finished quietly.
I found that the page https://www.google.com/search?q=katalon contains no broken links.

However, when I tried another web page which contains some broken links, the test finished with following Exception.

```
2020-11-21 15:40:13.341 ERROR com.kms.katalon.core.util.KeywordUtil    - ❌ one or more broken links are found
2020-11-21 15:40:13.349 ERROR c.k.katalon.core.main.TestCaseExecutor   - ❌ Test Cases/main FAILED.
Reason:
com.kms.katalon.core.exception.StepFailedException: one or more broken links are found
	at com.kms.katalon.core.util.KeywordUtil.markFailed(KeywordUtil.java:19)
	at com.kms.katalon.core.util.KeywordUtil$markFailed.call(Unknown Source)
	at main.run(main:21)
	at com.kms.katalon.core.main.ScriptEngine.run(ScriptEngine.java:194)
	at com.kms.katalon.core.main.ScriptEngine.runScriptAsRawText(ScriptEngine.java:119)
	at com.kms.katalon.core.main.TestCaseExecutor.runScript(TestCaseExecutor.java:339)
	at com.kms.katalon.core.main.TestCaseExecutor.doExecute(TestCaseExecutor.java:330)
	at com.kms.katalon.core.main.TestCaseExecutor.processExecutionPhase(TestCaseExecutor.java:309)
	at com.kms.katalon.core.main.TestCaseExecutor.accessMainPhase(TestCaseExecutor.java:301)
	at com.kms.katalon.core.main.TestCaseExecutor.execute(TestCaseExecutor.java:235)
	at com.kms.katalon.core.main.TestCaseMain.runTestCase(TestCaseMain.java:114)
	at com.kms.katalon.core.main.TestCaseMain.runTestCase(TestCaseMain.java:105)
	at com.kms.katalon.core.main.TestCaseMain$runTestCase$0.call(Unknown Source)
	at TempTestCase1605940726006.run(TempTestCase1605940726006.groovy:25)

```

## Conclusion

It is too tiring, too boring to check web pages for broken links if you do it manually. 
Here I proposed a solution for this years-old IT labor.
Make your testing automated!

