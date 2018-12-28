package com.alpha;

import com.alpha.decorations.Accessory;
import com.alpha.decorations.FlowerBouquet;
import com.alpha.plants.Flower;
import com.alpha.plants.FlowerType;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SaverReader implements Serializable {
    static String name;
    static ObjectInputStream objectInputStream;
    static ObjectOutput objectOutputStream;// = new ObjectInputStream(new FileInputStream("data.txt"));

    public SaverReader(String name) throws IOException {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        SaverReader saverReader = new SaverReader("data.txt");
        List<FlowerBouquet> priceables = new ArrayList<>();

        priceables.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
        }}, EnumSet.allOf(Accessory.class)));
        saverReader.write(priceables);
        priceables = saverReader.read();
        System.out.println(priceables.toString());


    }

    static List<FlowerBouquet> read() throws IOException, ClassNotFoundException {
        objectInputStream = new ObjectInputStream(new FileInputStream(name));
        List<FlowerBouquet> priceables1 = (ArrayList<FlowerBouquet>) objectInputStream.readObject();
        objectInputStream.close();
        return priceables1;
        //     return null;
    }

   static void write(List<FlowerBouquet> priceables) throws IOException, ClassNotFoundException {
        objectOutputStream = new ObjectOutputStream(new FileOutputStream(name));
        objectOutputStream.writeObject(priceables);
        objectOutputStream.close();
    }
}
