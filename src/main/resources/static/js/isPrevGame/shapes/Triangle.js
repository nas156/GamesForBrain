function Triangle() {
  this.side_lenght = CANVAS_HEIGHT * 3 / 5;

  this.draw = color => {
    noStroke();
    fill(color);
    const point1X = (CANVAS_WIDTH - this.side_lenght) / 2;
    const point1Y = CANVAS_HEIGHT - (CANVAS_HEIGHT - this.side_lenght) / 2;
    const point2X = point1X + this.side_lenght / 2;
    const point2Y = point1Y - Math.sqrt(Math.pow(this.side_lenght, 2) - Math.pow(this.side_lenght / 2, 2));
    const point3X = point2X + this.side_lenght / 2;
    const point3Y = point1Y;
    triangle(point1X, point1Y, point2X, point2Y, point3X, point3Y);
  };
}