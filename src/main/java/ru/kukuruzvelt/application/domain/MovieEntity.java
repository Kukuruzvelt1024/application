package ru.kukuruzvelt.application.domain;

import lombok.*;

import java.util.HashMap;
import java.util.List;
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
    private Integer Year;
    private String Country;
    private String[] Genre;
    private int Duration;
    private String TitleRussian;
    private String TitleOriginal;

  public String getGenre(){
      StringBuilder builder = new StringBuilder();
      for(int i = 0; i < this.Genre.length; i++){
          if(i > 0) builder.append(", ");
          builder.append(this.Genre[i]);
      }
      return builder.toString();
  }

  public boolean containsGenre(String genreRequired){
      for(int i = 0; i < Genre.length; i++){
          if (genreRequired.contentEquals(Genre[i])) return true;
      }
      return false;
  }
}
