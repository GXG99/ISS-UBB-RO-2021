package pharmacy.services;

import pharmacy.model.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPharmacyObserver extends Remote {
    void orderFinished(Order order) throws PharmacyException, RemoteException;
}
