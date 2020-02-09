let searchPlace = function (arr, x, start, end) {
    let mid = Math.floor((start + end)/2);
    if (start > end) return mid;
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
        // todo rewrite this code in dynamic style, because now it's govno sobaki
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
    container.innerHTML = "<a>Your last score is better than " + (result * 100) + "% of users have</a>";
}