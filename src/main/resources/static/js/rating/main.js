const url = "/ratingTable/getForTest?type=";
const testElemClasses = ["list-group-item", "hover-event", "event-listener"];

let CONTAINER;
async function buildTable(data) {

    CONTAINER.innerHTML = "";

    const table = document.createElement("table");
    table.setAttribute("class", "table");
    CONTAINER.appendChild(table);

    // creating head and body elements to fill them and add to table later on
    const head = document.createElement("thead");

    const body = document.createElement("tbody");

    data.forEach((elem, id) => {
        if (id === 0) {
            // filling table header
            const row = document.createElement("tr");

            const place = document.createElement("th");
            place.setAttribute("scope", "col");
            place.innerText = "Place";

            const username = document.createElement("th");
            username.setAttribute("scope", "col");
            username.innerText = "Username";

            const score = document.createElement("th");
            score.setAttribute("scope", "col");
            score.innerText = "Score";

            row.appendChild(place);
            row.appendChild(username);
            row.appendChild(score);

            head.appendChild(row)
        } else {
            // filling table body
            const row = document.createElement("tr");

            const place = document.createElement("th");
            place.setAttribute("scope", "row");
            place.innerText = `${id}`;

            const username = document.createElement("th");
            username.setAttribute("scope", "row");
            username.innerText = elem.username;

            const score = document.createElement("th");
            score.setAttribute("scope", "row");
            score.innerText = elem.score;

            row.appendChild(place);
            row.appendChild(username);
            row.appendChild(score);

            body.appendChild(row)
        }

        table.appendChild(head);
        table.appendChild(body);
    })

}

function createSpinner(){
    // creates spinner
    CONTAINER.innerHTML = "";
    const spinner = document.createElement("div");
    spinner.setAttribute("class", "d-flex justify-content-center");
    spinner.setAttribute("id", "rating-spinner");
    const innerDiv = document.createElement("div");
    innerDiv.setAttribute("class", "spinner-border");
    innerDiv.setAttribute("role", "status");
    const span = document.createElement("span");
    span.setAttribute("class", "sr-only");
    innerDiv.appendChild(span);
    spinner.appendChild(innerDiv);
    console.log(spinner);
    CONTAINER.appendChild(spinner)
}
async function main() {
    CONTAINER = document.getElementById("rating-container");
    document.querySelectorAll(".event-listener").forEach(item => {
        item.addEventListener("click", (event) => {
            const currentGame = event.target.id;

            createSpinner();
            fetch(url + currentGame)
                .then(response => {
                    return response.json()
                })
                .then(result => {
                    buildTable(result)
                })
                .catch(err => {
                    console.error("Error while fetching data for the rating table");
                    console.log(err)
                })
        })
    });
}

window.onload = main;
