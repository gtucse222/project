package com.example.a222latest;

import java.util.HashMap;
import java.util.Objects;

public class LocationAdapter
{
    private HashMap<String, Integer> faculty_to_vertex;

    public LocationAdapter()
    {
        faculty_to_vertex = new HashMap<>();
        createStructure();
    }

    private void createStructure() {

        faculty_to_vertex.put("Campus Entry", 1);
        faculty_to_vertex.put("Social and Science Institute", 2);
        faculty_to_vertex.put("Health, culture and sports department", 3);
        faculty_to_vertex.put("Nano Technology Lab", 4);
        faculty_to_vertex.put("KOSGEB", 5);
        faculty_to_vertex.put("Rector's House", 6);
        faculty_to_vertex.put("Foreign Languages Department", 7);
        faculty_to_vertex.put("Materials Science or Mechanical Engineering", 8);
        faculty_to_vertex.put("Architecture", 9);
        faculty_to_vertex.put("Architecture Faculty Office", 10);

        faculty_to_vertex.put("Architecture Classrooms", 11);
        faculty_to_vertex.put("Dining Hall", 12);
        faculty_to_vertex.put("Library", 13);
        faculty_to_vertex.put("Business Faculty", 14);
        faculty_to_vertex.put("Undergraduate Students Classrooms", 15);
        faculty_to_vertex.put("Design and Manufacturing Lab", 16);
        faculty_to_vertex.put("Geodetic Engineering", 17);
        faculty_to_vertex.put("Math Faculty", 18);
        faculty_to_vertex.put("Rectorate Building", 19);
        faculty_to_vertex.put("Construction Works and Technical Department", 20);

        faculty_to_vertex.put("Nursery", 21);
        faculty_to_vertex.put("Personal Dining Hall", 22);
        faculty_to_vertex.put("Erasmus Office", 23);
        faculty_to_vertex.put("Student Affairs Office", 24);
        faculty_to_vertex.put("Mosque", 25);
        faculty_to_vertex.put("TTO", 26);
        faculty_to_vertex.put("Computer Engineering", 27);
        faculty_to_vertex.put("Electronic Engineering", 28);
        faculty_to_vertex.put("Chemistry Engineering", 29);
        faculty_to_vertex.put("Molecular Biology and Genetic", 30);

        faculty_to_vertex.put("GYM", 31);
        faculty_to_vertex.put("Swimming Pool", 32);
        faculty_to_vertex.put("Faculty of Basic Sciences", 33);
        faculty_to_vertex.put("Will be Added", 34);
        faculty_to_vertex.put("Will be Added2", 35);
        faculty_to_vertex.put("Will be Added3", 36);
        faculty_to_vertex.put("Will be Added4", 0);
    }

    public Integer get_vertex_loca(String key) {

        return faculty_to_vertex.get(key);
    }
}
