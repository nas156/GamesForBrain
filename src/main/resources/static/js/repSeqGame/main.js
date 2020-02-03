var game;

function setup() {
  WIDTH = 500;
  HEIGHT = 500;
  var canvas = createCanvas(WIDTH, HEIGHT);
  // Move the canvas so it’s inside our <div id="sketch-holder">.
  canvas.parent('canvas');
  game = new game(
    width = WIDTH,
    height = HEIGHT,
    gridLen = 3,
    seqLen = 3
  );
  game.newGame();

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
