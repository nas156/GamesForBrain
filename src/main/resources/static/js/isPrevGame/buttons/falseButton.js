function falseButton() {
  this.executable = true;
  this.button;

  this.draw = () => {
    if (this.executable) {
      this.button = createButton("False");
      this.button.addClass("btn btn-danger");
      this.button.id("false");
      wi = $("#false");
      const posX = CANVAS_WIDTH - CANVAS_WIDTH / 5 - wi.width() * 2;
      const posY = CANVAS_HEIGHT - CANVAS_HEIGHT / 10;
      this.button.position(posX, posY);
      this.button.mousePressed(compare);
      this.executable = false;
    }
    this.button.show();
  };

  const compare = () => {
    if (JSON.stringify(currentShape) !== JSON.stringify(prevShape)) {
      score++;
      Game.randShape.executable = true;
      Game.singlChange.executable = true;
    } else {
      Game.singlChange.executable = true;
      Game.randShape.executable = true;
      Game.singlChange.executable = true;
      this.button.hide();
      tb.button.hide();
      stage = 3;
    }
  }
}