function grid(size, topLeft, botRight, seqLen) {
  this.init = () => {
    this.size = size;
    this.bounds = [topLeft, botRight];  // top left point end bottom right point of square where grid will be drawn
    this.tiles = [];
    this.stage = 0;
    this.seqLen = seqLen;
    this.seq = [];

    this.delay = 500;
    this.counter = 0;
    this.timeStamp = new Date().getTime();
    this.showColor = [255, 0, 0];
    this.defaultColor = [75, 111, 255];
    this.guessColor = [0, 255, 0];
    const xStep = (this.bounds[1][0] - this.bounds[0][0]) / this.size;
    const yStep = (this.bounds[1][1] - this.bounds[0][1]) / this.size;
    let xPos = this.bounds[0][0];
    let yPos = this.bounds[0][1];
    this.guesses = [];

    for (let i = 0; i < this.size; i++) {
      let row = [];
      for (let j = 0; j < this.size; j++) {
        row.push(new tile([xPos + xStep / 16, yPos + yStep / 16], xStep * 0.875));
        xPos += xStep;
      }
      this.tiles.push(row);
      yPos += yStep;
      xPos = this.bounds[0][0];
    }
  };
  this.init();

  this.isInSeq = (a, b) => {
    flag = false;
    for (i of this.seq) {
      if ((i[0] === a) && i[1] === b) {
        flag = true;
      }
    }
    return flag
  };

  this.generateVector = () => {
    let a = Math.floor(Math.random() * this.size);
    let b = Math.floor(Math.random() * this.size);
    while (this.isInSeq(a, b)) {
      a = Math.floor(Math.random() * this.size);
      b = Math.floor(Math.random() * this.size);
    }
    return [a, b]
  };

  this.genSeq = () => {
    for (let i = 0; i < this.seqLen; i++) {
      [a, b] = this.generateVector();

      this.seq.push([a, b]);
    }

  };

  this.drawTiles = () => {
    for (let i = 0; i < this.size; i++) {
      for (let j = 0; j < this.size; j++) {
        noStroke();

        this.tiles[i][j].draw();
      }
    }
  };

  this.draw = () => {
    if (this.stage === 1) {
      this.tiles[this.seq[this.counter][0]][this.seq[this.counter][1]].color = this.showColor;
      this.drawTiles();
      if ((new Date().getTime() - this.timeStamp) > this.delay) {
        this.tiles[this.seq[this.counter][0]][this.seq[this.counter][1]].color = this.defaultColor;
        this.counter += 1;
        this.timeStamp = new Date().getTime();
        if (this.counter === this.seqLen) {
          this.stage = 2;
          this.counter = 0;
        }
      }
    } else if (this.stage === 2) {
      this.drawTiles();
    } else if (this.stage === 3) {
      flag = true;
      for (let i = 0; i < this.seq.length; i++) {
        for (let j = 0; j < this.seq.length; j++) {
          if (this.seq[i][j] !== this.guesses[i][j]) {
            flag = false;
          }
        }
      }

      if (flag === true) {
        this.stage = 4;
      } else {
        this.stage = 5;
      }
    }
  };

  this.mouseClicked = () => {
    if (this.stage === 2) {
      for (let i = 0; i < this.size; i++) {
        for (let j = 0; j < this.size; j++) {
          if (this.tiles[i][j].isUnderMouse()) {
            this.tiles[i][j].color = this.guessColor;
            this.counter += 1;
            this.tiles[i][j].number = this.counter;
            this.guesses.push([i, j]);
            if (this.counter === this.seqLen) {
              this.stage = 3;
            }
          }
        }
      }
    }
  }
}

function tile(pos, size) {
  this.pos = pos;
  this.size = size;
  this.rounding = 5;
  this.color = [75, 111, 255];
  this.number = 0;
  this.txtPos = [this.pos[0] + size / 2, this.pos[1] + size / 2];

  this.draw = () => {
    fill(...this.color);
    square(this.pos[0], this.pos[1], this.size, this.rounding);
    if (this.number !== 0) {
      fill(0);
      text(this.number, this.txtPos[0], this.txtPos[1])
    }
  };

  this.isUnderMouse = () => {
    if ((this.pos[0] < mouseX) && (this.pos[0] + this.size > mouseX)
      && (this.pos[1] < mouseY) && (this.pos[1] + this.size > mouseY)) {
      return true
    }
  }
}