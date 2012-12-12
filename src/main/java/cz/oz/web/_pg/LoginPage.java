package cz.oz.web._pg;

import cz.oz.web._co.baseLayout.BaseLayoutPage;
import cz.oz.web.model.User;
import javax.persistence.NoResultException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * @author Ondrej Zizka
 */
@SuppressWarnings("serial")
public class LoginPage extends BaseLayoutPage {

    // Components
    private Form<User> form;
    private FeedbackPanel feedback = (FeedbackPanel) new FeedbackPanel("feedback").setOutputMarkupId(true);
    private Button lostButton;

    // Data
    private User user = new User();
    

    
    public LoginPage(PageParameters params) {
        
        add(this.feedback);

        this.form = new Form<User>("form") {
            @Override protected void onSubmit() {
            }
        };

        // User, pass
        this.form.add(new RequiredTextField("user", new PropertyModel(this.user, "name")));
        this.form.add(new PasswordTextField("pass", new PropertyModel(this.user, "pass")));
        
        // Register button
        final AjaxButton loginBtn = new AjaxButton("login") {
            @Override
            protected void onSubmit( AjaxRequestTarget target, Form<?> form ) {
                target.add( feedback );
                //checkLoginWithPicketBox();
                try {
                    //User user_ = userDao.loadUserIfPasswordMatches( user );
                    //if( !  LoginPage.this.getSession().authenticate( user ) )
                    if( !  LoginPage.this.getSession().signIn( user.getName(), user.getPass() ) )
                        throw new NoResultException("No such user.");
                    setResponsePage(JTexyPage.class);
                }
                catch( NoResultException ex ){
                    //setResponsePage(HomePage.class);
                    feedback.error("Wrong password or non-existent user: " + user.getName() + " / " + user.getPass());
                    feedback.info( "To get forgotten password, fill in user name and/or email.");
                    lostButton.setVisible( true );
                }
            }
        };
        this.form.add( loginBtn );

        add(this.form);
    }


    public User getUser() { return user; }
    public void setUser( User user ) { this.user = user; }

    
}// class
