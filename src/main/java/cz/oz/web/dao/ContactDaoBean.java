package cz.oz.web.dao;

import cz.oz.web.model.Contact;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * A bean which manages Contact entities.
 */
@Stateless
public class ContactDaoBean implements ContactDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    @SuppressWarnings("unchecked")
    public List<Contact> getContacts() {
        return em.createQuery("SELECT c FROM Contact c").getResultList();
    }

    /**
     * Get Contact by ID.
     */
    @Override
    public Contact getContact(Long id) {
        return em.find(Contact.class, id);
    }

    /**
     * Add a new Contact.
     */
    @Override
    public void addContact(String name, String email) {
        em.merge(new Contact(null, name, email));
    }

    /**
     * Remove a Contact.
     */
    @Override
    public void remove(Contact modelObject) {
        Contact managed = em.merge(modelObject);
        em.remove(managed);
        em.flush();
    }
    
}
