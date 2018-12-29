package com.alpha.plants;

import java.util.Objects;

public class Tree extends Plant {
    TreeType treeType;

    public Tree(Double length, String country, Integer price, TreeType treeType) {
        super(length, country, price);
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    @Override
    public String toString() {
        return "\n" + this.getTreeType() + "{" +
                super.toString()
                + "} \n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tree)) return false;
        if (!super.equals(o)) return false;
        Tree tree = (Tree) o;
        return getTreeType() == tree.getTreeType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTreeType());
    }
}
