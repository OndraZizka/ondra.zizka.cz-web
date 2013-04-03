package cz.oz.web._pg.jtexy;

import cz.oz.web.AppSettings;
import cz.oz.web.WicketJavaEEApplication;
import cz.oz.web._co.baseLayout.BaseLayoutPage;
import cz.oz.web._pg.ICountablePage;
import cz.oz.web.dao.TexyFileDaoBean;
import cz.oz.web.ex.NotFoundException;
import cz.oz.web.ex.OzczException;
import cz.oz.web.qualifiers.FromApp;
import cz.oz.web.util.FileUtil;
import cz.oz.web.util.FilesystemRequestHandler;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
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
    
    
    private static final String TEXY_FILE_SUFFIX = ".texy";
    
    @Inject private transient TexyFileDaoBean dao;
    @Inject @FromApp private transient AppSettings appSettings;

    
    // ID of the page to show.
    private String reqPath;

    private Long viewCount;
    private long MAX_TEXY_FILE_SIZE = 20*1024;

    
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
        
        // No page set -> default.
        if( StringUtils.isBlank( path ) ){
            path = this.appSettings.getTexyIndexFileName();
        }

        File texyFile;
        try {
            texyFile = getTexyFileFromRequestedPath( path );
            // If it's not a texy file, serve it as a static file.
            if( null == texyFile ){
                // Filesystem path.
                File rootPath = new File(((WicketJavaEEApplication)this.getApplication()).getSettings().getTexyFilesRootPath());
                File fullPath = new File(rootPath, reqPath);
                //getRequestCycle().scheduleRequestHandlerAfterCurrent( ContentDispatchPage.createFileReqestHandler( fullPath ) );
                getRequestCycle().scheduleRequestHandlerAfterCurrent( new FilesystemRequestHandler( fullPath ) );
                return;
            }
            add( new TexyDocumentPanel("document", path) );
        }
        catch( NotFoundException ex ){
            log.error("Not found: " + path, ex);
            add( new TexyDocumentErrorPanel("document", "Page not found", "Doesn't exist: " + path, 404));
            //((WebResponse)getRequestCycle().getResponse()).setStatus(404);
        }
        catch( OzczException ex ){
            log.error("Error rendering: " + path, ex);
            add( new TexyDocumentErrorPanel("document", "Error occured when loading document.", "Couldn't load: " + path, 500));
            //((WebResponse)getRequestCycle().getResponse()).setStatus(500);
        }

    }

    
    /**
     * Checks whether a requested texy file is inside one of root paths (Texy file repos).
     * 
     * @returns  The directory, or the File if it's a Texy file.
     *           Or null if the file exists but is not a Texy file.
     * 
     * @throws NotFoundException if file or dir doesn't exist.
     * @throws OzczException if @reqPath points outside root paths.
     * @throws OzczException if file too large 
     */
    private File getTexyFileFromRequestedPath( String reqPath ) throws OzczException {
        // Filesystem path.
        File rootPath = new File(((WicketJavaEEApplication)this.getApplication()).getSettings().getTexyFilesRootPath());
        File texyFile = new File(rootPath, reqPath);

        // Outside of parent dir?
        try {
            if( ! FileUtil.isParentOrSame(rootPath, texyFile) )
                throw new OzczException("Attempt to access non-existing file."); // Actually, "file outside of dir".
        } catch( IOException ex ) {
            throw new OzczException("Couldn't check the requested file existence.");
        }

        return getTexyFile( texyFile );
    }
    
    
    /**
     *  Returns a texy File for given path:
     *      <li> checks for existence
     *      <li> adds default index for a directory if exists
     *      <li> checks for .texy suffix.
     * 
     * @param texyFile
     * @returns null if the file is not a Texy file.
     * @throws NotFoundException if file or dir doesn't exist.
     * @throws OzczException if file too large 
     */
    private File getTexyFile( File texyFile ) throws OzczException {

        // Directory?
        if( texyFile.isDirectory() ){
            File texyFile2 = new File( texyFile, appSettings.getTexyIndexFileName() );
            if( ! texyFile2.exists() ){
                return texyFile; // Return the dir.
            }
            texyFile = texyFile2;
        }
        
        // Exists?
        if( ! texyFile.exists() ){
            throw new NotFoundException(texyFile.getPath());
        }
        
        if( texyFile.length() > MAX_TEXY_FILE_SIZE )
            throw new OzczException("File is too large: " + texyFile.getPath());
        
        // Not a .texy file?
        if( ! texyFile.getName().endsWith(TEXY_FILE_SUFFIX) ){
            return null;
        }
        
        return texyFile;
    }

     

    /** Counter stuff */
    @Override public String getCounterId() {
        return "texyPage:" + this.reqPath;
    }
    @Override public void setCount( Long count ) { this.viewCount = count; }
    @Override public Long getCount() { return this.viewCount; }


}// class
