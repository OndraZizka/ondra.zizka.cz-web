package cz.oz.web.ex;

/**
 *
 * @author Ondrej Zizka, ozizka at redhat.com
 */
public class OzczException extends Exception {

    public OzczException( String message ) {
        super( message );
    }


    public OzczException( String message, Throwable cause ) {
        super( message, cause );
    }


    public OzczException( Throwable cause ) {
        super( cause );
    }


    public OzczException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
    
}// class
