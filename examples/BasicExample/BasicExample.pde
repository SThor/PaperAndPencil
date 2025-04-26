import paperandpencil.*;

PaperAndPencil pp;

// Function to draw text with background
void labelText(String txt, float x, float y) {
  float textW = textWidth(txt);
  fill(360, 80);  // White background with some transparency
  noStroke();
  rect(x - textW/2 - 5, y - 15, textW + 10, 20);
  fill(0);
  text(txt, x, y);
}

void setup() {
  size(450, 700, P2D);
  colorMode(HSB, 360, 100, 100, 100);
  
  pp = new PaperAndPencil(this);
  
  // Create paper background
  pp.paper();
  
  // Set default pencil color
  pp.setPencilColor(color(0, 0, 0, 30));
  
  float margin = 30;
  float size = 100;
  
  // Set text properties
  textAlign(CENTER);
  textSize(12);
  
  // 1st row: Lines
  pp.line(margin, margin, margin + size, margin + size);
  labelText("Simple line", margin + size/2, margin + size + 20);
  
  pp.line(2*margin + size, margin, 2*margin + 2*size, margin + size, true);
  labelText("Line with fade", 2*margin + size + size/2, margin + size + 20);
  
  // 2nd row: Circles
  pp.circle(margin + size/2, 2*margin + size + size/2, size, false);
  labelText("Simple circle", margin + size/2, 2*margin + 2*size + 20);
  
  pp.circle(2*margin + size + size/2, 2*margin + size + size/2, size, true);
  labelText("Circle with fade", 2*margin + size + size/2, 2*margin + 2*size + 20);
  
  pp.fillCircle(3*margin + 2*size + size/2, 2*margin + size + size/2, size);
  labelText("Filled circle", 3*margin + 2*size + size/2, 2*margin + 2*size + 20);
  
  // 3rd row: Arcs
  pp.arc(margin + size/2, 3*margin + 2*size + size/2, size, -QUARTER_PI, PI, false);
  labelText("Simple arc", margin + size/2, 3*margin + 3*size + 20);
  
  pp.arc(2*margin + size + size/2, 3*margin + 2*size + size/2, size, -QUARTER_PI, PI, true);
  labelText("Arc with fade", 2*margin + size + size/2, 3*margin + 3*size + 20);

  // 4th row: Rectangles
  pp.rect(margin, 4*margin + 3*size, size, size);
  labelText("Simple rect", margin + size/2, 4*margin + 4*size + 20);

  pp.fillRect(2*margin + size, 4*margin + 3*size, size, size);
  labelText("Filled rect", 2*margin + size + size/2, 4*margin + 4*size + 20);

  // 5th row: Bézier curves
  float curveHeight = size/2;  // Control height of the curve

  pp.bezier(margin, 5*margin + 4*size + size/2,           // Start point
            margin + size/3, 5*margin + 4*size,           // Control point 1
            margin + 2*size/3, 5*margin + 5*size,         // Control point 2
            margin + size, 5*margin + 4*size + size/2,    // End point
            false);                                       // No fade
  labelText("Simple bezier", margin + size/2, 5*margin + 5*size + 20);

  pp.bezier(2*margin + size, 5*margin + 4*size + size/2,           // Start point
            2*margin + size + size/3, 5*margin + 4*size,           // Control point 1
            2*margin + size + 2*size/3, 5*margin + 5*size,         // Control point 2
            2*margin + 2*size, 5*margin + 4*size + size/2,         // End point
            true);                                                 // With fade
  labelText("Bezier with fade", 2*margin + size + size/2, 5*margin + 5*size + 20);
  
}

void draw() {
  // Static sketch, no animation needed
}

void keyPressed() {
  if (key == 's' || key == 'S') {
    save("BasicExample.png");
  }
}