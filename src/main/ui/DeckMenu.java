package ui;

import model.Deck;
import ui.dialog.AddCardDialog;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Menu showing options for deck
public class DeckMenu extends JPanel {
    private QuizApp app;        // parent application
    private Deck deck;          // main deck
    private MainMenu mainMenu;  // previous menu
    private JLabel title;       // title of deck

    // EFFECTS: instantiates a new deck menu with given deck
    //          adds components to panel (this)
    public DeckMenu(QuizApp app, Deck d) {
        this.app = app;
        this.mainMenu = new MainMenu(app);
        deck = d;

        addComponents();
        title = addTitlePanel();
    }

    // MODIFIES: this
    // EFFECTS: sets layout of panel to border layout,
    //          adds components
    private void addComponents() {
        setLayout(new BorderLayout());
        addTitlePanel();
        addButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a button panel to parent panel
    private void addButtonPanel() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        c.weighty = 1.0;
        c.weightx = 1.0;
        c.ipady = Short.MAX_VALUE;
        c.ipadx = Short.MAX_VALUE;
        addButtonsToPanel(c, buttonPanel);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // EFFECTS: uses grid bag constraints to add buttons to button panel
    private void addButtonsToPanel(GridBagConstraints c, JPanel buttonPanel) {
        JButton bt1 = makeButton("Study!", new StudyListener());
        buttonPanel.add(bt1, c);

        c.gridx = 1;
        JButton bt2 = makeButton("View all Cards", new ViewCardsListener());
        buttonPanel.add(bt2, c);

        c.gridx = 2;
        JButton bt3 = makeButton("Add a Card", new AddCardListener());
        buttonPanel.add(bt3, c);

        c.gridx = 0;
        c.gridy = 1;
        JButton bt4 = makeButton("Rename Deck", new RenameListener());
        buttonPanel.add(bt4, c);

        c.gridx = 1;
        JButton bt5 = makeButton("Delete Deck", new DeleteListener());
        buttonPanel.add(bt5, c);

        c.gridx = 2;
        JButton bt6 = makeButton("Back to Menu", new BackListener());
        buttonPanel.add(bt6, c);
    }

    // EFFECTS: creates and returns a formatted button
    private JButton makeButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 14));

        if (listener != null) {
            button.addActionListener(listener);
        }

        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates a title panel and adds it to frame
    private JLabel addTitlePanel() {
        JLabel title = new JLabel(deck.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 48));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        titlePanel.add(title, BorderLayout.PAGE_START);

        add(titlePanel, BorderLayout.PAGE_START);

        return title;
    }

    // MODIFIES: this
    // EFFECTS: changes content of frame to previous menu (main menu)
    private void backToMenu() {
        Container frameContent = app.getFrame().getContentPane();

        frameContent.removeAll();
        frameContent.add(mainMenu);
        frameContent.revalidate();
        frameContent.repaint();
    }

    // Listens for "Return to Menu" button press
    class BackListener implements ActionListener {

        // EFFECTS: calls backToMenu() on button press
        public void actionPerformed(ActionEvent e) {
            backToMenu();
        }
    }

    // Listens for "Rename Deck" button press
    class RenameListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: opens a rename dialog, takes user input to rename current deck
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = QuizAppUtilities.createRenameDialog(deck, app.getFrame());
            if (s != null && !(s.equals(""))) {
                deck.renameDeck(s);
                title.setText(s);
                app.getFrame().revalidate();
                app.getFrame().repaint();
            }
        }
    }

    // Listens for "Delete Deck" button press
    class DeleteListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: opens a confirm dialog for user to delete current deck
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = QuizAppUtilities.createDeleteDialog(deck, app.getFrame());
            if (confirm == 0) {
                backToMenu();
                mainMenu.removeDeck(deck);
                app.updateTitle();
            }
        }
    }

    // Listens for "View all Cards" button press
    class ViewCardsListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to list of cards
        @Override
        public void actionPerformed(ActionEvent e) {
            Container frameContent = app.getFrame().getContentPane();
            frameContent.removeAll();
            frameContent.add(new CardListMenu(deck, app));
            frameContent.revalidate();
            frameContent.repaint();
        }
    }

    // Listens for "Add a Card" button press
    class AddCardListener implements ActionListener {

        // EFFECTS: shows a new dialog that takes user input,
        //          allows for adding cards to deck
        @Override
        public void actionPerformed(ActionEvent e) {
            AddCardDialog addCardDialog = new AddCardDialog(app.getFrame(), deck);
            addCardDialog.pack();
            addCardDialog.setVisible(true);
        }
    }

    // Listens for "Study!" button press
    class StudyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to study session
        @Override
        public void actionPerformed(ActionEvent e) {
            if (deck.getCardList().size() != 0) {
                JFrame parentFrame = app.getFrame();
                parentFrame.getContentPane().removeAll();
                parentFrame.setContentPane(new ChooseStudySessionMenu(app, deck));
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                QuizAppUtilities.showNoCardsWarning(app.getFrame());
            }
        }


    }
}
