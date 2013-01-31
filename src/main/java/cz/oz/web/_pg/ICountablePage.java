package cz.oz.web._pg;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.wicket.Page;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 *
 * For pages which aren't identified by their class but rather their model.
 *
 * @author ondra
 */
public interface ICountablePage {

    public String getCounterId();

    public void setCount( Long count );

    public Long getCount();

    

    /**
     *  Model for the counter, giving number of views for given page.
     *  @see ICountablePage
     */
    static class SeenCountModel extends AbstractReadOnlyModel<String> {

        @Inject private EntityManager em;

        private Page page;


        private SeenCountModel( Page page ) {
            this.page = page;
        }

        @Override
        public String getObject() {

            String pageCounterId = (page instanceof ICountablePage)
                    ? ((ICountablePage)page).getCounterId()
                    : page.getClass().getName();

            page.getClass();
            Integer count = (Integer) em.createNativeQuery("SELECT count FROM counter WHERE id=:id")
                    .setParameter("id", pageCounterId)
                    .getSingleResult();

            return count.toString();
        }

    }// SeenCountModel

}// interface
