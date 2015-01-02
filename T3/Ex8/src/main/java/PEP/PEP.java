package PEP;

import java.util.NoSuchElementException;

import PDP.PDP;
import PDP.DataTypes.Action;
import PDP.DataTypes.User;
import PEP.Exceptions.UnauthorizedAccess;

public final class PEP {
	
	private static final PDP PDP = new PDP();
	private static final ResourceManager RESOURCE_MANAGER = new ResourceManager();
	
	public static final byte[] getResource(String userName , Action action, String resourceId){
		try {
			
			User user = PDP.getUser(userName);
			
			if (PDP.getResource(user, action, resourceId))
				return RESOURCE_MANAGER.retrieveResource(resourceId);
			
		} catch (NoSuchElementException e){
			
		}
		
		throw new UnauthorizedAccess("O acesso ao recurso : " + resourceId + " foi negado.");
	}
}
