package com.nokia.tools.jenkins.plugins.handlers;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.nokia.tools.jenkins.plugins.IHudson;
import com.nokia.tools.jenkins.plugins.IProject;
import com.nokia.tools.jenkins.plugins.XmlRpcMethod;

public class VbrHandler {

	@XmlRpcMethod
	public int is_alive(String value) {
		return 1;
	}

	@XmlRpcMethod
	public Vector get_connection_statuses(Vector connections) {
		return connections;
	}

	@XmlRpcMethod
	public String get_disk_space() {
		return null;
	}

	@XmlRpcMethod
	public String get_server_version() {
		return null;
	}

	@XmlRpcMethod
	public int submit_job(Hashtable input_params, String cpfs) {
		return 0;
	}

	@XmlRpcMethod
	public void restart_job(int job_id, Hashtable attrs) {
	}

	@XmlRpcMethod
	public int cancel_job(int jid) {
		return 0;
	}

	@XmlRpcMethod
	public Hashtable get_job(int job_id) {
		List<IProject> projects = getHudson().getProjects();
		return new Hashtable();
	}
	
	protected IHudson getHudson() {
		return null;
	}
}
