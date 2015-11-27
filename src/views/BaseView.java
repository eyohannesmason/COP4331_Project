package views;


import javax.swing.*;
import java.awt.*;

public abstract class BaseView extends JPanel {
    public BaseView(LayoutManager layout) {
        this.setLayout(layout);
        this.setPreferredSize(getPreferredSize());
        this.setMaximumSize(getPreferredSize());
    }

    public Dimension getPreferredSize() {
        int w = 0;
        int h = 0;
        for(int i = 0; i < this.getComponents().length; i++) {
            w += this.getComponent(i).getPreferredSize().getWidth();
            h += this.getComponent(i).getPreferredSize().getHeight();
        }
        return new Dimension(w, h);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    protected abstract void createComponents();
}
