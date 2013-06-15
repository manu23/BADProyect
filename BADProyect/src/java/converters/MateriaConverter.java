package converters;

import entities.Materia;
import controllers.MateriaFacade;
import beans.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
public class MateriaConverter implements Converter {

    @EJB
    private MateriaFacade ejbFacade;
    private static final String SEPARATOR = "#";
    private static final String SEPARATOR_ESCAPED = "\\#";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    entities.MateriaPK getKey(String value) {
        entities.MateriaPK key;
        String values[] = value.split(SEPARATOR_ESCAPED);
        key = new entities.MateriaPK();
        key.setIdpropuesta(values[0]);
        key.setCodigomateria(values[1]);
        return key;
    }

    String getStringKey(entities.MateriaPK value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value.getIdpropuesta());
        sb.append(SEPARATOR);
        sb.append(value.getCodigomateria());
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Materia) {
            Materia o = (Materia) object;
            return getStringKey(o.getMateriaPK());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Materia.class.getName()});
            return null;
        }
    }
}
