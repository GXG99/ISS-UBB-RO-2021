package pharmacy.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orders", schema = "public")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Order implements Identifiable<Long>, Serializable {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "list-array")
    @Column(
            name = "meds_ids",
            columnDefinition = "bigint[]"
    )
    private List<Long> medsIds;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "is_urgent")
    private Boolean isUrgent;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    public Order() {
    }

    public Order(List<Long> medsIds, Long doctorId) {
        this.medsIds = medsIds;
        this.doctorId = doctorId;
        this.isUrgent = false;
        this.isCompleted = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMedsIds() {
        return medsIds;
    }

    public void setMedsIds(List<Long> medsIds) {
        this.medsIds = medsIds;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Boolean getUrgent() {
        return isUrgent;
    }

    public void setUrgent(Boolean urgent) {
        isUrgent = urgent;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", medsIds=" + medsIds +
                ", doctorId=" + doctorId +
                ", isUrgent=" + isUrgent +
                '}';
    }
}
