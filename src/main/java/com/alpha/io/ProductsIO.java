package com.alpha.io;

import com.alpha.Priceable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsIO  {  //тут брєд, поки не дивіться
    private String name;
    private  ObjectInputStream objectInputStream;
    private  ObjectOutputStream objectOutputStream;

    public ProductsIO(String name) {
        this.name = name;
    }

    public List<Priceable> read() throws IOException, ClassNotFoundException {
        objectInputStream = new ObjectInputStream(new FileInputStream(name));
        List<Priceable> priceables1 = new ArrayList<>();
        priceables1.addAll(ArrayList.class.cast(objectInputStream.readObject()));
        objectInputStream.close();
        return priceables1;
    }

    public  void write(List<Priceable> priceables) throws IOException {
        objectOutputStream = new ObjectOutputStream(new FileOutputStream(name));
        objectOutputStream.writeObject(priceables);
        objectOutputStream.close();
    }


}

