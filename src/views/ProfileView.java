package views;

import app.BandHeroApp;
import controllers.ProfileController;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfileView extends BaseView {

    public ProfileView() {
        super(new BorderLayout());
        createComponents();
        this.setPreferredSize(getPreferredSize());
    }

    public void createComponents() {
        //Set profile image width and height
        profileImgHeight = profileImgWidth = 120;

        //Get Controller Reference
        ProfileController controller = (ProfileController) BandHeroApp.getInstance().getController();

        //Set Margins
        this.setBorder(new EmptyBorder(10,10,10,10));

        //Create Sidebar Container
        sideBarContainer = new JPanel(new BorderLayout());

        //Create Center Container
        centerContainer = new JPanel(new BorderLayout());

        //Add User Image
        userImage = new JLabel(new ImageIcon(resizeProfileImage(controller.getUser().getProfileImage(), profileImgWidth - 10, profileImgHeight - 10)));
        userImage.setPreferredSize(new Dimension(profileImgWidth, profileImgHeight));
        userImage.setBorder(new BevelBorder(BevelBorder.RAISED, Color.gray, Color.black));

        //Add Nav Menu
        JPanel navMenu = new NavMenu();

        //Add search bar
        JPanel searchBar = new SearchBar();

        //Add "News Feed"
        // TODO create "News Feed" component and add it under search bar.
        JPanel dynamicContentPanel = new DynamicContentPanel();

        //Add Components to Sidebar Container
        sideBarContainer.add(userImage, BorderLayout.PAGE_START);
        sideBarContainer.add(navMenu, BorderLayout.CENTER);

        //Add Components to Center Container
        centerContainer.add(searchBar, BorderLayout.PAGE_START);
        centerContainer.add(dynamicContentPanel, BorderLayout.CENTER);

        this.add(sideBarContainer, BorderLayout.LINE_START);
        this.add(centerContainer, BorderLayout.CENTER);
    }

    private Image resizeProfileImage(ImageIcon imgIcon, int w, int h) {
        Image img = imgIcon.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    JPanel centerContainer;
    JPanel sideBarContainer;
    JLabel userImage;
    private int profileImgWidth, profileImgHeight;
}
