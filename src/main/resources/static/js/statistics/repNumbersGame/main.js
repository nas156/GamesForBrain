let DATA;

async function getStats(testType) {
    let url = '/createStatistic/statisticByType?type=' + testType;
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

function plot(data, containerId, testTitle) {
    const yData = processData(data);
    let xData = ['0-9', '10-19', '20-29', '30-39', '40-49', '50-59', '60-69', '70-79', '80-89', '90-100'];
    const container1 = document.getElementById(containerId);
    const defaultColor = 'rgb(75, 111, 255)';

    let colorMap = [];
    for (let i = 0; i < xData.length; i++) {
        colorMap.push(defaultColor)
    }

    const trace1 = {
        x: xData,
        y: yData,
        type: 'bar',
        marker: {
            color: colorMap,
            opacity: 0.7,
        }
    };
    const plot_data = [trace1];
    const layout = {
        title: testTitle,
        barmode: 'group'
    };
    Plotly.newPlot(container1, plot_data, layout, {displayModeBar: false});
}

async function main() {
    DATA = await getStats('RepeatNumbersTest');
    DATA = DATA.sort(function (a, b) {return a-b});
    plot(DATA, 'plot', '');
}

window.onload = main;