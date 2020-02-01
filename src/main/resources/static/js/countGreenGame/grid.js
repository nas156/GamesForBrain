function Grid(topLeft, botRight, size, tileNum, round) {
  this.bounds = [topLeft, botRight];
  this.size = size;
  this.tiles = [];
  this.greens = 0;
  this.stage = 0;
  this.tileNum = tileNum;

  let stepX = (this.bounds[1][0] - this.bounds[0][0]) / this.size;
  let stepY = (this.bounds[1][1] - this.bounds[0][1]) / this.size;
  let y = this.bounds[0][1];
  for (let i = 0; i < this.size; i++) {
    let row = [];
    let x = this.bounds[0][0];
    for (let j = 0; j < this.size; j++) {
      row.push(new Tile(x + stepX/2, y + stepY/2, stepX - 10));
      x += stepX;
    }
    this.tiles.push(row);
    y += stepY;
  }


  this.genInRange = (start, stop) => {
    // generates int numbers in range [start, stop)
    return Math.floor(Math.random() * (stop - start) + start)
  };

  this.notGreenColor = [[255, 0, 0],[0, 0, 255], [255, 200, 0]][this.genInRange(0, 3)];


  this.setRandomTile = (type) =>{
    let x = this.genInRange(0, this.size);
    let y = this.genInRange(0, this.size);
    if (this.tiles[x][y].type === false){
      this.tiles[x][y].setType(type);
      if (type === 'notGreen'){
        this.tiles[x][y].setColor(this.notGreenColor);
      }
      return true
    } else {
      return false
    }
  };

  this.generateTiles = () => {
    // generates green and not green colored tile
    this.greens = this.genInRange(this.tileNum[0], this.tileNum[1]);
    let notGreens = this.genInRange(this.tileNum[0], this.tileNum[1]);
    let i = 0;
    while (i < this.greens){
      if (this.setRandomTile('green')){
        i += 1;
      }
    }
    i = 0;
    while (i < notGreens){
      if (this.setRandomTile('notGreen')){
        i += 1;
      }
    }
  };

  this.generateTiles();

  this.draw = () => {
    for (row of this.tiles){
      for (let tile of row){
        tile.draw();
      }
    }
    this.stage = 2;
  }
}

function Tile(posX, posY, size) {
  this.size = size;
  this.posX = posX - this.size/2;
  this.posY = posY - this.size/2;
  this.type = false;
  this.green = [0, 170, 0];
  this.noColor = [228];
  this.color = false;

  this.setType = (type) => {
    this.type = type
  };

  this.setColor = (color) => {
    this.color = color;
  };

  this.draw = () =>{
    push();
    if (this.type === 'green'){
      fill(...this.green);
    } else if (this.type === 'notGreen'){
      fill(...this.color);
    } else {
      fill(...this.noColor);
    }
    noStroke();
    rect(this.posX, this.posY, this.size, this.size, 5);
    pop()
  }
}

