package com.sun.jdi;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import java.util.List;

public interface ThreadReference extends ObjectReference {
	public static final int THREAD_STATUS_UNKNOWN = -1;
	public static final int THREAD_STATUS_ZOMBIE = 0;
	public static final int THREAD_STATUS_RUNNING = 1;
	public static final int THREAD_STATUS_SLEEPING = 2;
	public static final int THREAD_STATUS_MONITOR = 3;
	public static final int THREAD_STATUS_WAIT = 4;
	public static final int THREAD_STATUS_NOT_STARTED = 5;
	public ObjectReference currentContendedMonitor() throws IncompatibleThreadStateException;
	public StackFrame frame(int arg1) throws IncompatibleThreadStateException;
	public int frameCount() throws IncompatibleThreadStateException;
	public List frames() throws IncompatibleThreadStateException;
	public List frames(int arg1, int arg2) throws IncompatibleThreadStateException;
	public void interrupt();
	public boolean isAtBreakpoint();
	public boolean isSuspended();
	public String name();
	public List ownedMonitors() throws IncompatibleThreadStateException;
	public void resume();
	public int status();
	public void stop(ObjectReference arg1) throws InvalidTypeException;
	public void suspend();
	public int suspendCount();
	public ThreadGroupReference threadGroup();
	public void popFrames(StackFrame frame) throws IncompatibleThreadStateException;
}
