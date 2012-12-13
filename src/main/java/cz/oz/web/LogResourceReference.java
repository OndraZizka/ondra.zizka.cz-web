
package cz.oz.web;

import java.util.Locale;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.PackageResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 *
 *  @author Ondrej Zizka
 */
class LogResourceReference extends ResourceReference {

    public LogResourceReference() {
        super(LogResourceReference.class, "viewLog");
    }

    @Override
    public IResource getResource() {
        //return new ByteArrayResource("image/gif", new byte[0] );
        return new PackageResource( LogResourceReference.class, "hit.gif", Locale.US, "foo", "bar"){
            /* Just needs to be a subclass... */
        };
    }
}
