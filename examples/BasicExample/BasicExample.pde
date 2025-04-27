import paperandpencil.*;

PaperAndPencil pp;
float margin = 30;
float size = 100;

// Function to draw text with background
void labelText(String txt, float x, float y) {
  pushMatrix();
  // Move to the label position
  translate(x, y);
  
  float textW = textWidth(txt);
  fill(360, 80);  // White background with some transparency
  noStroke();
  rect(-textW/2 - 5, -15, textW + 10, 20);
  fill(0);
  text(txt, 0, 0);
  popMatrix();
}

void drawExample() {
  println("Starting drawExample()");
  background(360);  // Clear the canvas with white
  pp.paper();
  
  // Set text properties
  textAlign(CENTER);
  textSize(12);
  
  // Default pencil color
  pp.setPencilColor(color(0, 0, 0, 30));
  
  println("Drawing 1st row: Lines");
  pushMatrix();
  translate(margin, margin);
  {
    // Simple line
    println("  Drawing simple line");
    pp.line(0, 0, size, size, false);
    labelText("Simple line", size/2, size + 20);

    // Line with fade
    println("  Drawing line with fade");
    translate(margin + size, 0);
    pp.line(0, 0, size, size, true);
    labelText("Line with fade", size/2, size + 20);
  }
  popMatrix();
  
  println("Drawing 2nd row: Circles");
  pushMatrix();
  translate(margin, 2*margin + size);
  {
    // Simple circle
    println("  Drawing simple circle");
    pp.circle(size/2, size/2, size, false);
    labelText("Simple circle", size/2, size + 20);

    // Circle with fade
    println("  Drawing circle with fade");
    translate(margin + size, 0);
    pp.circle(size/2, size/2, size, true);
    labelText("Circle with fade", size/2, size + 20);

    // Filled circle
    println("  Drawing filled circle");
    translate(margin + size, 0);
    pp.fillCircle(size/2, size/2, size);
    labelText("Filled circle", size/2, size + 20);
  }
  popMatrix();
  
  println("Drawing 3rd row: Arcs");
  pushMatrix();
  translate(margin, 3*margin + 2*size);
  {
    // Simple arc
    println("  Drawing simple arc");
    pp.arc(size/2, size/2, size, -QUARTER_PI, PI, false);
    labelText("Simple arc", size/2, size + 20);

    // Arc with fade
    println("  Drawing arc with fade");
    translate(margin + size, 0);
    pp.arc(size/2, size/2, size, -QUARTER_PI, PI, true);
    labelText("Arc with fade", size/2, size + 20);
  }
  popMatrix();
  
  println("Drawing 4th row: Rectangles");
  pushMatrix();
  translate(margin, 4*margin + 3*size);
  {
    // Simple rect
    println("  Drawing simple rect");
    pp.rect(0, 0, size, size);
    labelText("Simple rect", size/2, size + 20);

    // Filled rect
    println("  Drawing filled rect");
    translate(margin + size, 0);
    pp.fillRect(0, 0, size, size);
    labelText("Filled rect", size/2, size + 20);
  }
  popMatrix();
  
  println("Drawing 5th row: Bézier curves");
  pushMatrix();
  translate(margin, 5*margin + 4*size);
  {
    float curveHeight = size/2;
    
    // Simple bezier
    println("  Drawing simple bezier");
    pp.bezier(0, size/2,             // Start point
              size/3, 0,             // Control point 1
              2*size/3, size,        // Control point 2
              size, size/2,          // End point
              false);                // No fade
    labelText("Simple bezier", size/2, size + 20);

    // Bezier with fade
    println("  Drawing bezier with fade");
    translate(margin + size, 0);
    pp.bezier(0, size/2,             // Start point
              size/3, 0,             // Control point 1
              2*size/3, size,        // Control point 2
              size, size/2,          // End point
              true);                 // With fade
    labelText("Bezier with fade", size/2, size + 20);
  }
  popMatrix();
  
  println("Drawing 6th row: Splines");
  pushMatrix();
  translate(margin, 6*margin + 5*size);
  {
    // Simple spline
    println("  Drawing simple spline");
    float[] points = {
      0, size/2,           // First point
      size/3, size/4,      // Second point
      2*size/3, 3*size/4,  // Third point
      size, size/2         // Fourth point
    };
    pp.spline(points, false, false);
    labelText("Simple spline", size/2, size + 20);

    // Full fade spline
    println("  Drawing full fade spline");
    translate(margin + size, 0);
    float[] fadePoints = {
      0, size/2,           // First point
      size/3, size/4,      // Second point
      2*size/3, 3*size/4,  // Third point
      size, size/2         // Fourth point
    };
    pp.spline(fadePoints, true, false);
    labelText("Full fade", size/2, size + 20);

    // First segment fade spline
    println("  Drawing first segment fade spline");
    translate(margin + size, 0);
    float[] firstSegmentFadePoints = {
      0, size/2,           // First point
      size/3, size/4,      // Second point
      2*size/3, 3*size/4,  // Third point
      size, size/2         // Fourth point
    };
    pp.spline(firstSegmentFadePoints, true, true);
    labelText("First segment fade", size/2, size + 20);
  }
  popMatrix();
  
  println("Drawing 7th row: Masking examples");
  pushMatrix();
  translate(margin, 7*margin + 6*size);
  {
    // Hard mask (circular)
    println("  Creating circular mask");
    PGraphics mask = pp.resetMask();
    mask.noStroke();
    mask.fill(255);
    mask.circle(size/2, size/2, size);
    println("  Enabling circular mask");
    pp.useMask();
    
    // Draw diagonal lines inside mask
    println("  Drawing vertical lines with circular mask");
    for (int i = 0; i <= 10; i++) {
      float x = i*(size/10);
      pp.line(x, 0, x, size, false);
    }
    labelText("Hard mask", size/2, size + 20);
    
    // Soft mask (gradient)
    println("  Creating gradient mask");
    translate(margin + size, 0);
    mask = pp.resetMask();
    mask.beginDraw();
    for (int y = 0; y < size; y++) {
      float alpha = map(y, 0, size, 255, 0);
      mask.stroke(255, 255, 255, alpha);
      mask.line(0, y, size, y);
    }
    mask.endDraw();
    println("  Enabling gradient mask");
    pp.useMask();
    
    // Draw vertical lines with gradient mask
    println("  Drawing vertical lines with gradient mask");
    for (int i = 0; i <= 10; i++) {
      float x = i*(size/10);
      pp.line(x, 0, x, size, false);
    }
    labelText("Soft mask", size/2, size + 20);
  }
  popMatrix();

  pp.resetMask();  // Reset mask for future drawings
  println("Finished drawExample()");
}

void setup() {
  size(450, 950, P2D);
  colorMode(HSB, 360, 100, 100, 100);
  
  pp = new PaperAndPencil(this);
  
  // Generate and save for each quality mode
  println("Drawing DRAFT quality...");
  pp.setQualityMode(PaperAndPencil.QualityMode.DRAFT);
  drawExample();
  save("BasicExample_DRAFT.png");
  
  println("Drawing SCREEN quality...");
  pp.setQualityMode(PaperAndPencil.QualityMode.SCREEN);
  drawExample();
  save("BasicExample_SCREEN.png");
  
  println("Drawing PRINT quality...");
  pp.setQualityMode(PaperAndPencil.QualityMode.PRINT);
  drawExample();
  save("BasicExample_PRINT.png");
  
  println("All examples completed");
  exit();
}

void draw() {
  // Static sketch, no animation needed
}