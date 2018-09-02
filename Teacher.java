// Скрипт для случайного выбора учеников для проверяющего учителя, но учитель так же является учеником для других учеников
// тоесть каждый проверяет 3 человека и его тоже проверяют 3 человека, по типу проверки в школе Ш++
package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//создаем класс учителя
public class Teacher {
    private String name;
    private ArrayList<String> learners = new ArrayList<>();
    private byte teacherCount;

//    определяем конструктор для каждого экземпляра
    Teacher(String inputName) {
        name = inputName;
    }

//    главный метод
    public static void main(String[] args) {
//        задаем список учителей
        String[] teacherList = {"Alexandr Zalenskiy", "Dmitro Napoleon", "Alex Memfis", "Coca Cola", "Anton Pepsi",
                "Adron Kolaider", "Marina Volochkova", };

//        ученики являются теми же учителями, но этот список мы будем постоянно менять так что делаем копию в ArrayList
        ArrayList<String> remainLearners = new ArrayList<>(Arrays.asList(teacherList));

//        задаем пустой массив для объектов класса
        Teacher[] teacherObj = new Teacher[teacherList.length];

//        задаем сколько человек нужно каждому проверить
        int reviewCount = 3;

//        создаем обьекты
        for (byte i = 0; i < teacherList.length; i++) {
            Teacher teacher = new Teacher(teacherList[i]);
            teacherObj[i] = teacher;
        }

//        для каждого обьекта назначаем учеников которые он должен проверить
        for (byte i = 0; i < teacherList.length; i++) {

//            убираем текущего учителя из списков учеников
            remainLearners.remove(teacherList[i]);

//            может получится что оставшихся учеников меньше чем нужно проверить
            if (remainLearners.size() < reviewCount) {
                reviewCount = remainLearners.size();
            }

//            рандомно выбираем из списка учеников review_count шт.
            ArrayList<String> oldRandom = new ArrayList<>();
            byte j = 0;
            String learnerName;
            while (j < reviewCount) {

//                выбираем случайное значение из массива оставшихся учеников
                int rand = new Random().nextInt(remainLearners.size());
                learnerName = remainLearners.get(rand);

//                проверяем чтобы не попался тот же ученик что и на предыдущей итерации, если попался то повторяем рандомный выбор
                if (oldRandom.contains(learnerName)) continue;

//                запоминаем кого мы выбрали учеником
                oldRandom.add(learnerName);
                j++;

//                добавляем в объект учителя его ученика
                teacherObj[i].learners.add(learnerName);

//                увеличиваем счетчик проверяющих учителей у ученика
                for (Teacher obj:teacherObj) {
                    if (obj.name.equals(learnerName)) {
                        obj.teacherCount++;

//                        проверяем что количество учителей не превысило допустимый лимит
                        if (obj.teacherCount >= reviewCount) {

//                            если привысило то этого ученика больше не нужно выводить в списке учеников
                            remainLearners.remove(obj.name);
                        }
                    }
                }
            }

//          возвращаем текущего учителя в список учеников для следуйщей итерации где он уже будет учеником
//          но проверяем что у него не должно быть слишком много учителей, иначе не добавляем
            if (teacherObj[i].teacherCount < reviewCount) {
                remainLearners.add(teacherObj[i].name);
            }
        }

//        выводим список учителей и их учеников
        for (Teacher t:teacherObj) {
            System.out.println(t.name + " review " + t.learners);
        }
    }
}
