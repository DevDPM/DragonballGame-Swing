package nl.pokemon.mapGenerator.view;

import nl.pokemon.mapGenerator.model.BigFiller;
import nl.pokemon.mapGenerator.model.Filler;
import nl.pokemon.mapGenerator.model.Selected_SQM;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OptionPanel extends JPanel {

    @Inject(name = "incrementedFill")
    Filler filler;

    @Inject(name = "incrementedFill")
    BigFiller bigFiller;

//    @Inject
//    MG_SQMService mgSqmService;

    public void init() {
        filler.setText("Till tiles 3x3: " + filler.isIncrementFill());
        filler.addActionListener(filler);
        filler.setVisible(true);
        filler.setFocusable(false);
        filler.setBounds(0,0,250,25);
        this.add(filler);
        bigFiller.setText("Till tiles 10x10: " + filler.isIncrementFill());
        bigFiller.addActionListener(bigFiller);
        bigFiller.setVisible(true);
        bigFiller.setFocusable(false);
        bigFiller.setBounds(0,25,250,25);
        this.add(bigFiller);
        JButton fillAll = new JButton();
        fillAll.setText("Fill all empty tiles with selected tile");
        fillAll.addActionListener(e -> {
//            mgSqmService.fillAllSQM();
        });
        fillAll.setVisible(true);
        fillAll.setFocusable(false);
        fillAll.setBounds(0,50,250,25);
        this.add(fillAll);
        JButton resetAll = new JButton();
        resetAll.setText("RESET ALL!");
        resetAll.addActionListener(e -> {
//            mgSqmService.makeAllSQMEmpty();
        });
        resetAll.setVisible(true);
        resetAll.setFocusable(false);
        resetAll.setBounds(0,75,250,25);
        this.add(resetAll);
    }

    public OptionPanel() {
        this.setAutoscrolls(true);
        this.setLayout(null);
        this.setFocusable(false);
//        SQMObjects.bootstrap();

        AtomicInteger y = new AtomicInteger(85);
//        for (Classify classify : Classify.values()) {
//            JLabel showClassification = new JLabel(classify.name());
//            showClassification.setBounds(1, y.getAndAdd(50), 150, 50);
//            showClassification.setVisible(true);
//            this.add(showClassification);
//            AtomicInteger x = new AtomicInteger(0);
//            SQMObjects.getSQMObjects().forEach((key, value) -> {
//                if (x.get() == 6) {
//                    x.set(0);
//                    y.addAndGet(50);
//                }
//                if (value.getClassify().equals(classify)) {
//                    Selected_SQM button = new Selected_SQM(value.getImageIcon());
//                    button.addActionListener(button);
//                    button.setObjectId(key);
//                    button.setBaseSQM(value);
//                    button.setFocusable(false);
//                    button.setBounds(x.get(), y.get(),50,50);
//                    button.setVisible(true);
//                    this.add(button);
//                    x.addAndGet(50);
//                }
//            });
//            y.addAndGet(50);
//        }

    }
}
