async function getStats(testType) {
    let url = '/createStatistic/statisticByUserForRepeatNumbers?type=' + testType;
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

async function main(){
    const response1 = await getStats('RepeatNumbersTest');
    const data1 = processData(response1);
    plot(data1, 'plot-1', 'Repeat Numbers');

    const response2 = await getStats('CountGreenTest');
    const data2 = processData(response2);
    plot(data2, 'plot-2', 'Count Green');

    const response3 = await getStats('ReactionTest');
    const data3 = processData(response3);
    plot(data3, 'plot-3', 'Reaction');

    const response4 = await getStats('IsPreviousTest');
    const data4 = processData(response4);
    plot(data4, 'plot-4', 'Is Previous');


}

window.onload = main;