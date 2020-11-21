package com.kazurayam.ksbackyard

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

public class LinkTestUtils {

	static int findBrokenLinks(List<String> urls, Appendable messageBuffer) {
		Objects.requireNonNull(urls)
		Objects.requireNonNull(messageBuffer)
		int count = 0
		for (url in urls) {
			//WS.comment("${url}")
			String statusCode = LinkTestUtils.getResponseStatus(url)
			messageBuffer.append("${statusCode} <a href=${url}>${System.lineSeparator}")
			// check if this link is broken
			switch(statusCode) {
				case '200':
				case '301':
				case '302':
					break
				case '???':
					++count   // should regard this as broken? up to you.
				default:
					++count
			}
		}
		messageBuffer.append("Broken/All: ${count}/${urls.size()}${System.lineSeparator}")
		return count
	}

	/**
	 * try to make HTTP GET for this url and return the HTTP Status Code of the response
	 * @param url
	 * @return
	 */
	static String getResponseStatus(String url) {
		String status = '???'
		if (url != null && url.startsWith('http')) {
			RequestObject req = new RequestObject('check if this url is reachable')
			req.setServiceType('REST')
			req.setRestUrl(url)
			req.setRestRequestMethod('GET')
			// Send the request and get the response
			ResponseObject res = WS.sendRequest(req)
			status = res.getStatusCode()  // '200' OK, '302' redirected, '403' etc
		}
		return status
	}
}
