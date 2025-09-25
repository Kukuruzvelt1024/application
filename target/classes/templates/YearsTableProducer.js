let yearstable = document.createElement('table');
        yearstable.setAttribute("align", "center");
        fetch("/yearsjson").then(response => {
            if (!response.ok) {throw new Error(`HTTP error! status: ${response.status}`);}
            return response.json(); // Преобразование ответа в JSON
       })
       .then(data => {
            var row = yearstable.insertRow();
            let allYearCellReference = row.insertCell();
            allYearCellReference.innerHTML = "<p><a href=?year=all>Все</a><p/>"
             for (const entity of data){
                let cell = row.insertCell();
                cell.innerHTML =
                "<p><a href=?year=" + entity + ">" + entity + "</a></p>"
            }
       })
       .catch(error => {
             console.error('Ошибка при получении данных:', error);
       });
     document.getElementById("Filter_by_year").appendChild(yearstable);