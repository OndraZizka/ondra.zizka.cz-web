package cz.oz.web.util;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Ondrej Zizka, ozizka at redhat.com
 */
public class FileUtil {

    /**
     *  Checks whether a dir is a parent of child file.
     */
    public static boolean isParentOrSame( File possibleParent, File maybeChild ) throws IOException {
        final File parent = possibleParent.getCanonicalFile();
        if( ! parent.exists() || ! parent.isDirectory() ){
            return false;
        }

        File child = maybeChild.getCanonicalFile();
        while( child != null ) {
            if( child.equals(parent) )
                return true;
            child = child.getParentFile();
        }
        // No match found, and we've hit the root directory
        return false;
    }
    
}// class
