package ru.kukuruzvelt.application.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor


public class MovieEntity {

    private String WebMapping;
    private String FilePath;
    private String TitleRussian;
    private String TitleOriginal;

}
