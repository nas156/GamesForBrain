/**
 * Fetch statistics of certain game of current user from server
 * @param {string} testType - type of test from test_type table
 * @returns {Promise<object>} - json object with results
 */
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

/**
 *
 * @param {Array} yData - array in form of [number of results in range 0-9,
 * number of results in range 10-19, ..., number of results in range 90-100]
 * @param {string} containerId - id of container
 * @param testTitle - title of test
 */
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

/**
 * Draws plots and put them into the containers
 * @returns {Promise<void>}
 */
async function main(){
    const response1 = await getStats('RepeatNumbersTest');
    const data1 = processData(response1);
    plot(data1, 'plot-1', 'Repeat Numbers');

    const response2 = await getStats('ReactionTest');
    const data2 = processData(response2);
    plot(data2, 'plot-2', 'Reaction');

    const response3 = await getStats('RepeatSequenceTest');
    const data3 = processData(response3);
    plot(data3, 'plot-3', 'Repeat Sequence');

    const response4 = await getStats('IsPreviousTest');
    const data4 = processData(response4);
    plot(data4, 'plot-4', 'Is Previous');

    const response5 = await getStats('CountGreenTest');
    const data5 = processData(response5);
    plot(data5, 'plot-5', 'Count green');

    dropDownAnimation();
}

window.onload = main;
