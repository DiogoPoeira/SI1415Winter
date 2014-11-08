package HTTPS;

public class ServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8189358574144853155L;

	public ServerException(){
		super();
	}
	
	public ServerException(String message){
		super(message);
	}
	
	public ServerException(String message,Exception cause){
		super(message,cause);
	}
	

}
