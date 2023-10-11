package org.example;
import java.util.HashMap;

public class Main {
    public static void main(String[] args)
    {
//        HashMap<Integer, String>hashMap = new HashMap<>();
//        hashMap.put(12, "AAAA");
//        System.out.println(hashMap);
//        Employee employee1 = new Employee("AAA", 12);
//        System.out.println(employee1.hashCode());
//        Employee employee2 = new Employee("BBB", 45);
//        System.out.println(employee2.hashCode());
        HashMap<String, String> hashMap = new HashMap<>(4);
        String addResult = hashMap.put("+79204089100", "AAAAA");
        addResult = hashMap.put("+79204089101", "BBBBBB");
        addResult = hashMap.put("+79204089100", "DDDDDD");
        addResult = hashMap.put("+79204089102", "EEEEEE1");
        addResult = hashMap.put("+79204089103", "EEEEEE2");
        addResult = hashMap.put("+79204089104", "EEEEEE3");
        addResult = hashMap.put("+79204089105", "EEEEEE4");
        addResult = hashMap.put("+79204089106", "EEEEEE5");
        addResult = hashMap.put("+79204089107", "EEEEEE6");
        addResult = hashMap.put("+79204089108", "EEEEEE7");
        addResult = hashMap.put("+79204089109", "EEEEEE8");

        String searchResult = hashMap.get("+79204089107");
//        var res = hashMap.toString();
//        System.out.println(res);
//        System.out.println();
//
//        System.out.println(hashMap);

        for(var item: hashMap.entrySet()){
            System.out.printf("[%s : %s]\n", item.getKey(), item.getValue());
        }
    }

}
