package com.example.a222latest;

import java.util.HashMap;
import java.util.Objects;

public class LocationAdapter
{
    private HashMap<String, Integer> faculty_to_vertex;
    private HashMap<String, Point2D> vertex_to_pixel_point;

    public LocationAdapter()
    {
        faculty_to_vertex = new HashMap<>();
        createStructure_vertex();
        createStructure_pixel();
    }

    private void createStructure_vertex() {

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
        faculty_to_vertex.put("Marmaray Exit", 34);
        faculty_to_vertex.put("Rector Underpass", 35);
        faculty_to_vertex.put("Marmaray Entry", 36);
        faculty_to_vertex.put("Underpass", 0);
        faculty_to_vertex.put("Faculty of Basic Sciences Underpass", 37);
    }

    private void createStructure_pixel() {

        vertex_to_pixel_point.put("0", new Point2D(835, 530));
        vertex_to_pixel_point.put("1", new Point2D(1358, 766));
        vertex_to_pixel_point.put("2", new Point2D(1244, 716));
        vertex_to_pixel_point.put("3", new Point2D(1566, 716));
        vertex_to_pixel_point.put("4", new Point2D(1523, 663));
        vertex_to_pixel_point.put("5", new Point2D(1451, 645));
        vertex_to_pixel_point.put("6", new Point2D(1376, 602));
        vertex_to_pixel_point.put("7", new Point2D(1372, 549));
        vertex_to_pixel_point.put("8", new Point2D(1363, 499));
        vertex_to_pixel_point.put("9", new Point2D(1304, 555));

        vertex_to_pixel_point.put("10", new Point2D(1265, 591));
        vertex_to_pixel_point.put("11", new Point2D(1242, 553));
        vertex_to_pixel_point.put("12", new Point2D(1222, 587));
        vertex_to_pixel_point.put("13", new Point2D(740, 631));
        vertex_to_pixel_point.put("14", new Point2D(1137, 667));
        vertex_to_pixel_point.put("15", new Point2D(1051, 594));
        vertex_to_pixel_point.put("16", new Point2D(1123, 563));
        vertex_to_pixel_point.put("17", new Point2D(1153, 606));
        vertex_to_pixel_point.put("18", new Point2D(1165, 554));
        vertex_to_pixel_point.put("19", new Point2D(1147, 438));

        vertex_to_pixel_point.put("20", new Point2D(1026, 412));
        vertex_to_pixel_point.put("21", new Point2D(987, 423));
        vertex_to_pixel_point.put("22", new Point2D(937, 408));
        vertex_to_pixel_point.put("23", new Point2D(929, 442));
        vertex_to_pixel_point.put("24", new Point2D(1014, 456));
        vertex_to_pixel_point.put("25", new Point2D(872, 436));
        vertex_to_pixel_point.put("26", new Point2D(883, 474));
        vertex_to_pixel_point.put("27", new Point2D(761, 497));
        vertex_to_pixel_point.put("28", new Point2D(634, 505));
        vertex_to_pixel_point.put("29", new Point2D(597, 625));

        vertex_to_pixel_point.put("30", new Point2D(673, 697));
        vertex_to_pixel_point.put("31", new Point2D(414, 685));
        vertex_to_pixel_point.put("32", new Point2D(435, 730));
        vertex_to_pixel_point.put("33", new Point2D(816, 840));
        vertex_to_pixel_point.put("34", new Point2D(1027, 796));
        vertex_to_pixel_point.put("35", new Point2D(1212, 501));
        vertex_to_pixel_point.put("36", new Point2D(1153, 754));
        vertex_to_pixel_point.put("37", new Point2D(980, 733));
    }

    public Integer get_vertex_loca(String key) {

        return faculty_to_vertex.get(key);
    }

    public Point2D get_vertex_pixel(String key) {

        return vertex_to_pixel_point.get(key);
    }
}
