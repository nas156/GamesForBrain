function Rhombus() {
  this.diagonal = CANVAS_WIDTH*3/10

  this.draw = color => {
    noStroke();
    fill(color);
    const point1X = CANVAS_WIDTH/2;
    const point1Y = CANVAS_HEIGHT/2-this.diagonal;
    const point2X = CANVAS_WIDTH/2 + this.diagonal;
    const point2Y = CANVAS_HEIGHT/2;
    const point3X = CANVAS_WIDTH/2;
    const point3Y = CANVAS_HEIGHT/2+this.diagonal;
    const point4X = CANVAS_WIDTH/2 - this.diagonal;
    const point4Y = CANVAS_HEIGHT/2;
    beginShape();
    vertex(point1X, point1Y);
    vertex(point2X, point2Y);
    vertex(point3X, point3Y);
    vertex(point4X, point4Y);
    endShape(CLOSE);
  };
}