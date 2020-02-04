const username = $("meta[name='username']").attr("content");
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

function game(rng, width, height, delay, numbersAmount, maxMistakes) {  // delay in milliseconds
  this.init = function () {
    // creation arguments
    this.rng = rng;
    this.width = width;
    this.height = height;
    this.delay = delay;
    this.numbersAmount = numbersAmount - 1;  // because incrementing just after start of the game
    this.maxMistakes = maxMistakes;
    this.txtSize = Math.floor(this.width / 17);

    // global parameters
    this.numOfGames = 0;
    this.totalScore = 0;
    this.avgScore = 0;
    this.accuracy = 0;
    this.mistakes = 0;
  };

  this.newGame = function () {
    // changing global params
    this.numOfGames += 1;
    this.numbersAmount += 1;

    this.genTime = 0;
    this.counter = 0;
    this.numArr = [];
    this.currentNum = 0;
    this.stages = ["justAfter1Stage", "UserInputStage", "SubmittingNumber",
      "CorrectNum", "InCorrectNum", "ResultStage", "End game"];
    this.stage = -1;

    // for userInputStage
    this.pressedDigits = [];
    this.correctAnswers = 0;
  };
  this.init();
  this.newGame();

  this.generateNum = function () {
    return Math.floor(Math.random() * this.rng);
  };

  this.drawNumberFromDigit = function () {
    textSize(Math.floor(this.txtSize * 1.5));
    textAlign(CENTER, CENTER);
    fill(75, 111, 255);
    text(this.pressedDigits.join(separator = ""), WIDTH / 2, HEIGHT / 2);
  };

  this.drawNumber = function (num) {
    textSize(Math.floor(this.txtSize * 1.5));
    textAlign(CENTER, CENTER);
    fill(75, 111, 255);
    text(num, WIDTH / 2, HEIGHT / 2);
  };

  this.drawUserInput = function () {
    textSize(Math.floor(this.txtSize * 1.5));
    textAlign(CENTER, CENTER);
    fill(75, 111, 255);
    text("Type the numbers", WIDTH / 2, HEIGHT / 2);
  };

  this.drawCorrect = function () {
    textSize(Math.floor(this.txtSize * 1.5));
    textAlign(CENTER, CENTER);
    fill(50, 200, 20);
    text("Correct!", WIDTH / 2, HEIGHT / 2);
  };

  this.drawInCorrect = function () {
    textSize(Math.floor(this.txtSize * 1.5));
    textAlign(CENTER, CENTER);
    fill(200, 20, 20);
    text("Wrong!", WIDTH / 2, HEIGHT / 2);
  };

  this.drawResult = function () {
    textSize(Math.floor(this.txtSize * 1.5));
    textAlign(CENTER, CENTER);
    fill(75, 111, 255);
    let res = "Correct answers: " + this.correctAnswers;
    text(res, WIDTH / 2, HEIGHT / 2);
  };

  this.drawHeader = function () {
    textSize(Math.floor(this.txtSize * 0.7));
    textAlign(CENTER, CENTER);
    fill(75, 111, 255);
    let txt = "Stage: " + this.numOfGames;
    text(txt, WIDTH * 0.2, HEIGHT / 8);
    txt = "Numbers amount: " + this.numbersAmount;
    text(txt, WIDTH * 0.5, HEIGHT / 8);
    txt = "Lives left: " + (this.maxMistakes - this.mistakes);
    text(txt, WIDTH * 0.8, HEIGHT / 8);
    txt = "Total score: " + this.totalScore;
    text(txt, WIDTH / 2, HEIGHT * 0.06);

  };

  this.drawEndGame = function () {
    textSize(Math.floor(this.txtSize * 0.7));
    textAlign(CENTER, CENTER);
    fill(75, 111, 255);
    let txt = "Game Over";
    text(txt, WIDTH / 2, HEIGHT / 2 - 40);
    txt = "Your score: " + this.totalScore;
    text(txt, WIDTH / 2, HEIGHT / 2);
    txt = "Press 'Enter' to restart test";
    text(txt, WIDTH / 2, HEIGHT / 2 + 40);
  };

  this.normalizeScore = (score) => {
    return parseInt(200 * Math.atan(0.2 * score) / Math.PI);
  };

  this.sendResult = () => {
    let score = this.totalScore;
    let headers = new Headers({
      'Accept': 'application/json, text/plain, */*',
      'Content-Type': 'application/json',
      [header] : token,
    });
    fetch('/createStatistic', {
      method: 'POST',
      mode: 'cors',
      headers,
      body: JSON.stringify({
        score: this.normalizeScore(score),
        username: username,
        testType: "RepeatNumbersTest"
      }),
    })
  };

  // in keyTyped func
  this.userInputStage = function () {
    if (this.stage === 6 && keyCode === 13){
      this.init();
      this.newGame();
    }
    if (this.stage === 0 || this.stage === 1) {
        this.pressedDigits.push(key);
        if (this.pressedDigits.length !== 0) {
          this.stage = 1;
        }
      if ((keyCode === 13)) {
        this.stage = 2;
        let userDigit = parseInt(this.pressedDigits.join(separator = ""));
        let realDigit = this.numArr[this.counter];
        if (userDigit === realDigit) {
          this.stage = 3;  // stage CorrectNum
          this.correctAnswers += 1;
          this.totalScore += 1;
        } else {
          this.stage = 4;  //stage InCorrectNum
          this.mistakes += 1;
          if (this.mistakes === this.maxMistakes + 1) {
            this.stage = 6;
            this.sendResult();
          }
        }
        this.counter += 1;
        this.pressedDigits = [];
      }
    } else if (this.stage === 3 || this.stage === 4) {
      if (keyIsPressed) {
        this.stage = 1;
        this.pressedDigits.push(key);
      }
    } else if (this.stage === 5) {
      if (keyCode === 13) {
        this.newGame();
      }
    }
    if (this.counter === this.numArr.length - 1 && !(this.stage === 6)) {
      this.stage = 5;

      this.avgScore = (this.totalScore / this.numOfGames).toFixed(2);
    }

  };

  // in main draw
  this.draw = function () {
    if (this.stage !== 6) {
      this.drawHeader();
    }
    if (this.stage >= 0) {
      if (this.stage === 0) {
        this.drawUserInput();
      } else if (this.stage === 1) {
        this.drawNumberFromDigit();
      } else if (this.stage === 3) {
        this.drawCorrect();
      } else if (this.stage === 4) {
        this.drawInCorrect();
      } else if (this.stage === 5) {
        this.drawResult();
      } else if (this.stage === 6) {
        this.drawEndGame();
      }
    } else {
      if ((new Date().getTime() - this.genTime) > this.delay) {
        this.currentNum = this.generateNum();
        this.genTime = new Date().getTime();
        this.numArr.push(this.currentNum);
        this.counter += 1;
      }
      if (this.counter - 1 === this.numbersAmount) {
        this.stage = 0;
        this.counter = 0;
      }
      this.drawNumber(this.currentNum);
    }
  };
}