package cz.oz.web.dao;

import cz.oz.web.model.TexyFile;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * A bean which manages Contact entities.
 */
@Stateless
public class TexyFileDaoBean {

    @PersistenceContext
    private EntityManager em;


    @SuppressWarnings("unchecked")
    public List<TexyFile> getContacts() {
        return em.createQuery("SELECT f FROM TexyFile f").getResultList();
    }

    /**
     * Get Contact by ID.
     */
    public TexyFile getContact(Long id) {
        return em.find(TexyFile.class, id);
    }

    /**
     * Add a new Contact.
     */
    public void addFile(String path) {
        em.merge(new TexyFile(path));
    }

    /**
     * Remove a Contact.
     */
    public void remove(TexyFile modelObject) {
        TexyFile managed = em.merge(modelObject);
        em.remove(managed);
        em.flush();
    }
    
}
