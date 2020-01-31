function Timer() {

  this.run = () => {
    fill(75, 111, 255);
    textAlign(RIGHT);
    textSize(CANVAS_WIDTH / 22);
    text(time, CANVAS_WIDTH / 17, CANVAS_HEIGHT / 17);
    if (frameCount % 60 === 0 && time > 0) {
      time--;
    }
    if (time == 0) {
      Game.singlChange.executable = true;
      Game.randShape.executable = true;
      Game.singlChange.executable = true;
      tb.button.hide();
      fb.button.hide();
      stage = 3;
    }
  }
}