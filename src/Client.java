import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        InterficieHorari horari = null;
        Scanner scan = new Scanner(System.in);
        String ip, ciutat, hora;

        //Para que se recpita las veces necesarias
        while (true) {

            System.out.println("Indicaque la IP del Servidor: ");
            //Obtener y comprobar la IP
            ip = scan.nextLine();

            System.out.println();

            try {
                //Intentamos acceder
                Registry registro = LocateRegistry.getRegistry(ip, 5550);

                horari = (InterficieHorari) registro.lookup("Horari");

                break;

            } catch (NotBoundException e) {

                System.err.println("Quin error es? " + e);

            } catch (RemoteException e) {

                System.err.println("ERROR: Dirección IP " + ip + " Time Out");

            }

        }
        //Si se ha podido acceder al Server
        while (true) {
            //Solicitamos el nombre de la población
            System.out.println("Introdueixi el nom de la poblacio (adeu per sortir)");
            ciutat = scan.nextLine();
            System.out.println();
            //Pasamos a minuscula si se quiere salir
            if (ciutat.toLowerCase().equals("adeu")) {
                break;
            } else {
                //Si se quiere quedar
                //
                try {
                    //Mostramos la población
                    System.out.println(ciutat.toLowerCase());
                    //Obtener la hora de la población deseada.
                    System.out.println(horari.donaHora(ciutat.toLowerCase()) + "\n");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


        }

    }
}
