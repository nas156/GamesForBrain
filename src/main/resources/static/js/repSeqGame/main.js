var game;

function setup() {
    WIDTH = 600;
    HEIGHT = 600;
    var canvas = createCanvas(WIDTH, HEIGHT);
    // Move the canvas so it’s inside our <div id="sketch-holder">.
    canvas.parent('game');
    game = new game(width=WIDTH,
                    height=HEIGHT,
                    gridLen=3,
                    seqLen=3

    );
    game.newGame();

}

function draw() {
    background(80);
    game.draw();
}

function keyTyped() {
    game.keyTyped();
}

function mouseClicked() {
    game.mouseClicked();
}
