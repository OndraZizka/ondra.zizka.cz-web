
package cz.oz.web;

import cz.oz.web.ex.OzczException;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *  App's settings.
 * 
 *  @author Ondrej Zizka
 */
public class AppSettings implements Serializable {
    
    private static final String DEFAULT_INDEX_TEXY = "index.texy";

    private transient String texyFilesRootPaths;
    private transient String texyIndexFileName;


    public AppSettings() throws OzczException {
        try {
            // Get the value from web.xml's <env-entry>.

            Context env = (Context)new InitialContext().lookup("java:comp/env");
            texyFilesRootPaths = (String)env.lookup("texyFilesRootPaths");
            // TODO: Split by whitespace.
            try {
                texyIndexFileName = (String)env.lookup("texyIndexFileName");
            } catch( NamingException ex ) {
                texyIndexFileName = DEFAULT_INDEX_TEXY;
            }
        } catch( NamingException ex ) {
            throw new OzczException("JNDI query failed", ex);
        }
    }
    
    void setTexyFilesRootPaths( String paths ) { this.texyFilesRootPaths = paths; }
    public String getTexyFilesRootPath() { return texyFilesRootPaths; }

    public String getTexyIndexFileName() { return texyIndexFileName; } 
    void setTexyIndexFileName( String texyIndexFileName ) { this.texyIndexFileName = texyIndexFileName; }
    
    public String getTexyFilesRootPaths() throws NamingException {
        return texyFilesRootPaths;
    }

} // class
