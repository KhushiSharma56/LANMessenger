package chat.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatUI extends JFrame {

    public interface SendListener {
        void onSend(String message);
    }

    private JTextArea chatArea;
    private JPanel chatPanel;
    private JScrollPane scrollPane;

    private JTextField inputField;
    private JButton sendButton;
    private SendListener listener;

    public ChatUI(String title, SendListener listener) {
        this.listener = listener;
        setTitle(title);
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS)); 

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);

        //add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty() && listener != null) {
            listener.onSend(text);
            inputField.setText("");
        }
    }

    public void appendMessage(String sender, String message, Color color) {
        chatArea.append("[" + sender + "] " + message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());  // auto-scroll
    }

    public void setStatus(String status, boolean connected) {
        // For simplicity, let's update title bar with status
        setTitle("LAN Messenger - " + status);
    }

    public void setSendEnabled(boolean enabled) {
        inputField.setEnabled(enabled);
        sendButton.setEnabled(enabled);
    }
    
    // public void addMessage(String message, String senderIP, boolean sentByMe, boolean sentAck) 
    // {
    //     JPanel messageWrapper = new JPanel();
    //     messageWrapper.setLayout(new BorderLayout());

    //     // Message text label
    //     JLabel msgLabel = new JLabel("<html><body style='width: 200px;'>" + message + "</body></html>");
    //     msgLabel.setOpaque(true);
    //     msgLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    //     msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    //     if (sentByMe) {
    //         msgLabel.setBackground(new Color(220, 248, 198)); // light green bubble for sent
    //         messageWrapper.add(msgLabel, BorderLayout.EAST);
    //     } else {
    //         msgLabel.setBackground(new Color(255, 255, 255)); // white bubble for received
    //         messageWrapper.add(msgLabel, BorderLayout.WEST);
    //     }

    //     // IP label smaller font below message
    //     JLabel ipLabel = new JLabel("IP: " + senderIP);
    //     ipLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
    //     ipLabel.setForeground(Color.GRAY);

    //     // Acknowledgment label (e.g., ✓ or empty string)
    //     JLabel ackLabel = new JLabel(sentAck ? "\u2713" : ""); // Unicode checkmark

    //     // Panel to hold IP and ack below message text
    //     JPanel infoPanel = new JPanel(new BorderLayout());
    //     infoPanel.add(ipLabel, BorderLayout.WEST);
    //     infoPanel.add(ackLabel, BorderLayout.EAST);

    //     JPanel messageWithInfo = new JPanel();
    //     messageWithInfo.setLayout(new BorderLayout());
    //     messageWithInfo.add(messageWrapper, BorderLayout.NORTH);
    //     messageWithInfo.add(infoPanel, BorderLayout.SOUTH);
    //     messageWithInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    //     chatPanel.add(messageWithInfo);
    //     chatPanel.revalidate();
    //     chatPanel.repaint();

    //     // Scroll to bottom
    //     SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    // }
 
    public void addMessage(String message, String senderIP, String timestamp, boolean sentByMe, boolean sentAck) {
        // Panel for single message (with flow alignment)
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new FlowLayout(sentByMe ? FlowLayout.RIGHT : FlowLayout.LEFT));
        wrapper.setOpaque(false);

        // Message + info stacked vertically
        Box box = Box.createVerticalBox();

        JLabel msgLabel = new JLabel(message);
        msgLabel.setOpaque(true);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        msgLabel.setBorder(BorderFactory.createEmptyBorder(8, 16, 2, 16));
        msgLabel.setBackground(sentByMe ? new Color(220, 248, 198) : Color.WHITE);

        JLabel infoLabel = new JLabel(timestamp + " | " + senderIP + (sentAck ? " ✓" : ""));
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        infoLabel.setForeground(Color.GRAY);

        box.add(msgLabel);
        box.add(infoLabel);
        wrapper.add(box);

        // Reduce vertical spacing between messages
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // was 6–12 before

        chatPanel.add(wrapper);
        chatPanel.revalidate();
        chatPanel.repaint();
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }


}
