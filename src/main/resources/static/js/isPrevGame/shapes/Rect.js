function Rect() {
  this.height = CANVAS_HEIGHT*3/5;
  this.width = CANVAS_WIDTH*3/5;

  this.draw = color => {
    noStroke();
    fill(color);
    const location_x = (CANVAS_WIDTH - this.width)/2;
    const location_y = (CANVAS_HEIGHT - this.height)/2;
    rect(location_x, location_y, this.width, this.height);
  };
}