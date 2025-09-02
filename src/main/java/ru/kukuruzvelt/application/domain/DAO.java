package ru.kukuruzvelt.application.domain;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DAO {

    private String source;
    private List<MovieEntity> localDatabaseCopy = new ArrayList<>();

    public DAO(String source){
        this.source = source;
        try {
            System.out.println("DAO CREATED");
            List<String> list = Files.readAllLines(Paths.get(source));
            Iterator<String> iterator = list.iterator();
            while(iterator.hasNext()) {
                String str = iterator.next();
                if (str.contentEquals("{")){
                    MovieEntity entity = new MovieEntity();
                    entity.setWebMapping(iterator.next().split(" = ")[1]);
                    entity.setFilePath(iterator.next().split(" = ")[1]);
                    entity.setTitleRussian(iterator.next().split(" = ")[1]);
                    entity.setTitleOriginal(iterator.next().split(" = ")[1]);
                    this.localDatabaseCopy.add(entity);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("DAO CREATION FINISHED");
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

    public MovieEntity findByWebMapping(String webMapping){
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

}
