
package cz.oz.web.security;

import cz.oz.web.dao.UserDaoBean;
import cz.oz.web.model.User;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;


/**
 * wicket-auth-roles -based session auth.
 * 
 * @author ozizka@redhat.com
 */
public class OzCzAuthSession extends AuthenticatedWebSession {
    
    @Inject private UserDaoBean userDao;
    
    
    private User user;
    
    //private Settings settings = new Settings();
    
    

    public OzCzAuthSession( Request request ) {
        super( request );
    }

    
    @Override
    public void signOut() {
        user = null;
        super.signOut();
    }

    
    @Override
    public boolean authenticate( String name, String pass ) {
        if( this.user != null )  return true;
        return authenticate( new User( name, pass ) );
    }

    public boolean authenticate( User user_) {
        if( this.user != null )  return true;

        try {
            this.user = userDao.loadUserIfPasswordMatches( user_ );
            return true;
        } catch (NoResultException ex){
            return false;
        }
    }
    
    
    

    @Override
    public Roles getRoles() {
        if( ! isSignedIn() )  return null;
        
        // If the user is signed in, they have these roles
        return new Roles( Roles.ADMIN );
    }

    //public Settings getSettings() { return settings; }
    
    public User getUser() { return user; }
    public void setUser( User user ) { this.user = user; }

}// class
