package cz.oz.web.pages;

import cz.oz.web.dao.ContactDao;
import cz.oz.web.model.Contact;
import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


/**
 * Dynamic behavior for the ListContact page
 * 
 * @author Filippo Diotalevi
 */
@SuppressWarnings("serial")
public class ListContacts extends WebPage {

    // Inject the ContactDao using @Inject
    @Inject
    private ContactDao contactDao;

    @Resource(name = "welcomeMessage")
    private String welcome;

    // Set up the dynamic behavior for the page, widgets bound by id
    public ListContacts() {

        // Add the dynamic welcome message, specified in web.xml
        add(new Label("welcomeMessage", welcome));
        add(new ListView<Contact>("contacts", contactDao.getContacts()) {

            // Populate the table of contacts
            @Override
            protected void populateItem(final ListItem<Contact> item) {
                Contact contact = item.getModelObject();
                item.add(new Label("name", contact.getName()));
                item.add(new Label("email", contact.getEmail()));
                item.add(new Link<Contact>("delete", item.getModel()) {

                    @Override
                    public void onClick() {
                        contactDao.remove(item.getModelObject());
                        setResponsePage(ListContacts.class);
                    }
                });
            }
        });
    }

}
