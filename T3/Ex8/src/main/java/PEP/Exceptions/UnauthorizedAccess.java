package PEP.Exceptions;

public class UnauthorizedAccess extends RuntimeException{

	public UnauthorizedAccess(String message) {
		super(message);
	}

	private static final long serialVersionUID = -5544866248892396049L;

}
