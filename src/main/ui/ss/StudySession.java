package ui.ss;

import model.Card;
import model.exceptions.EmptyStudyListException;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.*;

// a study session that displays flash cards for user to study
public abstract class StudySession extends JPanel {
    protected ArrayList<Card> cards;      // list of cards from deck, un-shuffled
    protected ArrayList<Card> studyList;  // list of cards from deck, shuffled
    protected Scanner scanner;            // scanner for user input
    protected int correctReviews;         // amount of cards marked as correct during study session

    // EFFECTS: constructs a new session with given list of cards to study
    public StudySession(ArrayList<Card> cards) {
        this.cards = cards;
        this.scanner = new Scanner(System.in);
    }

    // EFFECTS: selects the proper cards to add to study session
    protected abstract ArrayList<Card> generateStudyList(int n) throws EmptyStudyListException;

    // REQUIRES: n > 0
    // EFFECTS: generates a random sequence of integers from 0 to n
    protected ArrayList<Integer> generateShuffleSequence(Integer n) {
        ArrayList<Integer> sequence = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            sequence.add(i);
        }
        Collections.shuffle(sequence);
        return sequence;
    }

    /*
     * EFFECTS: displays front of each card in study list,
     *          one at a time, displays back of card
     *          when ENTER is pressed by user
     */
    public void begin() throws EmptyStudyListException {
        studyList = generateStudyList(cards.size());
        if (studyList.size() != 0) {
            return;
        }
    }

    // EFFECTS: calculates proportion of correct cards reviewed
    protected String calcSuccessRate() {
        double correct = correctReviews;
        double total = studyList.size();
        double rate = (correct / total) * 100;

        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(rate);
    }

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty of card
    protected void cardDifficulty(Card c) {
        System.out.println("\nDifficulty of card:");
        System.out.println("\t1) Hard");
        System.out.println("\t2) Good");
        System.out.println("\t3) Easy");

        while (true) {
            String diff = scanner.nextLine();
            if (diff.equals("2") || diff.equals("3")) {
                correctReviews++;
                c.processReview(Integer.parseInt(diff));
                break;
            } else if (diff.equals("1")) {
                c.processReview(Integer.parseInt(diff));
                break;
            } else {
                System.out.println("Invalid input, please try again!");
            }
        }
    }
}
