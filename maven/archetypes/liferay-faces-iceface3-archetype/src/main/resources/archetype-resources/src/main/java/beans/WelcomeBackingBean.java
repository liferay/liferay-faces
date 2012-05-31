#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#set( $symbol_open_curly = '{' )
#set( $symbol_close_curly = '}' )
/**
 * 
 */
package ${package}.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import ${package}.model.WelcomeModelBean;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.portal.context.LiferayFacesContext;

/**
 * 
 * @author $symbol_dollar$symbol_open_curlyusersymbol_close_curly
 *
 */
@ManagedBean(name = "welcomeBackingBean")
@RequestScoped
public class WelcomeBackingBean implements Serializable {

    private static final long serialVersionUID = -8998795728731794592L;

    // Logger
    private static final transient Logger logger = LoggerFactory
            .getLogger(WelcomeBackingBean.class);

    @ManagedProperty(name = "welcomeModelBean", value = "${symbol_pound}{welcomeModelBean}")
    private WelcomeModelBean welcomeModelBean;

    // Self Injection
    private LiferayFacesContext liferayFacesContext = LiferayFacesContext
            .getInstance();

    public void submit(ActionEvent actionEvent) {
        String firstName = welcomeModelBean.getFirstName();
        String lastName = welcomeModelBean.getLastName();

        long creatorUserId = liferayFacesContext.getUser().getUserId();
        long companyId = liferayFacesContext.getCompanyId();

        logger.debug("Company Id:" + companyId + " User Id:" + creatorUserId);

        logger.debug("First Name:" + firstName + " last name:" + lastName);
        String key = "welcome-message";
        String userName = firstName + " " + lastName;
        liferayFacesContext.addGlobalInfoMessage(key, new Object[]{userName});
    }

    /**
     * @param welcomeModelBean
     *            the welcomeModelBean to set
     */
    public void setWelcomeModelBean(WelcomeModelBean welcomeModelBean) {
        this.welcomeModelBean = welcomeModelBean;
    }

}
