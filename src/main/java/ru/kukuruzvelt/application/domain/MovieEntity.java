package ru.kukuruzvelt.application.domain;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor


public class MovieEntity {

    private String WebMapping;
    private String FilePath;
    private String PosterPath;
    private String Year;
    private String Country;
    private String Genre;
    private int Duration;
    private String TitleRussian;
    private String TitleOriginal;

    public static class FieldFreeEntity{
        Map<String, String> entityParam = new HashMap<>();
    }
}
