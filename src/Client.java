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

        //Para que se repita las veces necesarias
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
                    //Si la ciudad no esta ...
                    if (horari.donaHora(ciutat.toLowerCase()).equals("false")
                    ) {
                        System.out.println("Ciudad no existente . . .");
                        System.out.println("Desea introducir una nueva ciudad ? Si/No");
                        String confirmar = scan.nextLine();
                        //Para gestionar si se quiere añadir o no
                        switch (confirmar.toLowerCase()) {
                            //En el caso de que se quiera añadir una nueva ciudad
                            case "si":
                                //Solicitamos el nombre
                                System.out.println("Nombre de la ciudad Nueva?");
                                String nombreCiudad = scan.nextLine();
                                //Solicitamos la fecha
                                System.out.println("Introducir fecha dd/mm/yyyy ");
                                String fecha = scan.nextLine();
                                //La hora actual de la ciudad
                                System.out.println("Introducir hora hh:mm");
                                hora = scan.nextLine();
                                //Ahora se intenta añadir la nueva ciudad
                                if (horari.novaCiutat(nombreCiudad, fecha + " " + hora)) {
                                    //Si se ha podido añadir
                                    System.out.println("\nCiudad añadida....");
                                    //Mostramos el nombre de la ciudad
                                    System.out.println(nombreCiudad.toLowerCase());
                                    //Y la hora
                                    System.out.println(horari.donaHora(nombreCiudad) + "\n");
                                } else {
                                    //Caso de no poder introducir.
                                    System.out.println("No se ha podido introducir la nueva ciudad");
                                }//Fin de introducir la ciudad.
                                break;

                            case "no":
                                //Si no se quiere introducir la ciudad.
                                break;
                            default:
                                //Caso si no se cumplen las condiciones.
                                System.out.println("Introduzca o SI o NO");
                        }
                    } else {
                        //Obtener la hora de la población deseada.
                        System.out.println(horari.donaHora(ciutat.toLowerCase()) + "\n");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
