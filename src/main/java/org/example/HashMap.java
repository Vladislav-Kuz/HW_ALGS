/* Необходимо доработать структуру класса HashMap, реализованную на семинаре.

    У нас появился метод добавления элементов,
    каким образом я могу распечатать все элементы структуры хэш-таблицы на экране?

        1. Задача: Распечатайте все элементы структуры HashMap
        переопределив метод toString() - самый простой вариант.
        2. (Дополнительная, необязательная задача)
        Добавить возможность перебора всех элементов нашей структуры данных,
        необходимо добавить несколько элементов,
        а затем перебрать все элементы структуры HashTable используя цикл foreach.
        Подумайте, возможно вам стоит обратиться к интерфейсу Iterable и в рамках
        имплементации подобного интерфейса создать объект типа Iterator,
        далее, вы реализуете метод next и hasNext,
        наделите способностью нашу структуру HashMap быть перечисляемой.
*/

package org.example;


/**
 * Хэш-таблица
 * @param <K>
 * @param <V>
 */
public class HashMap<K, V> {

    //region Публичные методы

    /**
     * Свой метод PUT
     */
    public V put(K key, V value) {
        if (buckets.length * LOAD_FACTOR <= size) {
            recalculate();
        }
        //Рассчитаем индекс, куда добавлять
        int index = calculateBucketIndex(key);
        // получим доступ ук объекту по индексу
        Bucket bucket = buckets[index];
        // Существует объект? Если нет, создадим
        if (bucket == null) {
            bucket = new Bucket();
            buckets[index] = bucket;
        }
        // Создадим объект - составной элемент связанного списка
        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;
        // Добавим элемент связанного списка
        V buf = (V) bucket.add(entity);
        // проверяем, действительно ли был добавлен элемент. т.к. в return
        // попадает значение только в случае его перезаписи
        if (buf == null) { // если ничего не вернулось, значит элемента здесь не было и можно добавлять
            size++;
        }
        return buf;
    }

    /**
     * Поиск значения в таблице по ключу
     *
     * @param key
     * @return
     */
    public V get(K key) {
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        return (V) bucket.get(key);

    }


    /**
     * Удаление элемента таблицы по ключу
     *
     * @param key
     * @return значение
     */
    public V remove(K key) {
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        V buf = (V) bucket.remove(key);
        if (buf != null) {
            size--;
        }
        return buf;
    }

//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(buckets);
//        System.out.println(stringBuilder);
//        System.out.println();
//
////        }
//            return stringBuilder.toString();
//    }




    //endregion

    //region Методы

    /**
     * Метод, производящий реклькулирование,
     * если количество элементов становится больше 0,5 от длины массива
     */
    private void recalculate(){
        size = 0;
        Bucket<K, V>[] old = buckets;
        buckets = new Bucket[old.length*2];
        for (int i=0; i<old.length; i++){
            Bucket<K, V> bucket = old[i];
            if(bucket == null)
                continue;
            Bucket.Node node = bucket.head;
            while (node!=null){
                put((K)node.value.key, (V)node.value.value);
                node=node.next;
            }
        }
    }

    /**
     * Метод, вычисляющий индекс
     */
    private int calculateBucketIndex(K key){
        return Math.abs(key.hashCode()) % buckets.length;
    }
    //endregion

    //region Конструкторы
    public HashMap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public HashMap(int capacity){
        buckets = new Bucket[capacity];
    }
    //endregion

    //region Поля
    private Bucket[] buckets;

    private int size; //количество элементов


    //endregion

    //region Вспомогательные структуры
    /**
     * Элемент хэш-таблицы
     */
    class Entity{
        K key;
        V value;
    }

    class Bucket <K, V> {
        /**
         * Указатель на первый элемент связного списка
         */
        private Node head;

        class Node {
            Node next;
            Entity value;
        }

        /**
         * Метод добавления нового элемента (если список пустой) на уровне связного списка
         * @param entity элемент хэш таблицы
         * @return значение старого элемента, если ключи совпадают
         */
        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;
            if(head==null){
                head = node;
                return null;
            }

            Node currentNode = head;
            // бесконечный цикл
            while(true){
                if(currentNode.value.equals((entity.key))){
                    V buf = (V)currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if(currentNode.next!=null){
                    currentNode = currentNode.next;
                }
                else{ //если попали сюда. это значит, что такого элемента в списке нет и его можно добавить
                    currentNode.next = node;
                    return  null;
                }
            }

        }

        public V get(K key){
            Node node = head;
            while (node!=null){
                if(node.value.key.equals(key)) {
                    return (V)node.value.value;
                }
                node = node.next;
            }
            return null;
        }

        public V remove(K key){
            if(head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = (V)head.value.value;
                head = head.next;
                return buf;
            }

            else{ // если в bucket есть нода и по ключу она не равна
                Node node =head;
                while(node.next!=null){
                    if(node.next.value.key.equals(key)){
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }
    }
    //endregion

    //region Константы
    /**
     * Начальная длина массива
     */
    private static final int INIT_BUCKET_COUNT = 16;

    /**
     * Процентное соотношение количества эл-тов к длине массива
     */
    private static final double LOAD_FACTOR = 0.5;
    //endregion
}
