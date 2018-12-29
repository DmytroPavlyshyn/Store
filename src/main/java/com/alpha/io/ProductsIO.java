package com.alpha.io;

import com.alpha.Priceable;
import com.alpha.decorations.Accessory;
import com.alpha.decorations.FlowerBouquet;
import com.alpha.plants.Flower;
import com.alpha.plants.FlowerType;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ProductsIO implements Serializable {  //тут брєд, поки не дивіться
    static String name;
    static ObjectInputStream objectInputStream;
    static ObjectOutputStream objectOutputStream;// = new ObjectInputStream(new FileInputStream("data.txt"));

    public ProductsIO(String name) {
        this.name = name;
    }

    static List<Priceable> read() throws IOException, ClassNotFoundException {
        objectInputStream = new ObjectInputStream(new FileInputStream("data.txt"));
        List<Priceable> /*Object */priceables1 = (ArrayList<Priceable>) objectInputStream.readObject();
        objectInputStream.close();
        return priceables1;
    }

    static void write(List<FlowerBouquet> priceables) throws IOException, ClassNotFoundException {
        objectOutputStream = new ObjectOutputStream(new FileOutputStream(name));
        objectOutputStream.writeObject(priceables);
        objectOutputStream.close();
    }

    public static void main(String[] args) throws Exception {
        ProductsIO productsIO = new ProductsIO("data.txt");
        List<FlowerBouquet> priceables = new ArrayList<>();

        priceables.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
        }}, EnumSet.allOf(Accessory.class)));
        // productsIO.write(priceables);
        System.out.println(productsIO.read());
    }
}

