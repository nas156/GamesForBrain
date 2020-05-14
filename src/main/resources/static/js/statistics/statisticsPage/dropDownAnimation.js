function dropDownAnimation(){
    $(".plot-header").each(function(index) {
        $(this)[0].onclick = () => {
            if ($(this)[0].className === "plot-header notClicked"){
                $(this)[0].className = "plot-header clicked";
                let plot = $(`.plotContainer:eq(${index})`);
                plot.fadeIn(1000);
            } else if($(this)[0].className === "plot-header clicked"){
                $(this)[0].className = "plot-header notClicked";
                let plot = $(`.plotContainer:eq(${index})`);
                plot.fadeOut(1000);
            }
        }
    })
}
