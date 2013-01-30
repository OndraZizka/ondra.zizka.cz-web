
package cz.oz.web._pg.doclist;

import cz.oz.web._co.baseLayout.BaseLayoutPage;
import cz.oz.web.dao.TexyFileDaoBean;
import cz.oz.web.model.TexyDoc;
import java.util.List;
import javax.inject.Inject;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *  List of Texy docs.
 * 
 *  @author Ondrej Zizka
 */
public class DocListPage extends BaseLayoutPage {

    @Inject TexyFileDaoBean dao;

    public DocListPage( PageParameters parameters ) {

        // LDM to load latest 30 docs.
        IModel<List<TexyDoc>> lm = new LoadableDetachableModel<List<TexyDoc>>() {
            @Override
            protected List<TexyDoc> load() {
                return dao.getLatestDocs(30);
            }
        };

        // ListView
        add( new ListView<TexyDoc>("docs", lm){
            @Override
            protected void populateItem( ListItem<TexyDoc> item ) {
                item.add( new TexyDocStripePanel("docStripe", item.getModel()) );
            }
        });
    }

}
