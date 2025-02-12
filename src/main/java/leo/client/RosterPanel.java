///////////////////////////////////////////////////////////////////////
// Name: RosterPanel
// Desc: The panel that manages armies
// Date: 6/15/2003 - Gabe Jones
//   10/15/2010 - David Schwartz   
//   10/15/2010 - Added MirroredRandom Button to menu
//   12/2/2010 - Offset buttons so that they fit on screen nicely
// TODO:
///////////////////////////////////////////////////////////////////////
package leo.client;

// imports

import leo.shared.Action;
import leo.shared.Constants;

import java.awt.*;


public class RosterPanel extends LeoContainer {
    /////////////////////////////////////////////////////////////////
    // Constants
    /////////////////////////////////////////////////////////////////
    public static final int MARGIN = 6;
    private static final Color GOLD = new Color(255, 255, 175);
    private final RosterText rosterText;
    private boolean inside = false;

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private boolean first = true;
    private boolean noob = false;
    private String msgText = "";


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public RosterPanel(boolean isNoob) {
        super(0,
                0,
                Constants.SCREEN_WIDTH,
                Constants.SCREEN_HEIGHT);

        noob = true;

        SettingsButton tb = new SettingsButton(699, 495, 91, 19, Constants.IMG_TEAM_ICONS_BUTTON, "teamIcon");
        add(tb);

        SettingsButton mb = new SettingsButton(719, 529, 51, 19, Constants.IMG_MUTE, "soundButton");
        add(mb);
        SettingsButton mib = new SettingsButton(698, 529, 19, 19, Constants.IMG_MINUS_BUTTON, "soundMinus");
        add(mib);
        SettingsButton psb = new SettingsButton(772, 529, 19, 19, Constants.IMG_PLUS_BUTTON, "soundPlus");
        add(psb);        

        SettingsButton msb = new SettingsButton(719, 563, 51, 19, Constants.IMG_MUSIC, "musicButton");
        add(msb);
        SettingsButton mmb = new SettingsButton(698, 563, 19, 19, Constants.IMG_MINUS_BUTTON, "musicMinus");
        add(mmb);
        SettingsButton pmb = new SettingsButton(772, 563, 19, 19, Constants.IMG_PLUS_BUTTON, "musicPlus");
        add(pmb);

        // Create the buttons
        int buttonX = 5; //(Constants.SCREEN_WIDTH / 7) - 1;
        //int buttonHeight = 154;
        //int buttonY = Constants.SCREEN_HEIGHT - buttonHeight;

        // Play
        LaunchGameButton computerButton = new LaunchGameButton(
                this, buttonX, 69, 38, 38, Constants.IMG_SINGLE_RED, "Start Here!",
                "Play a single player game against the Artificial Opponent using your presently configured army. Each game you win, the difficulty increases. Every two you lose, it decreases.", false, "");
        add(computerButton);

        // Coop
        LaunchGameButton coopButton = new LaunchGameButton(
                this, buttonX, 111, 38, 38, Constants.IMG_COOPERATIVE_RED, "Cooperative",
                "Enter a queue to play in a cooperative game with another player against the Artificial Opponent.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(coopButton);

        // Play 2v2
        LaunchGameButton teamButton = new LaunchGameButton(
                this, buttonX, 153, 38, 38, Constants.IMG_TEAM, "2 vs 2",
                "Enter a queue to play a 2 vs 2 team game using your presently configured armies. At least four players must be in queue for the match to begin.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(teamButton);

        // play Constructed
        LaunchGameButton playButton = new LaunchGameButton(
                this, buttonX, 195, 38, 38, Constants.IMG_CONSTRUCTED_RED, "Constructed",
                "Enter a queue to play another player in a one-on-one match using your presently configured armies.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(playButton);

        // Duel with Random Armies
        LaunchGameButton duelButton = new LaunchGameButton(
                this, buttonX, 237, 38, 38, Constants.IMG_RANDOM_RED, "Random",
                "Enter a queue to play another player in a one-on-one match using randomly generated armies.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(duelButton);

        // Duel with Mirrored Random Armies
        LaunchGameButton mirroredButton = new LaunchGameButton(
                this, buttonX, 279, 38, 38, Constants.IMG_RANDOM_RED, "Mirrored Random",
                "Enter a queue to play another player in a one-on-one match using the same randomly generated armies.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(mirroredButton);

        // edit army
        LaunchGameButton editButton = new LaunchGameButton(
                this, buttonX, 321, 38, 38, Constants.IMG_EDIT_RED, "Edit Army",
                "Enter the army editor, where you can configure your army and buy and sell units.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(editButton);

        // Army Archive
        LaunchGameButton archiveButton = new LaunchGameButton(
                this, buttonX, 363, 38, 38, Constants.IMG_ARCHIVE_RED, "Army Archive",
                "Save and load army configurations.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(archiveButton);

        // Buy Unit
        LaunchGameButton buyButton = new LaunchGameButton(
                this, buttonX, 405, 38, 38, Constants.IMG_BUY_RED, "Buy New Unit",
                "Buy a randomly selected, new unit for your army for 100 gold.", true, "COMPLETE THE TUTORIAL FIRST!");
        add(buyButton);

        rosterText = new RosterText(this, "Wins: " + Client.getWins() + " Losses: " + Client.getLosses(), 4, 455, 186, 142);
    }


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public RosterPanel() {
        super(0,
                0,
                Constants.SCREEN_WIDTH,
                Constants.SCREEN_HEIGHT);
        //super(Constants.OFFSET,
        // Constants.OFFSET,
        // Constants.SCREEN_WIDTH - (Constants.OFFSET*2),
        // Constants.SCREEN_HEIGHT - (Constants.OFFSET*2));

        BlogButton bb = new BlogButton(699, 37, 91, 17);
        add(bb);
        GuideButton gb = new GuideButton(699, 87, 91, 17);
        add(gb);
        ForumButton fb = new ForumButton(699, 137, 91, 17);
        add(fb);
        ExitButton eb = new ExitButton(699, 187, 91, 17);
        add(eb);

        ScoresButton sb = new ScoresButton(699, 276, 91, 17);
        add(sb);
        AccountButton ab = new AccountButton(699, 326, 91, 17);
        add(ab);
        ReferButton rb = new ReferButton(699, 376, 91, 17);
        add(rb);
        CreditsButton cb = new CreditsButton(699, 426, 91, 17);
        add(cb);

        SettingsButton tb = new SettingsButton(699, 495, 91, 19, Constants.IMG_TEAM_ICONS_BUTTON, "teamIcon");
        add(tb);        

        SettingsButton mb = new SettingsButton(719, 529, 51, 19, Constants.IMG_MUTE, "soundButton");
        add(mb);
        SettingsButton mib = new SettingsButton(698, 529, 19, 19, Constants.IMG_MINUS_BUTTON, "soundMinus");
        add(mib);
        SettingsButton psb = new SettingsButton(772, 529, 19, 19, Constants.IMG_PLUS_BUTTON, "soundPlus");
        add(psb);        

        SettingsButton msb = new SettingsButton(719, 563, 51, 19, Constants.IMG_MUSIC, "musicButton");
        add(msb);
        SettingsButton mmb = new SettingsButton(698, 563, 19, 19, Constants.IMG_MINUS_BUTTON, "musicMinus");
        add(mmb);
        SettingsButton pmb = new SettingsButton(772, 563, 19, 19, Constants.IMG_PLUS_BUTTON, "musicPlus");
        add(pmb);         

        // Create the buttons
        int buttonX = 5; //(Constants.SCREEN_WIDTH / 7) - 1;
        //int buttonHeight = 154;
        //int buttonY = Constants.SCREEN_HEIGHT - buttonHeight;

        // Play AI
        LaunchGameButton computerButton = new LaunchGameButton(
                this, buttonX, 69, 38, 38, Constants.IMG_SINGLE_RED, "Single Player",
                "Play a single player game against the Artificial Opponent using your presently configured army. Each game you win, the difficulty increases. Every two you lose, it decreases.", false, "");
        add(computerButton);

        // Coop
        LaunchGameButton coopButton = new LaunchGameButton(
                this, buttonX, 111, 38, 38, Constants.IMG_COOPERATIVE_RED, "Cooperative",
                "Enter a queue to play in a cooperative game with another player against the Artificial Opponent.", Client.standalone, "ONLINE ONLY.");
        add(coopButton);

        // Play 2v2
        LaunchGameButton teamButton = new LaunchGameButton(
                this, buttonX, 153, 38, 38, Constants.IMG_TEAM, "2 vs 2",
                "Enter a queue to play a 2 vs 2 team game using your presently configured armies. At least four players must be in queue for the match to begin.", Client.standalone, "ONLINE ONLY.");
        add(teamButton);

        // play Constructed
        LaunchGameButton playButton = new LaunchGameButton(
                this, buttonX, 195, 38, 38, Constants.IMG_CONSTRUCTED_RED, "Constructed",
                "Enter a queue to play another player in a one-on-one match using your presently configured armies.", Client.standalone, "ONLINE ONLY.");
        add(playButton);

        // Duel with Random Armies
        LaunchGameButton duelButton = new LaunchGameButton(
                this, buttonX, 237, 38, 38, Constants.IMG_RANDOM_RED, "Random",
                "Enter a queue to play another player in a one-on-one match using randomly generated armies.", Client.standalone, "ONLINE ONLY.");
        add(duelButton);

        // Duel with Mirrored Random Armies
        LaunchGameButton mirroredButton = new LaunchGameButton(
                this, buttonX, 279, 38, 38, Constants.IMG_RANDOM_RED, "Mirrored Random",
                "Enter a queue to play another player in a one-on-one match using the same randomly generated armies.", Client.standalone, "ONLINE ONLY.");
        add(mirroredButton);

        // edit army
        LaunchGameButton editButton = new LaunchGameButton(
                this, buttonX, 321, 38, 38, Constants.IMG_EDIT_RED, "Edit Army",
                "Enter the army editor, where you can configure your army and buy and sell units.", false, "");
        add(editButton);

        // Army Archive
        LaunchGameButton archiveButton = new LaunchGameButton(
                this, buttonX, 363, 38, 38, Constants.IMG_ARCHIVE_RED, "Army Archive",
                "Save and load army configurations.", false, "");
        add(archiveButton);

        // Buy Unit
        LaunchGameButton buyButton = new LaunchGameButton(
                this, buttonX, 405, 38, 38, Constants.IMG_BUY_RED, "Buy New Unit",
                "Buy a randomly selected, new unit for your army for 100 gold.", false, "");
        add(buyButton);
        rosterText = new RosterText(this, "Wins: " + Client.getWins() + " Losses: " + Client.getLosses(), 4, 455, 186, 142);
    }


    /////////////////////////////////////////////////////////////////
    // Get the display string
    /////////////////////////////////////////////////////////////////
    public String getMessage() {
        if (noob) return "Welcome to Zatikon!";

        //if (Client.demo() || Client.getRating() < 1)
        if (Client.getRating() < 1)
            return "Gold: " + Client.getGold();
        else
            return ("Gold: " + Client.getGold() + "   Rating:" + Client.getRating() + ", #" + Client.getRank());
    }


    /////////////////////////////////////////////////////////////////
    // Get the display string
    /////////////////////////////////////////////////////////////////
    public String getCount() {
        return "Players online: " + Client.getPlayerCount();
    }


    /////////////////////////////////////////////////////////////////
    // Check gold
    /////////////////////////////////////////////////////////////////
    public void checkGold() {
        if (Client.lastGold() < Client.getGold() && Client.lastGold() != -1) {
            Client.getGameData().gainGold(Client.getGold() - Client.lastGold());
            Client.getImages().playSound(Constants.SOUND_GOLD);
        }
        Client.lastGold(Client.getGold());
    }

    public void setMsgText(String newText) {
        msgText = newText;
    }

    /////////////////////////////////////////////////////////////////
    // Draw the component
    /////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g, Frame mainFrame) {
        checkGold();

        if (first) {
            if (Client.needEmail()) {
                MissingEmail mie = new MissingEmail(Client.getFrame());
            }
            first = false;
            Client.getNetManager().sendByte(Action.NO_REFERRAL);
        }

        g.drawImage(Client.getImages().getImage(Constants.IMG_MENU), getScreenX(), getScreenY(), 800, 600, mainFrame);

        g.setFont(Client.getFontBold());
        drawText(g, Client.getName(), getScreenX() + 70, getScreenY() + 30, Color.yellow);

        g.setFont(Client.getFont());
        drawText(g, "Gold: " + Client.getGold(), getScreenX() + 70, getScreenY() + 45, GOLD);

        //g.setFont(Client.getFont());
        drawText(g, "Rating: " + Client.getRating() + (Client.getRank() != 0 ? ", #" + Client.getRank() : ""), getScreenX() + 70, getScreenY() + 58, GOLD);    

        //Client.getWins()
        //g.setFont(Client.getFontBig());
        //String text = getMessage();

        //int atX = getScreenX() + (getWidth()/2) - (g.getFontMetrics().stringWidth(text)/2);
        //int atY = getScreenY() + Constants.OFFSET + 20;

        //g.setColor(Color.black);
        //g.drawString(text, atX+1, atY);
        //g.drawString(text, atX-1, atY);
        //g.drawString(text, atX, atY+1);
        //g.drawString(text, atX, atY-1);
        //g.setColor(Color.white);

        //g.drawString(text, atX, atY);
        g.setFont(Client.getFont());


        if (Client.getGameData().gainGold() > 0) {
            g.setFont(Client.getFontBig());
            String text = "You have gained " +
                    Client.getGameData().gainGold() + " gold";

            int atX = getScreenX() + (getWidth() / 2) - (g.getFontMetrics().stringWidth(text) / 2);
            int atY = getScreenY() + getHeight() - 5;
            //int atY = Client.getGameData().getHiddenUnitStats().getScreenY();

            g.setColor(Color.black);
            g.drawString(text, atX + 1, atY);
            g.drawString(text, atX - 1, atY);
            g.drawString(text, atX, atY + 1);
            g.drawString(text, atX, atY - 1);
            g.setColor(Color.yellow);

            g.drawString(text, atX, atY);
            g.setFont(Client.getFont());
        }


        if (Client.getGameData().newRecruit()) {
            Client.getGameData().getHiddenUnitStats().draw(g, mainFrame);

            g.setFont(Client.getFontBig());
            String text = "You have recruited a new " +
                    Client.getGameData().getHiddenUnitStats().getName();

            //int atX = getScreenX() + (getWidth()/2) - (g.getFontMetrics().stringWidth(text)/2);
            //int atY = Client.getGameData().getHiddenUnitStats().getScreenY();
            //int atY = getY() + getHeight();
            //int atY = getY();

            //g.setColor(Color.black);
            //g.drawString(text, atX+1, atY);
            //g.drawString(text, atX-1, atY);
            //g.drawString(text, atX, atY+1);
            //g.drawString(text, atX, atY-1);
            //g.setColor(Color.yellow.brighter());

            //g.drawString(text, atX, atY);
            //g.setFont(Client.getFont());
        }

        //profile area top left of screen
        if ((Client.getGameData().getMouseX() >= getScreenX() && Client.getGameData().getMouseX() <= getScreenX() + 180) &&
                (Client.getGameData().getMouseY() >= getScreenY() && Client.getGameData().getMouseY() <= getScreenY() + 65)) {
            rosterText.setText("Wins:" + Client.getWins() + " Losses:" + Client.getLosses());
            rosterText.draw(g, mainFrame);

            if (!inside) {
                Client.getImages().playSound(Constants.SOUND_MOUSEOVER);
            }
            inside = true;
            //g.drawImage(Client.getImages().getImage(Constants.IMG_TEAM_HIGHLIGHT), getScreenX(), getScreenY(), getWidth(), getHeight(), mainFrame);
        } else {
            inside = false;
            //g.drawImage(Client.getImages().getImage(Constants.IMG_TEAM), getScreenX(), getScreenY(), getWidth(), getHeight(), mainFrame);
            if(msgText != "") {
                rosterText.setText(msgText);
                rosterText.draw(g, mainFrame);

                //g.setFont(Client.getFont());
                //g.setColor(Color.white);
                //g.drawString(msgText, 4 + 20, 505 + 13);
                //msgText = "";
            }
        }

        super.draw(g, mainFrame);
    }


    /////////////////////////////////////////////////////////////////
    // Draw some text
    /////////////////////////////////////////////////////////////////
    public void drawText(Graphics2D g, String text, int atX, int atY, Color color) {
        // draw the outline
        g.setColor(Color.black);
        g.drawString(text, atX + 1, atY);
        g.drawString(text, atX - 1, atY);
        g.drawString(text, atX, atY + 1);
        g.drawString(text, atX, atY - 1);

        // set the right color
        g.setColor(color);

        // draw it
        g.drawString(text, atX, atY);

    }
}
