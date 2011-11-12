package com.nokia.tools.jenkins.plugins;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestHandlingGetRequest {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}
	
	@Test
	public void testHandlingGetRequest() throws Exception {
		setRequestMethod("GET");
		service();
		String content = response.getContentAsString();
		assertEquals("<html>\n<body>\n<h1>XmlRpc Api Documentation here!</h1>\n</body>\n</html>",content);
	}


	@Test
	public void testSimpleMethodWithStringParam1() throws Exception {
		setRequestMethod("POST");
		String payLoad = getSimpleMethodWithStringParam("sayHello","Ismail");
		addHeadersContentAndService(payLoad);
		String content = response.getContentAsString();
		assertEquals("Hello Ismail",content);
	}
	
	@Test
	public void testSimpleMethodWithStringParam2() throws Exception {
		setRequestMethod("POST");
		String payLoad = getSimpleMethodWithStringParam("sayHello","Markus");
		addHeadersContentAndService(payLoad);
		String content = response.getContentAsString();
		assertEquals("Hello Markus",content);
	}

	private void addHeadersContentAndService(String payLoad)
			throws ServletException, IOException {
		request.addHeader("Content-Type","text/xml");
		request.addHeader("Content-Length", new Integer(payLoad.length()));
		request.setContent(payLoad.getBytes());
		service();
	}

	private String getSimpleMethodWithStringParam(String methodName, String param) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<methodCall>" +
				"<methodName>"+methodName+"</methodName>" +
				"<params>" +
				"<param><value><string>"+param+"</string></value></param>" +
				"</params>" +
				"</methodCall>";
	}
		
	private void setRequestMethod(String method) {
		request.setMethod(method);
	}
	
	private void service() throws ServletException, IOException {
		new XmlRpcControllerServlet().service(request, response);
	}
}
