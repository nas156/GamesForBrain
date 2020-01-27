function main() {
  if (isStarted === false) {
    textAlign(CENTER, CENTER);
    textSize(24);
    fill(0, 52, 123);
    text('Try to memorize all numbers', WIDTH / 2, HEIGHT / 2);
    text('Press "Enter" to start', WIDTH / 2, HEIGHT / 2 + 50);
    if (keyIsDown(13)) {
      isStarted = true;
    }
  } else {
    game.draw();
  }
}

function keyTyped() {
  if (game.stage >= 0) {
    if (game.stages[game.stage]) {
      game.userInputStage();
    }
  }
}


function setup() {
  WIDTH = 400;
  HEIGHT = 400;
  isStarted = false;

  cnv = createCanvas(WIDTH, HEIGHT);
  cnv.parent('rep-num-game');
  game = new game(rng = 100,
    width = WIDTH,
    height = HEIGHT,
    delay = 600,
    numbersAmount = 3,
    maxMistakes = 1);
}

function draw() {
  background(220);
  main();
}
