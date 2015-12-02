package com.hdhelper.client.frame.components.plugin;

public class Plugin {

	private String name;
	
	private boolean enabled;
	
	private Object configure, info, extra;
	
	public Plugin(String name, boolean enabled, Object configure, Object info, Object extra) {
		this.name 	   = name;
		this.enabled   = enabled;
		this.configure = configure;
		this.info 	   = info;
		this.extra     = extra;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Object getConfigure() {
		return configure;
	}
	
	public void setConfigure(Object configure) {
		this.configure = configure;
	}
	
	public Object getInfo() {
		return info;
	}
	
	public void setInfo(Object info) {
		this.info = info;
	}
	
	public Object getExtra() {
		return extra;
	}
	
	public void setExtra(Object extra) {
		this.extra = extra;
	}
	
}
