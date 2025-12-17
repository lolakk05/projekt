package serialization;

import com.google.gson.reflect.TypeToken;
import osoba.Klient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.*;

public class UserSerialize {
    public static ArrayList<Klient> loadClients() {
        try {
            File clientsFile = new File("data/clients.json");
            BufferedReader reader = new BufferedReader(new FileReader(clientsFile));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();;
            Type clientListType = new TypeToken<ArrayList<Klient>>() {}.getType();
            ArrayList<Klient> loadedClients = gson.fromJson(reader, clientListType);
            reader.close();
            return loadedClients;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public static void printClients(ArrayList<Klient> clients) {
        for (Klient client : clients) {
            System.out.println(client);
        }
    }
}
