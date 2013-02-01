
package cz.oz.web.util;

import java.io.File;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.time.Duration;

/**
 *
 *  @author Ondrej Zizka
 */
public class FilesystemRequestHandler extends ResourceStreamRequestHandler{

    public FilesystemRequestHandler( File fullPath ) {
        
        super( new FileResourceStream( fullPath ) );
        
        //org.apache.wicket.util.file.File file = new org.apache.wicket.util.file.File(fullPath);
        setFileName(fullPath.getName()); // UrlEncoder.encode?
        setContentDisposition(ContentDisposition.INLINE);
        setCacheDuration( Duration.MAXIMUM );
    }

    @Override
    public void respond( IRequestCycle requestCycle ) {
        super.respond( requestCycle );
    }

}// class
