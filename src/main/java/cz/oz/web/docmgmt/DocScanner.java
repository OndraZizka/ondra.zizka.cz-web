
package cz.oz.web.docmgmt;

import cz.oz.web.dao.TexyFileDaoBean;
import cz.oz.web.model.TexyDoc;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 *  Scans and indexes the Texy documents.
 *  @author Ondrej Zizka
 */
@Stateless
@WebListener
public class DocScanner implements ServletContextListener {

    @Inject TexyFileDaoBean dao;
    
    TexyDocParser parser = new TexyDocParser();
    
    //@Resource private TimerService timerService;


    @Schedule(minute = "*/10", hour = "*")
    void scan(){
        new DirectoryWalker( TrueFileFilter.INSTANCE, new SuffixFileFilter(".texy"), 0){
            @Override
            protected void handleFile( File file, int depth, Collection results ) throws IOException {
                addDocToIndexIfNotExists( file );
            }
        };
    }

    private void addDocToIndexIfNotExists( File file ) {
        
        TexyDoc texyFile = dao.findDocByPath( file.getPath() );
        if( null != texyFile )
            return;

        texyFile = parser.createTexyDoc( file );
        dao.addTexyFile( texyFile );
    }

    

    @Override
    public void contextInitialized( ServletContextEvent sce ) {
        scan();
    }

    @Override
    public void contextDestroyed( ServletContextEvent sce ) {
    }

}// class
