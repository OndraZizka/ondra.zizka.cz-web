
package cz.oz.web.wicket;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ResourceReference;

/**
 *  @author Ondrej Zizka
 */
public class FavIconHeaderContributor implements IHeaderContributor
{
    private ResourceReference resRef;

    public FavIconHeaderContributor(ResourceReference ref){
        resRef = ref;
    }

    public void renderHead(IHeaderResponse response) {
        CharSequence url = RequestCycle.get().urlFor(resRef, null);
        response.render( new StringHeaderItem( this.getFavIconReference(url) ) );
    }

    private CharSequence getFavIconReference(CharSequence url)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("<link rel=\"shortcut icon\" href=\"");
        sb.append(url);
        sb.append("\" type=\"image/x-icon\">\n");
        return sb.toString();
    }
}
