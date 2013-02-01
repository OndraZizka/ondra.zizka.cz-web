package cz.oz.web._pg.jtexy;

import cz.oz.web.WicketJavaEEApplication;
import cz.oz.web._co.baseLayout.BaseLayoutPage;
import cz.oz.web._pg.ContentDispatchPage;
import cz.oz.web._pg.ICountablePage;
import cz.oz.web.dao.TexyFileDaoBean;
import cz.oz.web.util.FilesystemRequestHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Renders the Texy file. 
 * Either gets the content from a cache, or loads the file from filesystem and converts.
 * 
 * @author Ondrej Zizka
 *
 * TODO:  Split to TexyDocumentPanel.
 */
@SuppressWarnings("serial")
public class JTexyPage extends BaseLayoutPage implements ICountablePage {
    private static final Logger log = LoggerFactory.getLogger(JTexyPage.class);

    @Inject private transient TexyFileDaoBean dao;

    
    // ID of the page to show.
    private String reqPath;

    private Long viewCount;

    
    public JTexyPage(PageParameters params) {
        
        // Construct the path to the texy file.
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < params.getIndexedCount(); i++ ) {
            sb.append('/').append(params.get(i));
        }
        // Requested path. May be served from cache/db.
        init( sb.toString() );
    }

    public JTexyPage( String path ) {
        this.init(path);
    }

    private void init( String path ) {
        this.reqPath = path;

        if( ! path.endsWith(".texy") ){
            // Filesystem path.
            File rootPath = new File(((WicketJavaEEApplication)this.getApplication()).getSettings().getTexyFilesRootPath());
            File fullPath = new File(rootPath, reqPath);
            //getRequestCycle().scheduleRequestHandlerAfterCurrent( ContentDispatchPage.createFileReqestHandler( fullPath ) );
            getRequestCycle().scheduleRequestHandlerAfterCurrent( new FilesystemRequestHandler( fullPath ) );
            return;
        }
        
        add( new TexyDocumentPanel("document", path) );
    }


    // Counter stuff
    @Override public String getCounterId() {
        return "texyPage:" + this.reqPath;
    }
    @Override public void setCount( Long count ) { this.viewCount = count; }
    @Override public Long getCount() { return this.viewCount; }


}// class
