package it.epicode.be.common.util.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -7068045830079190922L;

	public ServiceException(String message) {
		super(message);
	}

}
