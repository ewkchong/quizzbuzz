package ui.ss;

import model.Card;
import ui.DeckMenu;
import ui.QuizApp;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel that represents a flash card with a front and back
public class CardPanel extends StudyCardPanel {

    // EFFECTS: creates new card panel to show a single card
    public CardPanel(Card c, StudySession ss, int i, int size, QuizApp app) {
        super(app, c, ss, i, size);
        setLayout(new BorderLayout());
        addComponents();
        addButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty (1, 2 or 3)
    //          returns GUI to deck menu
    @Override
    protected void processDifficulty(int diff) {
        if (index + 1 >= size) {
            card.processReview(diff);
            returnToMenu();
            if (diff > 1) {
                ss.incrementCorrectReviews();
            }
            ss.showPerformanceDialog();
        } else {
            card.processReview(diff);
            CardLayout cl = (CardLayout) ss.getLayout();
            cl.next(ss);
            if (diff > 1) {
                ss.incrementCorrectReviews();
            }
        }
    }

}

