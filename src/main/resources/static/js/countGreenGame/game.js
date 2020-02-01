function Game(width, height) {
  // init
  this.width = width;
  this.height = height;
  this.txtSize = width / 30;
  this.headerTxtSize = width / 25;
  this.txtFill = [75, 111, 255];
  this.stages = ["StartScreen", "Show round", "GuessHowMany"];
  this.gameDuration = 30; // in seconds
  this.gridPadding = 100;
  this.gridPaddingTop = 100;
  this.gridSizes = [5,5,5,5,6,6,6,6,6,7,7,7,7,7,7,8,8,8,8,8,8,8];
  this.tilesNumber = [
    [3,6], [4,7], [5,8], [6,9],
    [4,7], [5,8], [6,9], [7,10], [8,11],
    [5,8], [6,9], [7,10], [8,11], [9, 12], [10, 13],
    [6,9], [7,10], [8,11], [9, 12], [10, 13], [11, 14], [12, 15]
  ];

  this.init = () => {
    this.buttons = [];
    this.score = 0;
    this.startTime = new Date().getTime();
    this.isResultSent = false;
    this.stage = 0;
    this.round = 1;
    this.timeLeft = 100000;
  };

  this.init();

  this.newGrid = () =>{
    this.stage = 1;
    this.grid = new Grid(
      [this.gridPadding, this.gridPaddingTop],
      [this.width - this.gridPadding, this.gridPaddingTop
      + this.height - this.gridPadding * 2],
      this.gridSizes[this.round-1],
      this.tilesNumber[this.round-1],
      this.round);
    this.grid.stage = 1;
  };

  this.newRound = () => {
    this.round += 1;
    this.newGrid();
  };



  this.drawStartScreen = () => {
    textSize(this.txtSize);
    textAlign(CENTER, CENTER);
    fill(...this.txtFill);
    let txt = "Your task is to count how many green tiles are in the screen\n"
      + "You have "+ this.gameDuration +" seconds\n"
      + "Press 'Enter' to start";

    text(txt, this.width / 2, this.height / 2);
  };

  this.drawHeader = () => {
    textSize(this.headerTxtSize);
    textAlign(CENTER, CENTER);
    fill(...this.txtFill);
    let txt = "Round: " + this.round;
    text(txt, this.width * 0.25, this.height / 12);
    txt = "Score: " + this.score;
    text(txt, this.width * 0.5, this.height / 12);
    this.timeLeft = (this.gameDuration - (new Date().getTime() - this.startTime) / 1000).toFixed(1);
    txt = "Time left: " + this.timeLeft;
    text(txt, this.width * 0.75, this.height / 12);
  };

  this.drawGameOver = () => {
    textSize(this.txtSize);
    textAlign(CENTER, CENTER);
    fill(...this.txtFill);
    let txt = "Time is up or you passed all rounds\n"
              + "Your score: " + this.score + "\n"
              + "Press 'Enter' to restart test";
    text(txt, this.width / 2, this.height / 2);
  };

  this.drawButtons = () => {
    for (let btn of this.buttons){
      btn.draw();
    }
  };

  this.mouseClicked = () => {
    if (this.stage === 2){

      let mousePos = [mouseX, mouseY];
      let res = false;
      for (let btn of this.buttons){
        if (btn.isUnderMouse(mousePos) && this.grid.greens === btn.number){
          res = 'correct';
        } else if (btn.isUnderMouse(mousePos) && this.grid.greens !== btn.number){
          res = 'inCorrect';
        }
      }
      if (res === 'correct'){
        this.score += this.round * 10;
        this.newRound();
      } else if (res === 'inCorrect'){
        this.score -= this.round * 5
      }
    }
  };

  this.keyTyped = () => {
    if (this.stage === 0 && keyCode === 13){
      this.startTime = new Date().getTime();
      this.newGrid();
    } else if (this.stage === 10 && keyCode === 13){
      this.init();
      this.newGrid();
    }
  };

  this.draw = () => {
    if (this.timeLeft > 0 && this.round < this.gridSizes.length){
      switch (this.stage) {
        case(0):
          this.drawStartScreen();
          break;
        case(1):
          this.grid.draw();
          if (this.grid.stage === 2){
            this.stage = 2;
            let xShift = 0;
            let xStep = (this.width - 2 * this.gridPadding) / 4;
            for (let i = 0; i < 4; i++) {
              this.buttons.push(
                new Button(
                  this.width * 0.234 + xShift,
                  this.height * 0.93,
                  this.tilesNumber[this.round-1][0] + i,
                  this.width,
                  this.height
                )
              );
              xShift += xStep;
            }
          }
          break;
        case(2):
          this.drawHeader();
          this.grid.draw();
          this.drawButtons();
      }
    } else {
      this.drawGameOver();
      this.stage = 10;
      if (!this.isResultSent){
        // sending result
        this.isResultSent = true
      }
    }
  };
}

function Button(posX, posY, number, width, height) {
  this.posX = posX;
  this.posY = posY;
  this.number = number;

  this.btnColor = [75, 111, 255];
  this.btnHoverColor = [60, 90, 240];
  this.txtSize = 24;
  // this.sizeX = 100;
  // this.sizeY = 60;
  this.sizeX = width / 7;
  this.sizeY = height / 10;

  this.isUnderMouse = (mousePos) => {
    return (mousePos[0] > posX - this.sizeX / 2) && (mousePos[0] < this.sizeX / 2 + posX)
      && (mousePos[1] > posY - this.sizeY / 2) && (mousePos[1] < this.sizeY / 2 + posY);
  };

  this.draw = () => {
    push();
    noStroke();
    if (this.isUnderMouse([mouseX, mouseY])){
      fill(...this.btnHoverColor)
    }else {
      fill(...this.btnColor);
    }
    rect(this.posX - this.sizeX/2, this.posY - this.sizeY/2, this.sizeX, this.sizeY, 5);
    fill(0);
    textAlign(CENTER, CENTER);
    textSize(this.txtSize);
    text(number, this.posX, this.posY);
    pop()
  };
}