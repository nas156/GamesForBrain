const CANVAS_WIDTH = 500;
const CANVAS_HEIGHT = 500;
let stage = 0;
let startTime = 0;
let endTime = 0;
let resultTime = 0;

let singlFunc = () => {
  self = {
    executable: true
  };
  self.run = (def) => {
    if (self.executable) {
      self.executable = false;
      def();
    }
  };
  return self
};

let singlTimeout = singlFunc();


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
  $("#gr").css({"background": "rgb(228,228,228)"});
  const delay = Math.random() * (maxDelayTime - minDelayTime) + minDelayTime;
  singlTimeout.run(() => {
      setTimeout(() => {
        startTime = Date.now();
        $("#gr").css({"background": "rgb(23, 194, 0)"});
        background(23, 194, 0); //green
        singlTimeout.executable = true;
        stage = 2;
      }, delay);
    }
  );
};

const greenPart = () => {
  background(23, 194, 0); //green
  document.addEventListener('keyup', function handler() {
    endTime = Date.now();
    resultTime = endTime - startTime;
    stage = 3;
    this.removeEventListener('keyup', handler);
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
  textSize(Math.floor(CANVAS_WIDTH / 17));
  fill(75, 111, 255);
  text(`Your result is ${resultTime}ms.\n Press any key to Restart`, CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
  textAlign(CENTER);
  document.addEventListener("keypress", function handler(e) {
    stage = 1;
    this.removeEventListener("keypress", handler);
  });
};