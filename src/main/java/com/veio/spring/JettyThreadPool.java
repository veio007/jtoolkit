package com.veio.spring;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.softee.management.annotation.Description;
import org.softee.management.annotation.ManagedAttribute;
import org.softee.management.helper.MBeanRegistration;

import javax.management.ObjectName;
import java.util.concurrent.LinkedBlockingQueue;

public class JettyThreadPool extends QueuedThreadPool{
	private LinkedBlockingQueue<Runnable> _jobs;
	private MBeanRegistration registration;
	private int _maxQueued;
	public JettyThreadPool(int minThreads, int maxThreads, int maxIdleTime, LinkedBlockingQueue<Runnable> _jobs, int maxQueued){
		super(minThreads, maxThreads, maxIdleTime,_jobs);
		this._maxQueued = maxQueued;
		this._jobs = _jobs;
		try {
			registration = new MBeanRegistration(this, new ObjectName("com.gexin.platform.open.os:name=JettyThreadPool"));
			registration.register();
		} catch (Exception e) {
		}
	}
	
	@ManagedAttribute
	@Description("Idle Threads")
	public int getJettyIdleThreads() {
		return getIdleThreads();
	}
	
	@ManagedAttribute
	@Description("threads currently")
	public int getJettyThreads() {
		return getThreads();
	}
	
	@ManagedAttribute
	@Description("threads max")
	public int getJettyThreadsMaximum() {
		return getMaxThreads();
	}
	
	@ManagedAttribute
	@Description("queue size")
	public int getJettyQueueSize() {
		return _jobs.size();
	}
	
	@ManagedAttribute
	@Description("queue size maximum")
	public int getJettyMaxQueueSize() {
		return _maxQueued;
	}
}
