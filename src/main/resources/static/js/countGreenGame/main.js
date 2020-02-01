let game;

function setup() {
  const WIDTH = 700;
  const HEIGHT = 700;
  let canvas = createCanvas(WIDTH, HEIGHT);
  canvas.parent('game');
  game = new Game(WIDTH, HEIGHT);
}

function draw() {
  background(228);
  game.draw();
}

function keyTyped() {
  game.keyTyped();
}

function mouseClicked() {
  game.mouseClicked();
}


