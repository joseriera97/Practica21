import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {

    public static void main(String[] args) {

        Registry reg;

        {
            try {
                //Preparamos el Registro
                reg = LocateRegistry.createRegistry(5550);

                System.out.println("Creando el registro del Servidor...");
                //Declaramos horari
                Horari horari = new Horari("ZonesHoraries.json");
                //Pasamos el casting
                reg.rebind( "Horari", (InterficieHorari) UnicastRemoteObject.exportObject(horari,0) );

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

}
