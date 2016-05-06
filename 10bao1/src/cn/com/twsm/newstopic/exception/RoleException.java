package cn.com.twsm.newstopic.exception;

public class RoleException extends CommonException{
	private static final long serialVersionUID = 1L;

	public RoleException() {
		super();
	}

	public RoleException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RoleException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleException(String message) {
		super(message);
	}

	public RoleException(Throwable cause) {
		super(cause);
	}
	
}
