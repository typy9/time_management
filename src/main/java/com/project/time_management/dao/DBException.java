package com.project.time_management.dao;

public class DBException extends Exception {
	public DBException() {
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(Throwable cause) {
		super(cause);
	}
}
