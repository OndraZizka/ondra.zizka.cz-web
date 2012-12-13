
package cz.oz.web;

/**
 *  App's settings.
 * 
 *  @author Ondrej Zizka
 */
public class Settings {
    String texyFilesRootPath;

    public String getTexyFilesRootPath() {
        return texyFilesRootPath;
    }

    /**
     *  TODO: Split by whitespace.
     */
    void setTexyFilesRootPaths( String paths ) {
        this.texyFilesRootPath = paths;
    }

} // class
