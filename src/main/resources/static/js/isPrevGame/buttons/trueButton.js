function trueButton() {
  this.executable = true;
  this.button;

  this.draw = () => {
    if(this.executable){
      this.button = createButton("True");
      const posX = CANVAS_WIDTH/5;
      const posY = CANVAS_HEIGHT - CANVAS_HEIGHT/10;
      this.button.position(posX, posY);
      this.button.addClass("btn btn-success");
      this.button.mousePressed(compare);
      this.executable = false;
    }
    this.button.show();
  }

  const compare= () => {
    if( JSON.stringify(currentShape) === JSON.stringify(prevShape) ){
      score++;
      console.log(score);
      Game.randShape.executable = true;
      Game.singlChange.executable = true;
    } else {
      Game.singlChange.executable = true
      Game.randShape.executable = true;
      Game.singlChange.executable = true;
      this.button.hide();
      fb.button.hide();
      stage = 3;
    }
  }
}