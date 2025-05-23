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
    float pencilSpread;
    
    /**
     * Creates a new PaperAndPencil instance.
     * 
     * @param p The PApplet instance (usually 'this' in your sketch)
     */
    public PaperAndPencil(PApplet p) {
        this.p = p;
        this.pencilColor = p.color(0, 0, 0, 30);
        this.printMode = false;
        this.pencilSpread = 2f;
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
     * Gets the current pencil color.
     * 
     * @return The current pencil color
     */
    public int getPencilColor() {
        return this.pencilColor;
    }

    public void setPencilSpread(float spread) {
        this.pencilSpread = spread;
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

    public void dot(float x, float y) {
        p.circle(x + p.random(this.pencilSpread), y + p.random(this.pencilSpread), p.random(2));
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
        p.fill(pencilColor);
        float x, y;
        
        for (float theta = startAngle; theta < endAngle; theta += 0.3f/diameter) {
            if (fade) {
                float fadeProgress = (theta - startAngle) / (endAngle - startAngle);
                p.fill(p.hue(pencilColor), p.saturation(pencilColor), 
                    p.brightness(pencilColor), 
                    p.alpha(pencilColor) * fadeProgress);
            }
            
            x = centerX + diameter/2 * p.cos(theta);
            y = centerY + diameter/2 * p.sin(theta);
            dot(x, y);
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
        p.fill(pencilColor);
        
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
            dot(x, y);
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

    /**
     * Draws a cubic Bézier curve with a pencil-like effect.
     * 
     * @param x1 x-coordinate of the start point
     * @param y1 y-coordinate of the start point
     * @param cx1 x-coordinate of the first control point
     * @param cy1 y-coordinate of the first control point
     * @param cx2 x-coordinate of the second control point
     * @param cy2 y-coordinate of the second control point
     * @param x2 x-coordinate of the end point
     * @param y2 y-coordinate of the end point
     * @param fade if true, applies a fade effect from start to end
     */
    public void bezier(float x1, float y1, float cx1, float cy1, 
                      float cx2, float cy2, float x2, float y2, boolean fade) {
        p.noStroke();
        p.fill(pencilColor);
        
        // Approximate curve length by using the polygon length of control points
        float approxLength = p.dist(x1, y1, cx1, cy1) + 
                           p.dist(cx1, cy1, cx2, cy2) + 
                           p.dist(cx2, cy2, x2, y2);
        
        // Scale increment based on approximate curve length
        float increment = 0.15f / approxLength;
        
        // Use smaller increments for print mode
        if (printMode) {
            increment *= 0.5f;
        }
        
        for (float t = 0; t <= 1; t += increment) {
            if (fade) {
                p.fill(p.hue(pencilColor), p.saturation(pencilColor), 
                    p.brightness(pencilColor), 
                    p.alpha(pencilColor) * t);
            }
            
            float x = p.bezierPoint(x1, cx1, cx2, x2, t);
            float y = p.bezierPoint(y1, cy1, cy2, y2, t);
            dot(x, y);
        }
    }

    /**
     * Draws a smooth spline through a series of points using connected bézier curves.
     * The spline will pass through all given points while maintaining smooth transitions.
     * 
     * @param points Array of x,y coordinates in the format [x1,y1,x2,y2,...]. Must have at least 4 elements (2 points).
     * @param fade if true, applies a fade effect (either on the whole spline or just the last segment)
     * @param fadeFirstSegmentOnly if true and fade is true, only fades the first segment instead of the entire spline
     */
    public void spline(float[] points, boolean fade, boolean fadeFirstSegmentOnly) {
        if (points.length < 4 || points.length % 2 != 0) {
            return; // Need at least 2 points (4 coordinates) and even number of coordinates
        }

        int numSegments = (points.length - 2) / 2;
        float currentSegment = 0;

        // For each segment between points, calculate control points for smooth transition
        for (int i = 0; i < points.length - 3; i += 2) {
            float x0 = i > 0 ? points[i-2] : points[i];
            float y0 = i > 0 ? points[i-1] : points[i+1];
            float x1 = points[i];
            float y1 = points[i+1];
            float x2 = points[i+2];
            float y2 = points[i+3];
            float x3 = i < points.length - 4 ? points[i+4] : points[i+2];
            float y3 = i < points.length - 4 ? points[i+5] : points[i+3];

            // Calculate control points
            float tension = 0.5f;
            float cx1 = x1 + (x2 - x0) * tension;
            float cy1 = y1 + (y2 - y0) * tension;
            float cx2 = x2 - (x3 - x1) * tension;
            float cy2 = y2 - (y3 - y1) * tension;

            // Calculate fade parameters based on whether we're fading the whole spline or just the last segment
            float fadeStart = 1;
            float fadeEnd = 1;
            
            if (fade) {
                if (fadeFirstSegmentOnly) {
                    // Only fade if this is the first segment
                    if (currentSegment == 0) {
                        fadeStart = 0;
                        fadeEnd = 1;
                    }
                } else {
                    // Fade the entire spline
                    fadeStart = currentSegment / numSegments;
                    fadeEnd = (currentSegment + 1) / numSegments;
                }
            }
            
            // Draw the bezier curve segment with the appropriate fade range
            bezierSegment(x1, y1, cx1, cy1, cx2, cy2, x2, y2, fade, fadeStart, fadeEnd);
            currentSegment++;
        }
    }

    /**
     * Draws a smooth spline through a series of points using connected bézier curves.
     * This overload applies the fade effect to the entire spline when fade is true.
     * 
     * @param points Array of x,y coordinates in the format [x1,y1,x2,y2,...]. Must have at least 4 elements (2 points).
     * @param fade if true, applies a fade effect along the entire spline
     */
    public void spline(float[] points, boolean fade) {
        spline(points, fade, false);
    }

    /**
     * Internal method to draw a bezier segment with fade range control
     */
    private void bezierSegment(float x1, float y1, float cx1, float cy1, 
                             float cx2, float cy2, float x2, float y2, 
                             boolean fade, float fadeStart, float fadeEnd) {
        p.noStroke();
        p.fill(pencilColor);
        
        // Approximate curve length by using the polygon length of control points
        float approxLength = p.dist(x1, y1, cx1, cy1) + 
                           p.dist(cx1, cy1, cx2, cy2) + 
                           p.dist(cx2, cy2, x2, y2);
        
        // Scale increment based on approximate curve length
        float increment = 0.15f / approxLength;
        
        // Use smaller increments for print mode
        if (printMode) {
            increment *= 0.5f;
        }
        
        for (float t = 0; t <= 1; t += increment) {
            if (fade) {
                float fadeProgress = fadeStart + (fadeEnd - fadeStart) * t;
                p.fill(p.hue(pencilColor), p.saturation(pencilColor), 
                    p.brightness(pencilColor), 
                    p.alpha(pencilColor) * fadeProgress);
            }
            
            float x = p.bezierPoint(x1, cx1, cx2, x2, t);
            float y = p.bezierPoint(y1, cy1, cy2, y2, t);
            dot(x, y);
        }
    }
}