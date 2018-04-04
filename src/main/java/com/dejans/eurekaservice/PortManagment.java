package com.dejans.eurekaservice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


class PortManagment {

    class Client {
        String clientName;
        int pid;
        int port;

        Client(String clientName, int port, int pid) {
            this.clientName = clientName;
            this.port = port;
            this.pid = pid;
        }
    }

    private static PortManagment instance = null;
    //TODO: SR: Kompletirati ceo paket da postoji potpuna evidencija po klijentima da 
    // moze da se radi zamena da postoji opcija jedne ili vise instanci client-a
    private static Map<Integer, Client> portOfClients = new HashMap<Integer, Client>();

    // this is Singleton class
    private static PortManagment getInstance() {
        if (instance == null) {
            instance = new PortManagment();
            portOfClients = new HashMap<Integer, PortManagment.Client>();
        }
        return instance;
    }

    public static int setPortForClient(String clientName, int pid) {
        PortManagment portM = PortManagment.getInstance();
        Client client = portM.new Client(clientName, 0, pid);

        int randomNum = 0;
        boolean findCorrectPort = false;
        while (!findCorrectPort) {
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            randomNum = ThreadLocalRandom.current().nextInt(3000, 10000);
            Client clientL = portOfClients.get(randomNum);
            if (clientL == null) {
                client.port = randomNum;
                portOfClients.put(randomNum, client);
                findCorrectPort = true;
            }
        }
        return randomNum;

    }

}