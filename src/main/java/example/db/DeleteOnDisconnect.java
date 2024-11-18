package example.db;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@SessionScoped
public class DeleteOnDisconnect implements Serializable {
    private String sesId;

    public DeleteOnDisconnect() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        sesId = session.getId();
    }

    @PreDestroy
    public void cleanup() {
        DAOFactory.getInstance().getResultDAO().deleteResult(sesId);
    }

    public String getSessionId() {
        return sesId;
    }
}