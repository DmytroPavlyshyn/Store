package com.alpha;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(?<index>\\d+)");
        Scanner scanner = new Scanner(System.in);
        Matcher matcher = pattern.matcher(scanner.nextLine());
        while (matcher.find()) {
            System.out.println(matcher.group("index"));
        }
    }
}
