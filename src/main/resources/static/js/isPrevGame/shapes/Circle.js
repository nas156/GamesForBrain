function Circle() {
  this.diameter = CANVAS_HEIGHT * 3 / 5;

  this.draw = color => {
    noStroke();
    fill(color);
    const location_x = CANVAS_WIDTH / 2;
    const location_y = CANVAS_HEIGHT / 2;
    circle(location_x, location_y, this.diameter);
  };
}