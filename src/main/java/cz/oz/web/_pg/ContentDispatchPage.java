
package cz.oz.web._pg;

import cz.oz.web.WicketJavaEEApplication;
import cz.oz.web._pg.jtexy.JTexyPage;
import cz.oz.web.util.FilesystemRequestHandler;
import java.io.File;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  @deprecated  Use IRequestMapper and FileSystemRequestHandler.
 *  @author Ondrej Zizka
 */
public class ContentDispatchPage extends Page {
    private static final Logger log = LoggerFactory.getLogger(ContentDispatchPage.class);

    // ID of the page to show.
    private String reqPath;

    
    public ContentDispatchPage(PageParameters params) {

        // Construct the path to the texy file.
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < params.getIndexedCount(); i++ ) {
            sb.append('/').append(params.get(i));
        }
        // Requested path. May be served from cache/db.
        init( sb.toString(), params );
    }

    public ContentDispatchPage( String path ) {
        this.init(path, null);
    }

    private void init( String path, PageParameters params ) {
        this.reqPath = path;

        // Only show .texy files. For others, simply show them. (Images etc.)
        File file = new File(path);

        // For .texy files.
        if( file.getName().endsWith(".texy") ){
            throw new RestartResponseAtInterceptPageException( JTexyPage.class, params );
        }


        // For others.

        // Filesystem path.
        File rootPath = new File(((WicketJavaEEApplication)this.getApplication()).getSettings().getTexyFilesRootPath());
        File fullPath = new File(rootPath, reqPath);
        
        // Process request using this handler.
        getRequestCycle().scheduleRequestHandlerAfterCurrent( new FilesystemRequestHandler( fullPath ) );

    }// init()

    
}// class
