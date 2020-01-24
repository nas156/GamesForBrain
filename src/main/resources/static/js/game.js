function game(rng, width, height, delay, numbersAmount) {  // delay in milliseconds
    this.init = function(){
        // creation arguments
        this.rng = rng;
        this.width = width;
        this.height = height;
        this.delay = delay;
        this.numbersAmount = numbersAmount - 1;  // because incrementing just after start of the game

        // global parameters
        this.numOfGames = 0;
        this.totalScore = 0;
        this.avgScore = 0;
        this.accuracy = 0;
    }

    this.newGame = function () {
        // changing global params
        this.numOfGames += 1;
        this.numbersAmount += 1

        this.genTime = 0;
        this.counter = 0;
        this.numArr = [];
        this.currentNum = 0;
        this.stages = ["justAfter1Stage", "UserInputStage", "SubmittingNumber",
            "CorrectNum", "InCorrectNum", "ResultStage", "End game"]
        this.stage = -1;

        // for userInputStage
        this.pressedDigits = [];
        this.correctAnswers = 0;
    }
    this.init();
    this.newGame();

    this.generateNum = function () {
        return Math.floor(Math.random() * this.rng);
    }

    this.drawNumberFromDigit = function () {
        textSize(40);
        textAlign(CENTER, CENTER);
        fill(0, 52, 123);
        text(this.pressedDigits.join(separator = ""), WIDTH / 2, HEIGHT / 2);
    }

    this.drawNumber = function (num) {
        textSize(40);
        textAlign(CENTER, CENTER);
        fill(0, 52, 123);
        text(num, WIDTH / 2, HEIGHT / 2);
    }

    this.drawUserInput = function () {
        textSize(20);
        textAlign(CENTER, CENTER);
        fill(0, 52, 123);
        text("Type the numbers", WIDTH / 2, HEIGHT / 2);
    }

    this.drawCorrect = function () {
        textSize(40);
        textAlign(CENTER, CENTER);
        fill(50, 200, 20);
        text("Correct!", WIDTH / 2, HEIGHT / 2);
    }

    this.drawInCorrect = function () {
        textSize(40);
        textAlign(CENTER, CENTER);
        fill(200, 20, 20);
        text("Wrong!", WIDTH / 2, HEIGHT / 2);
    }

    this.drawResult = function () {
        textSize(40);
        textAlign(CENTER, CENTER);
        fill(0, 52, 123);
        let res = "Correct answers: " + this.correctAnswers;
        text(res, WIDTH / 2, HEIGHT / 2);
    }

    this.drawHeader = function(){
        textSize(15);
        textAlign(CENTER, CENTER);
        fill(0, 52, 123);
        let txt = "Games played: " + this.numOfGames;
        text(txt, WIDTH / 4, HEIGHT / 8);
        txt = "Numbers amount: " + this.numbersAmount;
        text(txt, WIDTH * 0.75, HEIGHT / 8);
        textSize(18);
        txt = "Total score: " + this.totalScore;
        text(txt, WIDTH / 2, HEIGHT * 0.06);
    }

    // in keyTyped func
    this.userInputStage = function () {
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
                } else {
                    this.stage = 4;  //stage InCorrectNum
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
        if (this.counter === this.numArr.length - 1) {
            this.stage = 5;
            this.totalScore += this.correctAnswers;
            this.avgScore = (this.totalScore / this.numOfGames).toFixed(2);


        }
    };

    // in main draw
    this.draw = function () {
        this.drawHeader();
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