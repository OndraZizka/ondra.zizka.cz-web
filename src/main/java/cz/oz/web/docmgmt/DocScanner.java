
package cz.oz.web.docmgmt;

import cz.oz.web.Settings;
import cz.oz.web.dao.TexyFileDaoBean;
import cz.oz.web.model.TexyDoc;
import cz.oz.web.qualifiers.FromApp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 *  Scans and indexes the Texy documents.
 *  @author Ondrej Zizka
 */
//@Stateless
@WebListener
public class DocScanner implements ServletContextListener {

    @Inject TexyFileDaoBean dao;
    @Inject @FromApp Settings settings;
    
    TexyDocParser parser = new TexyDocParser();
    
    //@Resource private TimerService timerService;

    
    @Override public void contextInitialized( ServletContextEvent sce ) {
        scan();
    }

    //@Schedule(minute = "*/3", hour = "*")
    void scan(){
        try {
            scan( new File(settings.getTexyFilesRootPath()) );
        } catch( IOException ex ) {
            System.out.println( "ERROR: " + ex ); // TODO: Log.
        }
    }

    void scan( File dirToScan ) throws IOException{
        new DirectoryWalker( null, new SuffixFileFilter(".texy"), -1){
            File dirToScan;

            @Override protected void handleFile( File file, int depth, Collection results ) throws IOException {
                String rel = dirToScan.toURI().relativize(file.toURI()).getPath();
                File relativePath = new File(rel);
                addDocToIndexIfNotExists( dirToScan, relativePath );
            }

            public void scan( File dirToScan ) throws IOException {
                List results = new ArrayList();
                this.dirToScan = dirToScan;
                walk( dirToScan, results );
            }
        }.scan( dirToScan );
    }

    private void addDocToIndexIfNotExists( File baseDir, File relativePath ) {
        try {
            TexyDoc texyFile = dao.findDocByPath( relativePath.getPath() );
            if( null != texyFile ){
                System.out.println( "INFO: Aready indexed: " + relativePath.getPath() );
                return;
            }

            System.out.println( "INFO: Parsing " + relativePath.getPath() );
            texyFile = parser.createTexyDoc( baseDir, relativePath );
            dao.addTexyFile( texyFile );
        }
        catch( UnsupportedOperationException ex ){
            System.err.println( "ERROR when parsing " + relativePath.getPath() + ": " + ex ); // LOG
            ex.printStackTrace( System.err );
        }
        // For input string: "d��" in /home/ondra/uw/oz.cz/web-git/pages/programovani/php/lib-docs/classc_d_b_a___error.png
        catch( NumberFormatException ex ){
            System.err.println( "ERROR when parsing " + relativePath.getPath() + ": " + ex ); // LOG
            ex.printStackTrace( System.err );
        }
        // Illegal group reference in /home/ondra/uw/oz.cz/web-git/pages/programovani/php/advantages-and-disadvantages-of-PHP.html
        catch( IllegalArgumentException ex ){
            System.err.println( "ERROR when parsing " + relativePath.getPath() + ": " + ex ); // LOG
            ex.printStackTrace( System.err );
        }
        catch( Throwable ex ){
            System.err.println( "ERROR when parsing " + relativePath.getPath() + ": " + ex ); // LOG
        }
    }


    @Override public void contextDestroyed( ServletContextEvent sce ) { }

}// class
