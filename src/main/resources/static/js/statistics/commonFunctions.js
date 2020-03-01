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

function plot(data, containerId, testTitle="Global statistics") {
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
        barmode: 'group',
        //plot_bgcolor: 'rgb(228, 228, 228)',
        //bgcolor: 'rgb(228, 228, 228)',

};
    Plotly.newPlot(container1, plot_data, layout, {displayModeBar: false});

    // setting background of plot
    //document.getElementsByClassName('main-svg')[0].setAttribute('style', 'rgb(228)')
}

let searchPlace = function (arr, x, start, end) {
    let mid = Math.floor((start + end)/2);
    if (start > end) {
        return mid < 0 ? 0 : mid;
    }
    if (arr[mid]===x) return mid;
    if(arr[mid] > x)
        return searchPlace(arr, x, start, mid-1);
    else
        return searchPlace(arr, x, mid+1, end);
};

function processData(data) {
    /**
     * Count how many results in ranges 0-9, 10-19, 20-29, ..., 90-100.
     * @return {Array} array of 10 elements which says upper mentioned information
     */
        // todo rewrite this code in dynamic style
    let result = [];
    let lowerBound = 0;
    let upperBound = 9;
    for (let i = 0; i < 10; i++) {
        result.push(data.filter(score =>{
            return (score >= lowerBound) && (score <= upperBound)
        }));
        lowerBound += 10;
        upperBound += 10;
    }
    result = result.map(function (arr) {
        return arr.length
    });
    return result
}



function lastGameStatistics(allData, gameResult) {
    /**
     * Returns result in range [0, 1]
     * 0 means that all results are better than current
     * 1 means that current result is the best of all time
     * 0.6 means that result is better than 60% of all results
    * */
    let place = searchPlace(allData, gameResult, 0, allData.length-1);
    return (place / allData.length).toFixed(3)
}

function showLastGameStatistics(result, containerId="stats-inf") {
    let container = document.getElementById(containerId);
    let percentage = (result * 100).toFixed(1);
    container.innerHTML = "<a>Your last score is better than " + percentage + "% of users have</a>";
}