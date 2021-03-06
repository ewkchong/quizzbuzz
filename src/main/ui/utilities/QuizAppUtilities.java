package ui.utilities;

import model.Deck;
import ui.DeckMenu;
import ui.QuizApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Utilities used throughout the program
public class QuizAppUtilities {
    public static final String UI_FONT = "Montserrat";  // font used throughout UI

    // MODIFIES: parent
    // EFFECTS: shows a dialog to rename a deck
    public static String createRenameDialog(Deck d, JFrame parent) {
        String s = (String) JOptionPane.showInputDialog(
                parent,
                "What would you like to rename the deck to?",
                "Rename Deck",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                d.getTitle());
        if (s != null) {
            parent.setTitle("QuizzBuzz (Unsaved Changes)");
        }
        return s;
    }

    // EFFECTS: shows a dialog to confirm deletion of deck
    public static int createDeleteDialog(Deck d, JFrame parent) {
        return JOptionPane.showConfirmDialog(
                parent,
                "Are you sure you would like to delete the deck: " + '"' + d.getTitle() + '"' + "?",
                "Delete Deck",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: splits a comma-separated list into an array list of strings
    public static ArrayList<String> stringToArrayList(String tags) {
        String[] tagsArray = tags.split("\\s*,\\s*");
        List<String> tagsList = Arrays.asList(tagsArray);

        return new ArrayList<>(tagsList);
    }

    // EFFECTS: shows a dialog notifying the user
    //          that there are no cards to study
    public static void showNoCardsWarning(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame,
                "Currently no cards to study!",
                "Empty Study List",
                JOptionPane.PLAIN_MESSAGE);
    }
}
