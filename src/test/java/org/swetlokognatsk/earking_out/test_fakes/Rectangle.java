package org.swetlokognatsk.earking_out.test_fakes;

public record Rectangle(double width, double length) {
    public Rectangle {
        if (width <= 0D) {
            throw new IllegalArgumentException("width cannot be negative");
        }
        if (length <= 0D) {
            throw new IllegalArgumentException("length cannot be negative");
        }
    }
}