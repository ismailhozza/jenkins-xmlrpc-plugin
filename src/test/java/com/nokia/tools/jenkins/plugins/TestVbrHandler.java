package com.nokia.tools.jenkins.plugins;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.nokia.tools.jenkins.plugins.handlers.VbrHandler;

public class TestVbrHandler {
	private VbrHandler vbrHandler;
	private List<String> xmlRpcMethodNames = new ArrayList<String>();
	private IHudson hudsonMock;
	
	@Before
	public void setUp() throws Exception {
		hudsonMock = createMock(IHudson.class);
		vbrHandler = new VbrHandler() {
			protected IHudson getHudson() {
				return hudsonMock;
			}
		};
		xmlRpcMethodNames.add("is_alive");
		xmlRpcMethodNames.add("get_connection_statuses");
		xmlRpcMethodNames.add("get_disk_space");
		xmlRpcMethodNames.add("get_server_version");
		xmlRpcMethodNames.add("submit_job");
		xmlRpcMethodNames.add("restart_job");
		xmlRpcMethodNames.add("cancel_job");
		xmlRpcMethodNames.add("get_job");
		
	}
	
	@Test
	public void verifyXmlRpcMethods() {
		Class vbrHandlerClass = VbrHandler.class;
		Method[] allMethods = vbrHandlerClass.getMethods();
		ArrayList<String> xmlRpcMethods = new ArrayList<String>();
		for (Method method : allMethods) {
			if (method.isAnnotationPresent(XmlRpcMethod.class)) {
				xmlRpcMethods.add(method.getName());
			}
		}
		for (String methodName : xmlRpcMethodNames) {
			assertTrue(String.format("Expected XML_RPC method %s not found.", methodName),xmlRpcMethods.contains(methodName));
		}
	}
	
	@Test
	public void testIsAlive() throws Exception {
		assertEquals(1, vbrHandler.is_alive("foo"));
	}

	@Test
	public void testGetConnectionStatuses() throws Exception {
		assertEquals(null, vbrHandler.get_connection_statuses(null));
	}
	
	@Test
	public void testGetJob() throws Exception {
		int jobId = 100;
		IProject p = new IProject() {
			
		};
		List<IProject> projects = new ArrayList<IProject>();
		projects.add(p);
		expect(hudsonMock.getProjects()).andReturn(projects);
		replay(hudsonMock);
		assertNotNull(vbrHandler.get_job(jobId));
		verify(hudsonMock);
	}
	
}
