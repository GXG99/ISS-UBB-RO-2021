package pharmacy.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

public class Order implements Identifiable<Long>, Serializable {

    private Long id;

    private List<Long> medsIds;

    private Long doctorId;

    private Boolean isUrgent;

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
