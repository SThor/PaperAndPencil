package paperandpencil;

import processing.core.PApplet;

/**
 * PaperAndPencil provides utilities for creating paper-like textures and pencil-like drawing effects
 * in Processing sketches. It simulates natural drawing techniques through various methods that add
 * randomness and imperfection to standard geometric shapes.
 */
public class PaperAndPencil {
    PApplet p;
    int pencilColor;
    boolean printMode;
    
    /**
     * Creates a new PaperAndPencil instance.
     * 
     * @param p The PApplet instance (usually 'this' in your sketch)
     */
    public PaperAndPencil(PApplet p) {
        this.p = p;
        this.pencilColor = p.color(0, 0, 0, 30);
        this.printMode = false;
    }
    
    /**
     * Creates a textured paper background effect by randomly placing small colored circles
     * and then applying a lightening effect to create a natural paper texture.
     */
    public void paper() {
        p.noStroke();
        for (int i = 0; i < 100000; ++i) {
            p.fill(p.random(360), p.random(100), p.random(100), p.random(20));
            float x = p.random(p.width);
            float y = p.random(p.height);
            p.circle(x, y, p.random(2));
        }
        p.loadPixels();
        for (int i = 0; i < p.pixels.length; i += 1) {
            p.pixels[i] = p.lerpColor(p.pixels[i], p.color(360), p.random(.5f));
        }
        p.updatePixels();
    }

    /**
     * Sets the color used for pencil strokes.
     * 
     * @param c The color to use for pencil strokes (use color() to create)
     */
    public void setPencilColor(int c) {
        this.pencilColor = c;
    }

    /**
     * Enables or disables print mode. When enabled, drawing operations use higher quality
     * settings suitable for print output.
     * 
     * @param mode true for print mode, false for screen mode
     */
    public void setPrintMode(boolean mode) {
        this.printMode = mode;
    }

    /**
     * Draws a circle with a pencil-like effect.
     * 
     * @param centerX x-coordinate of the circle center
     * @param centerY y-coordinate of the circle center
     * @param diameter diameter of the circle
     * @param fade if true, applies a fade effect to the stroke
     */
    public void circle(float centerX, float centerY, float diameter, boolean fade) {
        arc(centerX, centerY, diameter, 0, p.TWO_PI, fade);
    }

    /**
     * Draws an arc with a pencil-like effect.
     * 
     * @param centerX x-coordinate of the arc center
     * @param centerY y-coordinate of the arc center
     * @param diameter diameter of the arc
     * @param startAngle starting angle in radians
     * @param endAngle ending angle in radians
     * @param fade if true, applies a fade effect to the stroke
     */
    public void arc(float centerX, float centerY, float diameter, float startAngle, float endAngle, boolean fade) {
        p.noStroke();
        float x, y;
        
        // Set initial fill color if not fading
        if (!fade) {
            p.fill(pencilColor);
        }
        
        for (float theta = startAngle; theta < endAngle; theta += 0.3f/diameter) {
            if (fade) {
                float fadeProgress = (theta - startAngle) / (endAngle - startAngle);
                p.fill(p.hue(pencilColor), p.saturation(pencilColor), 
                    p.brightness(pencilColor), 
                    p.alpha(pencilColor) * fadeProgress);
            }
            
            x = centerX + diameter/2 * p.cos(theta);
            y = centerY + diameter/2 * p.sin(theta);
            p.circle(x + p.random(2), y + p.random(2), p.random(2));
        }
    }

    /**
     * Draws a line with a pencil-like effect.
     * 
     * @param x1 x-coordinate of the start point
     * @param y1 y-coordinate of the start point
     * @param x2 x-coordinate of the end point
     * @param y2 y-coordinate of the end point
     * @param fade if true, applies a fade effect from start to end
     */
    public void line(float x1, float y1, float x2, float y2, boolean fade) {
        p.noStroke();
        if (!fade) {
            p.fill(pencilColor);
        }
        
        float x, y;
        float increment = 0.15f / p.dist(x1, y1, x2, y2);
        
        for (float amt = 0; amt < 1; amt += increment) {
            if (fade) {
                p.fill(p.hue(pencilColor), p.saturation(pencilColor), 
                    p.brightness(pencilColor), 
                    p.alpha(pencilColor) * amt);
            }
            
            x = p.lerp(x1, x2, amt);
            y = p.lerp(y1, y2, amt);
            p.circle(x + p.random(2), y + p.random(2), p.random(2));
        }
    }

    /**
     * Draws a line with a pencil-like effect.
     * 
     * @param x1 x-coordinate of the start point
     * @param y1 y-coordinate of the start point
     * @param x2 x-coordinate of the end point
     * @param y2 y-coordinate of the end point
     */
    public void line(float x1, float y1, float x2, float y2) {
        line(x1, y1, x2, y2, false);
    }

    /**
     * Draws a rectangle with a pencil-like effect.
     * 
     * @param leftX x-coordinate of the rectangle's top-left corner
     * @param topY y-coordinate of the rectangle's top-left corner
     * @param width width of the rectangle
     * @param height height of the rectangle
     */
    public void rect(float leftX, float topY, float width, float height) {
        line(leftX, topY, leftX+width, topY, false);
        line(leftX, topY, leftX, topY+height, false);
        line(leftX+width, topY, leftX+width, topY+height, false);
        line(leftX, topY+height, leftX+width, topY+height, false);
    }

    public void fillRect(float leftX, float topY, float width, float height) {
        float increment = 4f;//printMode ? 1.3f : 1.2f;
        float centerX = leftX + (width / 2);
        float centerY = topY + (height / 2);
        // draw some bigger and bigger rectangles until the whole area is filled
        for (float size = 2; size < Math.max(width, height); size += increment) {
            float rectWidth = Math.min(size, width);
            float rectHeight = Math.min(size, height);
            rect(centerX - (rectWidth / 2), centerY - (rectHeight / 2), rectWidth, rectHeight);
        }
    }

    /**
     * Fills a circle with concentric pencil circles to create a filled effect.
     * The spacing between circles is adjusted based on print mode.
     * 
     * @param centerX x-coordinate of the circle center
     * @param centerY y-coordinate of the circle center
     * @param diameter diameter of the circle
     */
    public void fillCircle(float centerX, float centerY, float diameter) {
        float increment = printMode ? 1.3f : 1.2f;
        for (float d = 2; d < diameter; d += increment) {
            circle(centerX, centerY, d, false);
        }
    }
}