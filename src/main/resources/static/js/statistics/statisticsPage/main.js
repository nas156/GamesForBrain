async function getStats() {
    /**
     * Returns all test's statistics of current user
     * */
    let url = '/createStatistic/getAllTestsStatisticByUser';
    return await fetch(url)
        .then(response => {
            return response.json()
        })
        .then(result => {
            return result
        })
        .catch(err => {
            console.log(err)
        })
}



function plot(yData, containerId, testTitle) {
    let xData = ['0-9', '10-19', '20-29', '30-39', '40-49', '50-59', '60-69', '70-79', '80-89', '90-100'];
    const container1 = document.getElementById(containerId);
    const trace1 = {
        x: xData,
        y: yData,
        type: 'bar',
        marker: {
            color: 'rgb(75, 111, 255)',
            opacity: 0.7,
        }
    };
    const data = [trace1];
    const layout = {
        title: testTitle,
        barmode: 'group'
    };
    Plotly.newPlot(container1, data, layout, {displayModeBar: false});
}

async function buildPlots(){
    const response = await getStats();
    let plotsContainer = document.getElementById("plots");
    let id = 1;
    for (let testType in response){
        let data = processData(response[testType]);
        let containerId = `plot-${id++}`;
        let container = document.createElement('DIV');
        container.setAttribute('id', containerId);
        container.setAttribute('class', 'plot-container');
        plotsContainer.appendChild(container);
        plot(data, containerId, testType);
    }

}

async function main(){
    await buildPlots()
}

window.onload = main;
