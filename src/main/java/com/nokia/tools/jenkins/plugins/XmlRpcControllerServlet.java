package com.nokia.tools.jenkins.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XmlRpcControllerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		writer.print("<html>\n<body>\n<h1>XmlRpc Api Documentation here!</h1>\n</body>\n</html>");
		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream in = req.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String requestContent = reader.readLine();
		reader.close();
		String methodName = getMethodName(requestContent);
		String[] params = getStringParameters(requestContent);
		String ret = invoke(methodName,params);
		createResponse(resp, ret);
	}

	private void createResponse(HttpServletResponse resp, String ret)
			throws IOException {
		PrintWriter writer = resp.getWriter();
		writer.print("<?xml version=\"1.0\"?><methodResponse><params><param><value><string>"+ret +"</string></value></param></params></methodResponse>");
		writer.close();
	}

	private String[] getStringParameters(String requestContent) {
		ArrayList<String> params = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<param><value><string>(\\w*)</string></value></param>");
		Matcher matcher = pattern.matcher(requestContent);
		while (matcher.find()) {
			String param = matcher.group(1);
			System.out.println("parsed parameter: " + param);
			params.add(param);
		}
		return params.toArray(new String[]{});
	}

	private String getMethodName(String requestContent) {
		Pattern pattern = Pattern.compile("<methodName>(\\w*)</methodName>");
		Matcher matcher = pattern.matcher(requestContent);
		if (matcher.find()) {
			String method = matcher.group(1);
			System.out.println("parsed method: " + method);
			return method;
		}
		return null;
	}

	private String invoke(String methodName, String[] params) {
		if (methodName.endsWith("sayHello")) {
			return "Hello " + ((params.length==1) ? params[0]:params[0] + " and "+ params[1]);
		}
		return "";
	}
}
