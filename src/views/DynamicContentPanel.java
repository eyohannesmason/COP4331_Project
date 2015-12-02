package views;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;

public class DynamicContentPanel extends BaseView {
    public DynamicContentPanel() {
        super(new GridBagLayout());
        createComponents();
        this.setBorder(new CompoundBorder(new EmptyBorder(20,20,20,20), new MetalBorders.Flush3DBorder()));
    }

    @Override
    public void createComponents() {
        //Create Grid Bag Constraints
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        setContent(new InstrumentsView());
    }

    public void setContent(JPanel content) {
        this.removeAll();
        this.content = content;
        this.add(content, c);
        this.revalidate();
        this.repaint();
    }

    private JPanel content;
    GridBagConstraints c;
}
