package ru.kukuruzvelt.application.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DAO {

    private String source;
    private List<MovieEntity> localDatabaseCopy = new ArrayList<>();
    private static List<MovieEntity> databaseCache = new ArrayList<>();
    public DAO instance;
    public TimerTask task;
    public Timer timer;

    public DAO(String source){
        this.source = source;
        this.updateInformation();
    }

    public DAO getInstance(String source){
        if (this.instance == null){
            return new DAO(source);
        }
        return this.instance;
    }

    public void updateInformation(){
        try {
            List<String> list = Files.readAllLines(Paths.get(source));
            Iterator<String> iterator = list.iterator();
            while(iterator.hasNext()) {
                String str = iterator.next();
                if (str.contentEquals("{")){
                    MovieEntity entity = new MovieEntity();
                    entity.setWebMapping(iterator.next().split(" = ")[1]);
                    entity.setFilePath(iterator.next().split(" = ")[1]);
                    entity.setPosterPath(iterator.next().split(" = ")[1]);
                    entity.setYear(iterator.next().split(" = ")[1]);
                    entity.setCountry(iterator.next().split(" = ")[1]);
                    entity.setGenre(iterator.next().split(" = ")[1]);
                    entity.setDuration(Integer.parseInt((iterator.next().split(" = ")[1])));
                    entity.setTitleRussian(iterator.next().split(" = ")[1]);
                    entity.setTitleOriginal(iterator.next().split(" = ")[1]);
                    this.localDatabaseCopy.add(entity);
                    databaseCache = List.copyOf(this.localDatabaseCopy);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("DAO CREATED");

    }

    public List<MovieEntity> getListOfEntities(){
        return this.localDatabaseCopy;
    }

    public MovieEntity findByOriginalTitle(String title){
        Iterator<MovieEntity> it = this.localDatabaseCopy.iterator();
        while (it.hasNext()){
            MovieEntity me = it.next();
            if (me.getTitleOriginal().equalsIgnoreCase(title)){
                return me;
            }
        }
        return null;
    }

    public MovieEntity findByRussianTitle(String title){
        Iterator<MovieEntity> it = this.localDatabaseCopy.iterator();
        while (it.hasNext()){
            MovieEntity me = it.next();
            if (me.getTitleRussian().equalsIgnoreCase(title)){
                return me;
            }
        }
        return null;
    }

    public MovieEntity findByWebMapping(String webMapping) throws NullPointerException{
        Iterator<MovieEntity> staticIterator = databaseCache.iterator();
        while (staticIterator.hasNext()){
            MovieEntity me = staticIterator.next();
            if (me.getWebMapping().equalsIgnoreCase(webMapping)){
                return me;
            }
        }


        Iterator<MovieEntity> it = this.localDatabaseCopy.iterator();
        while (it.hasNext()){
            MovieEntity me = it.next();
            if (me.getWebMapping().equalsIgnoreCase(webMapping)){
                return me;
            }
        }
        return null;
    }

    public MovieEntity findByFilePath(String filePath){
        Iterator<MovieEntity> it = this.localDatabaseCopy.iterator();
        while (it.hasNext()){
            MovieEntity me = it.next();
            if (me.getFilePath().equalsIgnoreCase(filePath)){
                return me;
            }
        }
        return null;
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

    class UpdateScheduler extends TimerTask{


        @Override
        public void run() {

        }
    }

}
