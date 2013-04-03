package cz.oz.web.ex;

/**
 *
 * @author Ondrej Zizka, ozizka at redhat.com
 */
public class NotFoundException extends OzczException {


    public NotFoundException( String message ) {
        super( message );
    }


    public NotFoundException( String message, Throwable cause ) {
        super( message, cause );
    }


    public NotFoundException( Throwable cause ) {
        super( cause );
    }


    public NotFoundException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
    
}
