package com.liferay.portal;

/**
 * This class is a backport from Liferay 6.0.x that helps to minimize source differences in different branches of Liferay Faces.
 * 
 * @author Brian Wing Shun Chan
 */
public class CompanyMaxUsersException extends PortalException {

	// serialVersionUID
	private static final long serialVersionUID = 7558477840649876196L;

	public CompanyMaxUsersException() {
		super();
	}

	public CompanyMaxUsersException(String msg) {
		super(msg);
	}

	public CompanyMaxUsersException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CompanyMaxUsersException(Throwable cause) {
		super(cause);
	}

}
