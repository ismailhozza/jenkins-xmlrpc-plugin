package org.jenkins.plugins.xmlrpcplugin.handlers;

import hudson.model.Hudson;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JenkinsXmlRpcApiHandler {

    private final Log log = LogFactory.getLog(getClass());

    public String getJenkinsVersion() {
    	return Hudson.getVersion().toString();
	}
    
    public ArrayList<String> getProjects() {
        return null;
    }
    
    public boolean projectExists(String projectName) {
        return false;
    }
    
    public void buildProject(String projectName) {
        
    }
    
    
}
