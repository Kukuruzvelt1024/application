let params = new URLSearchParams(document.location.search);
       let countryRequired = params.get("country");
       let yearRequired = params.get("year");
       let genreRequired = params.get("genre");
       let searchRequest = params.get("search");
       let table = document.createElement('table');
       table.setAttribute("align", "center");
       fetch("/catalogjson?genre="+genreRequired+"&year="+yearRequired+"&country="+countryRequired+"&search="+searchRequest)
       .then(response => {
           if (!response.ok) {
               throw new Error(`HTTP error! status: ${response.status}`);
           }
           return response.json(); // Преобразование ответа в JSON
      })
      .then(data => {
           console.log(data); // Работаем с данными
           console.log("Length=" + data.length);
           var row = table.insertRow();
           var i = 0;
            for (const entity of data){
               if(i == 3){
                   var row = table.insertRow();
                   i = 0;
               }
               i++
               let cell = row.insertCell();
               cell.innerHTML =
               "<a href=/movie/" + entity.WebMapping +"><img src=poster/" + entity.WebMapping + " alt="+entity.TitleRussian + " width=120 height=180 /></a>" +
               "<p><a href=/movie/"+entity.WebMapping+ ">" + entity.TitleRussian + "</a></p>" +
               "<p>" + entity.Countries+ " / " + entity.Genres + " / " + entity.Duration + " мин. / " + entity.Year + "</p>"
           }
      })
      .catch(error => {
            console.error('Ошибка при получении данных:', error);
      });
        document.getElementById("Table_of_entities").appendChild(table);