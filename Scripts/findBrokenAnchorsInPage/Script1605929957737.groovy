import org.openqa.selenium.WebElement

import com.kazurayam.ksbackyard.LinkTestUtils
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// String pageUrl is passed by the caller, or will default to the value defined in the Variables tab of this testcase

WebUI.openBrowser("")
WebUI.navigateToUrl(pageUrl)
WebUI.delay(5)   // wait until the page gets stable


// find all <a> elements with non-null href attribute
TestObject tObjAnchors =
	new TestObject("all anchors").addProperty("xpath", ConditionType.EQUALS,
			"//a")
List<WebElement> anchors = WebUI.findWebElements(tObjAnchors, 10)
List<String> hrefs = 
	anchors.stream()
    	.filter { we -> we.getAttribute('href') != null }
		.map { we -> we.getAttribute('href') }
		.collect()

// prepare buffer for messages
StringBuilder messageBuffer = new StringBuilder()
messageBuffer.append("*** All <a> elements in ${pageUrl} ***${System.lineSeparator}")

// find <a> elements with broken href
int brokenLinksCount = LinkTestUtils.findBrokenLinks(hrefs, messageBuffer)

// print message
print(messageBuffer.toString())

WebUI.closeBrowser()

return brokenLinksCount