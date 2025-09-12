package ru.kukuruzvelt.application.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class RawDataCompiler {
    private List<MovieEntity> localDatabaseCopy;
    private DAO dao;


    public RawDataCompiler(List list){
        Collections.copy(list, this.localDatabaseCopy);
    }

    public void sortBy(String sortType){
        if (sortType.contentEquals("eng"))
            this.localDatabaseCopy.sort(Comparator.comparing(MovieEntity::getTitleOriginal));
        if (sortType.contentEquals("ru"))
            this.localDatabaseCopy.sort(Comparator.comparing(MovieEntity::getTitleRussian));
        if (sortType.contentEquals("year"))
            this.localDatabaseCopy.sort(Comparator.comparing(MovieEntity::getYear));
        if (sortType.contentEquals("duration"))
            this.localDatabaseCopy.sort(Comparator.comparing(MovieEntity::getDuration));
        if (sortType.contentEquals("genre"))
            this.localDatabaseCopy.sort(Comparator.comparing(MovieEntity::getGenre));
    }

    public List<MovieEntity> getAllEntriesByGenre(String genre){
        Iterator<MovieEntity> it = this.localDatabaseCopy.iterator();
        while (it.hasNext()){
            MovieEntity me = it.next();
            if (me.getGenre().equalsIgnoreCase(genre)){
                return Collections.singletonList(me);
            }
        }
        return null;
    }

    public List<MovieEntity> getAllEntriesByYear(int year){
        return null;
    }

    public List<MovieEntity> getAllEntriesByCountry(String country){
        return null;
    }
}
