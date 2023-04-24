package nl.pokemon.mapGenerator.view;

import nl.pokemon.mapGenerator.model.Selection;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Service
public class SelectedPanel extends JPanel {

//    @Inject(name = "selectedItem")
//    Selection selection;
//
//    @Inject
//    MG_ViewService mgViewService;
//
//    private JLabel label;
//    public SelectedPanel() {
//
//        this.setPreferredSize(new Dimension(300,100));
//        label = new JLabel();
//        label.setText("selected");
//        label.setVisible(true);
//
//        JButton button = new JButton();
//        button.setText("GENERATE MAP!");
//        button.addActionListener(e -> {
//
//            StringBuilder builder = new StringBuilder("{");
//            for (int y = 0; y < mgViewService.getViewMap()[1].length; y++) {
//                if (mgViewService.getViewMap()[y][0].getObjectNumber() != 0)
//                    builder.append("{");
//                for (int x = 0; x < mgViewService.getViewMap().length; x++) {
//                    if (mgViewService.getViewMap()[y][x].getObjectNumber() != 0) {
//                        try {
//                            if (mgViewService.getViewMap()[y][x + 1].getObjectNumber() == 0) {
//                                builder.append(mgViewService.getViewMap()[y][x].getObjectNumber() + "");
//                            } else {
//                                builder.append(mgViewService.getViewMap()[y][x].getObjectNumber() + ", ");
//                            }
//                        } catch (ArrayIndexOutOfBoundsException ex) {
//                            builder.append(mgViewService.getViewMap()[y][x].getObjectNumber() + "");
//                        }
//                    }
//                }
//                if (y+1 < mgViewService.getViewMap()[1].length) {
//                    if (mgViewService.getViewMap()[y+1][0].getObjectNumber() != 0)
//                        builder.append("},\n");
//                } else {
//                    builder.append("}");
//                }
//            }
//            builder.append("};");
//            System.out.println(builder);
//        });
//
//        button.setFocusable(false);
//
//        this.add(button);
//        this.add(label);
//    }
//
//    public void updateSelection() {
//        label.setIcon(this.selection.getImageIcon());
//
//    }
}
