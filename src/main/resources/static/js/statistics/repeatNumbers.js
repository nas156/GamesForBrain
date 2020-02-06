fetch("http://localhost:8082/createStatistic/statisticByUserForRepeatNumbers")
    .then(response => {
        return response.json()
    })
    .then(result => {
        console.log(result);
        for (let i of result){
            console.log(i)
        }
    })
    .catch(err => {
        console.log(err)
    });

