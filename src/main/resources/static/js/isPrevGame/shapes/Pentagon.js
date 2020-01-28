function Pentagon() {
	this.side_length = CANVAS_HEIGHT*2/5;

  this.draw = color => {

    function toRadians (angle) {
      return angle * (Math.PI / 180);
    } 
    noStroke();
    fill(color);
    const point1X = CANVAS_WIDTH/2;
    const point1Y = CANVAS_HEIGHT/2 - this.side_length;
    const point2X = CANVAS_WIDTH/2 + this.side_length * Math.cos(toRadians(18));
    const point2Y = CANVAS_HEIGHT/2 - this.side_length * Math.sin(toRadians(18));
    const point3X = CANVAS_WIDTH/2 + this.side_length * Math.cos(toRadians(-54));
    const point3Y = CANVAS_HEIGHT/2 - this.side_length * Math.sin(toRadians(-54));
    const point4X = CANVAS_WIDTH/2 - this.side_length * Math.cos(toRadians(-54));
    const point4Y = point3Y;
    const point5X = CANVAS_WIDTH/2 - this.side_length * Math.cos(toRadians(18));
    const point5Y = point2Y;
    beginShape();
    vertex(point1X, point1Y);
    vertex(point2X, point2Y);
    vertex(point3X, point3Y);
    vertex(point4X, point4Y);
    vertex(point5X, point5Y);
    endShape(CLOSE);
  };
}