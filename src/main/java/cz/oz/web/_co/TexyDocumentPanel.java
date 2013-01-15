package cz.oz.web._co;

import cz.dynawest.jtexy.JTexy;
import cz.dynawest.jtexy.TexyException;
import cz.oz.web.WicketJavaEEApplication;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import res.ResourcesPackageMarker;

/**
 *
 *  @author Ondrej Zizka
 */
public class TexyDocumentPanel extends Panel {

    private transient Charset encoding = Charset.forName("utf-8");

    
    public TexyDocumentPanel( String id, String reqPath ) {
        super( id, new Model<String>(reqPath) );

        // Filesystem path.
        File rootPath = new File(((WicketJavaEEApplication)this.getApplication()).getSettings().getTexyFilesRootPath());
        File texyFile = new File(rootPath, reqPath);

        // Exists?
        if( ! texyFile.exists() ){
            add( new Label("substituteTitle", "Page not found."));
            add( new Label("body", "Doesn't exist: " + texyFile.getPath() ));
        }
        else {
            String title = guessTitle(texyFile.getName());
            String body = "";
            boolean success = false;

            // If given path points to a dir, try "index.texy".
            // TODO: This all should be done at the page level, not in the component.
            //       The component should receive Texy String.
            if( texyFile.isDirectory() ){
                File f = new File(texyFile, "index.texy");
                if( f.isFile() )
                    texyFile = f;
                else {
                    title = "No directory index.";
                    body = "This URL leads to a directory, but there's no index page.";
                    texyFile = null;
                    // TODO: Redirect to a dir listing?
                }
            }

            if( texyFile != null )
            try {
                // Read.
                String src = FileUtils.readFileToString(texyFile, this.encoding);
                // Convert.
                String html = JTexy.create().process(src);
                // Show.
                add( new Label("body", html).setEscapeModelStrings(false));
            } catch (IOException ex) {
                body = "Error reading " + texyFile.getPath() + ": " + ex.toString();
                getWebResponse().setStatus(404);
                // TODO log
            } catch( TexyException ex ){
                body = "Error rendering " + texyFile.getPath() + ": " + ex.toString();
                getWebResponse().setStatus(500);
                // TODO log
            }

            // Add title if absent in document.
            add( new Label("substituteTitle", title).setVisibilityAllowed(!success) );
            add( new Label("body",            body ).setEscapeModelStrings(!success));
        }
    }// const

    
    @Override
    public void renderHead( IHeaderResponse response ) {
        // Syntax Highlighter.
        response.render( new CssReferenceHeaderItem(
            new CssResourceReference( ResourcesPackageMarker.class, "js/syntaxhighlighter/styles/shThemeDefault.css"), null, null, null));
        response.render( new JavaScriptReferenceHeaderItem(
            new JavaScriptResourceReference( ResourcesPackageMarker.class, "js/syntaxhighlighter/scripts/shCore.js"), null, "shCore", true, null, null));
        response.render( new JavaScriptReferenceHeaderItem(
            new JavaScriptResourceReference( ResourcesPackageMarker.class, "js/syntaxhighlighter/scripts/shAutoloader.js"), null, "shAutoloader", true, null, null));
    }


    /**
     *  foo-bar-baz.texy --> Foo bar baz.
     */
    public static String guessTitle( String name ) {
        return StringUtils.capitalize( name ).replaceAll("-", " ");
    }

}// class TexyDocumentPanel
