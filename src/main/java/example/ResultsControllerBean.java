package example;

import example.db.DAOFactory;
import example.entity.ResultEntity;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("resultsControllerBean")
@ApplicationScoped
@ManagedBean
public class ResultsControllerBean implements Serializable {
    private String y = "0.0";
    private String r;

    private String hiddenY;

    @Inject
    private PointCheckerBean pointCheckerBean;

    /*
    бин, в котором инжектится второй, в котором инжектится первый
     */

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getHiddenY() {
        return hiddenY;
    }

    public void setHiddenY(String y) {
        this.hiddenY = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public List<ResultEntity> getResults() throws IOException {
        System.out.println("QWEQWEQWE");
        try {
            return new ArrayList<>(DAOFactory.getInstance().getResultDAO().getAllResults());
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
        }
        return new ArrayList<>();
    }

    public void checkPoint(String xStr, String yStr, String rStr) throws IOException {
        System.out.println("QWEQWEQWE1");
        try {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            double r = Double.parseDouble(rStr);
            boolean checkP = pointCheckerBean.checker(x, y, r);
            if (checkP) {
                boolean isValid = pointCheckerBean.validatePoint(x, y, r);
                DAOFactory.getInstance().getResultDAO().addNewResult(new ResultEntity(x, y, r, isValid));
            } else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid point data");
                facesContext.responseComplete();
            }
        } catch (NumberFormatException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid point data");
            facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            facesContext.responseComplete();
        }
    }
}