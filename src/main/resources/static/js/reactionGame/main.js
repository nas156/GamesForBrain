const CANVAS_WIDTH = 500;
const CANVAS_HEIGHT = 500;
let stage = 0;
let startTime = 0;
let endTime = 0;
let resultTime = 0;
const username = $("meta[name='username']").attr("content");
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let timeout;

function singlFunc() {
  this.executable = true;
  this.run = (def) => {
    if (this.executable) {
      this.executable = false;
      def();
    }
  };
};

let singlTimeout = new singlFunc();
let singlRequest = new singlFunc();


function setup() {
  let cnv = createCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);
  cnv.parent("canvas");
}

function draw() {
  switch (stage) {
    case 0:
      gameBegin();
      break;
    case 1:
      waitingPart(2000, 6000);
      break;
    case 2:
      greenPart();
      break;
    case 3:
      finalPart();
      break;
    case 4:
      invalidPress();
      break;
  }
}

const gameBegin = () => {
  background(228,228,228); //grey
  textSize(Math.floor(CANVAS_WIDTH / 17));
  fill(75, 111, 255);
  text("Press any key when you see green.\n Press Enter to start", CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
  textAlign(CENTER);
  document.addEventListener("keypress", function handler(e) {
    if (e.keyCode === 13) {
      stage = 1;
      this.removeEventListener("keypress", handler);
    }
  });
};

const waitingPart = (minDelayTime, maxDelayTime) => {
  background(228,228,228); //grey
  singlRequest.executable = true;
  $("#gr").css({"background": "rgb(228,228,228)"});
  const delay = Math.random() * (maxDelayTime - minDelayTime) + minDelayTime;
  singlTimeout.run(() => {
      timeout = setTimeout(() => {
        startTime = Date.now();
        $("#gr").css({"background": "rgb(23, 194, 0)"});
        background(23, 194, 0); //green
        singlTimeout.executable = true;
        stage = 2;
      }, delay);
    }
  );
  document.addEventListener('keydown', function handler() {
    stage = 4;
    clearTimeout(timeout);
    singlTimeout.executable = true;
    this.removeEventListener('keydown', handler);
  });
  document.addEventListener('click', function handler() {
    stage = 4;
    clearTimeout(timeout);
    singlTimeout.executable = true;
    this.removeEventListener('click', handler);
  });
};

const greenPart = () => {
  background(23, 194, 0); //green
  document.addEventListener('keydown', function handler() {
    endTime = Date.now();
    resultTime = endTime - startTime;
    stage = 3;
    this.removeEventListener('keydown', handler);
  });
  document.addEventListener('click', function handler() {
    endTime = Date.now();
    resultTime = endTime - startTime;
    stage = 3;
    this.removeEventListener('click', handler);
  });
};

const finalPart = () => {
  background(23, 194, 0); //green
  $("#gr").css({"background": "rgb(23, 194, 0)"});
  textSize(Math.floor(CANVAS_WIDTH / 17));
  fill(75, 111, 255);
  text(`Your result is ${resultTime}ms.\n Press any key to Restart`, CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
  textAlign(CENTER);
  let headers = new Headers({
    'Accept': 'application/json, text/plain, */*',
    'Content-Type': 'application/json',
    [header] : token,
  });


  document.addEventListener("keypress", function handler(e) {
    singlRequest.run( () => {
      fetch('/createStatistic', {
        method: 'POST',
        mode: 'cors',
        headers,
        body: JSON.stringify({
          score: resultTime,
          username: username,
          testType: "ReactionTest"
        }),
      });
      fetch('/createStatistic/statisticByUserForRepeatNumbers?type=ReactionTest', {
        method: 'GET',
        mode: 'cors',
        headers,
      }).then(response => response.json())
        .then(response => console.log(response));
    });
    stage = 1;
    this.removeEventListener("keypress", handler);
  });
};

const invalidPress = () => {
  background(228,228,228); //grey
  $("#gr").css({"background": "rgb(228,228,228)"});
  textSize(Math.floor(CANVAS_WIDTH / 17));
  fill(75, 111, 255);
  text(`Too early.\n Press any key to Restart`, CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
  document.addEventListener("keypress", function handler(e) {
    stage = 1;
    this.removeEventListener("keypress", handler);
  });
};