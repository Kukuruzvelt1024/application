package ru.kukuruzvelt.application.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kukuruzvelt.application.Application;
import ru.kukuruzvelt.application.domain.DAO;
import ru.kukuruzvelt.application.domain.MovieEntity;


import java.util.Comparator;
import java.util.List;

@Controller
public class CatalogController {

    @GetMapping("/catalog")
    public String getCatalog(Model model,
                             HttpServletRequest request,
                             HttpServletResponse response,
    @RequestParam(name = "sortby", required = false, defaultValue = "year") String sortingType,
    @RequestParam(name = "year", required = false, defaultValue = "-1") String yearRequired,
    @RequestParam(name = "genre", required = false, defaultValue = "all") String genreRequired){
        System.out.println("Доступ к каталогу от: " + request.getRemoteAddr() + "; Тип сортировки: " + sortingType
        + "; Фильтр по: " + yearRequired);
        DAO dao = DAO.getInstance(Application.sourceBase).
                prepareData().
                filterByYear(Integer.parseInt(yearRequired)).
                filterByGenre(genreRequired).
                sortBy(sortingType);

        model.addAttribute("listOfGenres", dao.getAllGenres());
        model.addAttribute("listOfYears", dao.getAllYears());
        model.addAttribute("listOfMovies", dao.getListOfEntities());

        return "catalog";
    }
}
