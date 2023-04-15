package nl.pokemon.mapGenerator.service;

import nl.pokemon.game.model.Combinable;
import nl.pokemon.game.util.SQMObjects;
import nl.pokemon.mapGenerator.model.BigFiller;
import nl.pokemon.mapGenerator.model.Filler;
import nl.pokemon.mapGenerator.model.MG_BaseSQM;
import nl.pokemon.mapGenerator.model.Selection;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class MG_SQMService {

    @Inject
    MG_ViewService mgViewService;

    @Inject(name = "incrementedFill")
    Filler filler;

    @Inject(name = "incrementedFill")
    BigFiller bigFiller;

    @Inject(name = "selectedItem")
    Selection selection;

    public void updateSQM(MG_BaseSQM sqm) {
        Selection selection = Kickstarter.getInstanceOf(Selection.class);
        sqm.setObjectNumber(selection.getId());
        sqm.setImageIcon(selection.getImageIcon());
        sqm.updateBounds();

        if (selection.getBaseSQM() instanceof Combinable e) {
            if ((sqm.getIdY()+e.getNumberOfSQMWidth() < mgViewService.getViewMap()[0].length) &&
                    ((sqm.getIdX()+e.getNumberOfSQMHeight() < mgViewService.getViewMap().length)) &&
                    (e.isTopLeftCornerOfCollection())) {
                int startNumber = sqm.getObjectNumber();
                for (int y = sqm.getIdY(); y < sqm.getIdY()+e.getNumberOfSQMHeight(); y++) {
                    for (int x = sqm.getIdX(); x < sqm.getIdX()+e.getNumberOfSQMWidth(); x++) {
                        MG_BaseSQM baseSQM = mgViewService.getViewMap()[y][x];
                        baseSQM.setObjectNumber(startNumber);
//                        baseSQM.setImageIcon(SQMObjects.getSQMObject().get(startNumber).getImageIcon());
                        startNumber++;
                    }
                }
            }
            return;
        }

        if (bigFiller.isIncrementFill()) {
            for (int y = sqm.getIdY(); y < sqm.getIdY()+10; y++) {
                for (int x = sqm.getIdX(); x < sqm.getIdX()+10; x++) {
                    MG_BaseSQM baseSQM = mgViewService.getViewMap()[y][x];
                    baseSQM.setObjectNumber(selection.getBaseSQM().getObjectNumber());
                    baseSQM.setImageIcon(selection.getImageIcon());
                }
            }
            return;
        }

        if (filler.isIncrementFill()) {
            for (int y = sqm.getIdY(); y < sqm.getIdY()+3; y++) {
                for (int x = sqm.getIdX(); x < sqm.getIdX()+3; x++) {
                    MG_BaseSQM baseSQM = mgViewService.getViewMap()[y][x];
                    baseSQM.setObjectNumber(selection.getBaseSQM().getObjectNumber());
                    baseSQM.setImageIcon(selection.getImageIcon());
                }
            }
        }
    }

    public void fillAllSQM() {
        for (int y = 0; y < mgViewService.getViewMap()[0].length; y++) {
            for (int x = 0; x < mgViewService.getViewMap().length; x++) {
                MG_BaseSQM baseSQM = mgViewService.getViewMap()[y][x];
                if (baseSQM.getObjectNumber() == 0) {
                    baseSQM.setObjectNumber(selection.getBaseSQM().getObjectNumber());
                    baseSQM.setImageIcon(selection.getImageIcon());
                }
            }
        }
    }

    public void makeAllSQMEmpty() {
        for (int y = 0; y < mgViewService.getViewMap()[0].length; y++) {
            for (int x = 0; x < mgViewService.getViewMap().length; x++) {
                MG_BaseSQM baseSQM = mgViewService.getViewMap()[y][x];
                baseSQM.setObjectNumber(0);
                baseSQM.setImageIcon(null);
            }
        }
    }
}
