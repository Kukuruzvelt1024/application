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
    @RequestParam(name = "sortby", required = false, defaultValue = "RussianTitle") String sortingType,
    @RequestParam(name = "filter", required = false, defaultValue = "order") String filter){
        System.out.println("Доступ к каталогу от: " + request.getRemoteAddr() + "; Тип сортировки: " + sortingType
        + "; Фильтр по: " + filter);
        DAO dao = DAO.getInstance(Application.sourceBase);
        dao.prepareData();
        //dao.filterBy();
        dao.sortBy(sortingType);
        List<MovieEntity> list = dao.getListOfEntities();

        model.addAttribute("MoviesList", list);
        return "catalog";
    }
}
