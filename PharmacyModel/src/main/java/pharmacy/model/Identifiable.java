package pharmacy.model;

public interface Identifiable<ID> {
    void setID(ID id);

    ID getId();
}
