package cz.oz.web.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Counts the occurrences of given exception.
 *  Uses messages hashes and first 3 stack elements class name hashes to determine "same" exception.
 * 
 *  @author Ondrej Zizka, ozizka at redhat.com
 */
public class RepeatedExceptionsDetector {
    private static final Logger log = LoggerFactory.getLogger( RepeatedExceptionsDetector.class );

    private final Map<String, Integer> exCount = new ConcurrentHashMap();
    
    private static final int STACKTRACE_SCAN_DEPTH = 3;
    
    /**
     *  Counts how many times given exception was seen.
     */
    public int countException( Exception ex ){
        String chara = buildCharacteristicsString( ex );
        if( log.isDebugEnabled() )  log.debug("Characteristic: " + chara);
        
        Integer count = this.exCount.get( chara );
        if( count == null )
            count = new Integer(1);
        else
            count++;
        this.exCount.put( chara, count );
        return count;
    }


    /**
     *  Builds a string which can be considered as a hash of given exception.
     */
    private static String buildCharacteristicsString( Exception ex ) {
        StringBuilder sb = new StringBuilder();
        
        Throwable curEx = ex;
        do{
            sb.append( Integer.toHexString( curEx.getMessage().hashCode() ) );
            StackTraceElement[] stackTrace = curEx.getStackTrace();
            // For each stack trace element, append "cFooClass@72".
            for( int i = 0; i < stackTrace.length & i <= STACKTRACE_SCAN_DEPTH; i++ ) {
                StackTraceElement ste = stackTrace[i];
                sb.append('{');
                sb.append( Integer.toHexString( ste.getClassName().hashCode() ) );
                sb.append('@');
                sb.append( ste.getLineNumber() );
            }
            sb.append("\n");
            curEx = curEx.getCause();
        }while( curEx != null );
        
        return sb.toString();
    }
    
}// class
